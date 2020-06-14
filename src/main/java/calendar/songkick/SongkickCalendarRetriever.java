package calendar.songkick;

import auth.SongkickApiAuthenticator;
import calendar.CalendarRetriever;
import http.HttpClient;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class SongkickCalendarRetriever implements CalendarRetriever {
    private static final String CALENDAR_SEARCH_URL = "http://api.songkick.com/api/3.0/metro_areas/%s/calendar.json?min_date=%s&max_date=%s&page=%s&per_page=%s&apikey=%s";

    private final HttpClient<SongkickCalendarSearchResponse> httpClient;

    public SongkickCalendarRetriever(http.HttpClient<SongkickCalendarSearchResponse> httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Set<String> getArtistsPlayingIn(String area) {
        int page = 1;
        Set<String> output = new HashSet<>();
        Set<String> pageOfOutput = getMetroAreaCalendarFor(area, page);

        while (!pageOfOutput.isEmpty()) {
            output.addAll(pageOfOutput);
            pageOfOutput = getMetroAreaCalendarFor(area, ++page);
        }

        return output;
    }

    private Set<String> getMetroAreaCalendarFor(String metroAreaId, int page) {
        try {
            String filledUrl = String.format(CALENDAR_SEARCH_URL,
                    metroAreaId,
                    getTodaysDate(),
                    getOneMonthFromNow(),
                    page,
                    50,
                    SongkickApiAuthenticator.getApiKey());

            SongkickCalendarSearchResponse response = httpClient.get(filledUrl, SongkickCalendarSearchResponse.class).orElseThrow();

            SongkickCalendarSearchResponse.Event[] events = response.getResultsPage().getResults().getEvent();

            if (events == null || events.length == 0) {
                return Collections.emptySet();
            }

            return Arrays.stream(events)
                    .filter(Objects::nonNull)
                    .filter(event -> !event.getStatus().equals("cancelled"))
                    .flatMap(event -> Arrays.stream(event.getPerformance()))
                    .map(performance -> performance.getArtist().getDisplayName())
                    .collect(Collectors.toSet());

        } catch (FileNotFoundException e) {
            // TODO error handling
            return Collections.emptySet();
        }
    }

    private static String getTodaysDate() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        return format.format(today);
    }

    private static String getOneMonthFromNow() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.MONTH, 1);

        return format.format(calendar.getTime());
    }
}
