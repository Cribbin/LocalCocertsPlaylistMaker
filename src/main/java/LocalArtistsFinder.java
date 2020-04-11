import auth.SongkickApiAuthenticator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mikael.urlbuilder.UrlBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class LocalArtistsFinder {
    private static final String SONGKICK_HOST = "api.songkick.com";
    private static final String LOCATION_SEARCH_PATH = "api/3.0/search/locations.json";
    private static final String AREA_QUERY_PARAM = "query";
    private static final String API_KEY_QUERY_PARAM = "apikey";

    public String findUpcomingConcertsIn(String area) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        URI uri = UrlBuilder.empty()
                .withScheme("https")
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

        return response;
    }
}
