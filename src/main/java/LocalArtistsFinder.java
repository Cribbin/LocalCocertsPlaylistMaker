import auth.SongkickApiAuthenticator;
import io.mikael.urlbuilder.UrlBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class LocalArtistsFinder {
    private static final String SONGKICK_HOST = "api.songkick.com";
    private static final String LOCATION_SEARCH_PATH = "api/3.0/search/locations.json";
    private static final String CALENDAR_PATH = "api/3.0/metro_areas/%s/calendar.json";
    private static final String AREA_QUERY_PARAM = "query";
    private static final String API_KEY_QUERY_PARAM = "apikey";
    private static final String PROTOCOL = "https";

    private HttpClient httpClient;

    public LocalArtistsFinder() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public Optional<String> getMetroAreaIdFor(String area) throws IOException, InterruptedException {
        URI uri = UrlBuilder.empty()
                .withScheme(PROTOCOL)
                .withHost(SONGKICK_HOST)
                .withPath(LOCATION_SEARCH_PATH)
                .addParameter(AREA_QUERY_PARAM, area)
                .addParameter(API_KEY_QUERY_PARAM, SongkickApiAuthenticator.getApiKey())
                .toUri();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        String response = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();

        JsonParser parser = new JsonParser();
        var parsedJson = parser.parse(response);

        var resultsPage = (LinkedHashMap) parsedJson.get("resultsPage");
        var results = (LinkedHashMap) resultsPage.get("results");
        var locations = (ArrayList) results.get("location");

        if (!locations.isEmpty()) {
            var primaryLocation = (LinkedHashMap) locations.get(0);
            var metroAreaDetails = (LinkedHashMap) primaryLocation.get("metroArea");
            var metroAreaId = (Integer) metroAreaDetails.get("id");
            return Optional.of(metroAreaId.toString());
        }

        return Optional.empty();
    }

    public List<Event> getMetroAreaCalendarFor(String metroAreaId) throws IOException, InterruptedException {
        int page = 1;
        Set<Event> output = new LinkedHashSet<>();
        List<Event> pageOfOutput = getMetroAreaCalendarFor(metroAreaId, page);

        while (!pageOfOutput.isEmpty()) {
            output.addAll(pageOfOutput);
            pageOfOutput = getMetroAreaCalendarFor(metroAreaId, ++page);
        }

        return output.stream().sorted(Event::compareByPopularity).collect(Collectors.toList());
    }

    public List<Event> getMetroAreaCalendarFor(String metroAreaId, int page) throws IOException, InterruptedException {
        URI uri = UrlBuilder.empty()
                .withScheme(PROTOCOL)
                .withHost(SONGKICK_HOST)
                .withPath(String.format(CALENDAR_PATH, metroAreaId))
                .addParameter(API_KEY_QUERY_PARAM, SongkickApiAuthenticator.getApiKey())
                .addParameter("min_date", getTodaysDate())
                .addParameter("max_date", getNMonthsFromNow(1))
                .addParameter("page", String.valueOf(page))
                .addParameter("per_page", "50")
                .toUri();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        String response = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();

        JsonParser parser = new JsonParser();
        var parsedJson = parser.parse(response);

        var resultsPage = (LinkedHashMap) parsedJson.get("resultsPage");
        var results = (LinkedHashMap) resultsPage.get("results");

        if (results.isEmpty()) {
            return Collections.emptyList();
        }

        var events = (ArrayList) results.get("event");

        return (List<Event>) events
                .stream()
                .map(eventHashMap -> makeEvent((LinkedHashMap) eventHashMap))
                .filter(event -> event != null)
                .collect(Collectors.toList());
    }

    private static String getTodaysDate() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        return format.format(today);
    }

    private static String getNMonthsFromNow(int numMonths) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.MONTH, numMonths);

        return format.format(calendar.getTime());
    }

    private static Event makeEvent(LinkedHashMap<String, Object> eventMap) {
        var performance = (ArrayList) eventMap.get("performance");

        if (performance.isEmpty()) {
            return null;
        }

        var artist = (String) ((LinkedHashMap) performance.get(0)).get("displayName");
        var popularity = (Double) eventMap.get("popularity");

        return new Event(artist, popularity);
    }
}
