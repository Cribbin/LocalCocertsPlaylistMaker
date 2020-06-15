package calendar.songkick;

import http.HttpClient;

import java.util.Optional;
import java.util.Properties;

import static calendar.songkick.SongkickLocationSearchResponse.Location;

public class SongkickLocationRetriever {
    private static final String LOCATION_SEARCH_URL = "http://api.songkick.com/api/3.0/search/locations.json?query=%s&apikey=%s";

    private final HttpClient<SongkickLocationSearchResponse> httpClient;
    private final String songkickApiKey;

    public SongkickLocationRetriever(HttpClient<SongkickLocationSearchResponse> httpClient, Properties properties) {
        this.httpClient = httpClient;
        this.songkickApiKey = properties.getProperty("songkick-api-key");

        if (songkickApiKey.isEmpty()) {
            throw new IllegalArgumentException("songkick-api-key not found");
        }
    }

    public Optional<String> getMetroAreaIdFor(String area) {
        String url = String.format(LOCATION_SEARCH_URL, area, songkickApiKey);

        Optional<SongkickLocationSearchResponse> response = httpClient.get(url, SongkickLocationSearchResponse.class);

        if (response.isEmpty()) {
            return Optional.empty();
        }

        Location[] locations = response.get().getResultsPage().getResults().getLocation();

        if (locations == null || locations.length == 0) {
            return Optional.empty();
        }

        return Optional.of(locations[0].getMetroArea().getId());
    }

}
