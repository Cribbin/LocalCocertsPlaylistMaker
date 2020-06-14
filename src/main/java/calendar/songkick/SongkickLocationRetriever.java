package calendar.songkick;

import auth.SongkickApiAuthenticator;
import http.HttpClient;

import java.io.FileNotFoundException;
import java.util.Optional;

public class SongkickLocationRetriever {
    private static final String LOCATION_SEARCH_URL = "http://api.songkick.com/api/3.0/search/locations.json?query=%s&apikey=%s";

    private final HttpClient<SongkickLocationSearchResponse> httpClient;

    public SongkickLocationRetriever(HttpClient<SongkickLocationSearchResponse> httpClient) {
        this.httpClient = httpClient;
    }

    public Optional<String> getMetroAreaIdFor(String area) {
        String filledUrl;

        try {
            filledUrl = String.format(LOCATION_SEARCH_URL, area, SongkickApiAuthenticator.getApiKey());
        } catch (FileNotFoundException e) {
            // TODO Add error handling
            return Optional.empty();
        }

        Optional<SongkickLocationSearchResponse> response = httpClient.get(filledUrl, SongkickLocationSearchResponse.class);

        if (response.isEmpty()) {
            return Optional.empty();
        }

        SongkickLocationSearchResponse.Location[] locations = response.get().getResultsPage().getResults().getLocation();

        if (locations == null || locations.length == 0) {
            return Optional.empty();
        }

        return Optional.of(locations[0].getMetroArea().getId());
    }

}
