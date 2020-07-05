package artist;

import http.HttpClient;

import java.util.*;
import java.util.stream.Collectors;

public class SpotifyArtistSearcher implements ArtistSearcher {
    private static final String URL = "https://api.spotify.com/v1/search?q=%s&type=artist";

    private final HttpClient<SpotifySearchResponse> httpClient;
    private final String accessToken;

    public SpotifyArtistSearcher(HttpClient<SpotifySearchResponse> httpClient, String accessToken) {
        this.httpClient = httpClient;
        this.accessToken = accessToken;
    }

    @Override
    public List<Artist> findArtists(Set<String> artistNames) {
        return artistNames.stream()
                .map(artist -> artist.replace(" ", "+"))
                .map(this::findArtist)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted(Artist::compareByPopularity)
                .collect(Collectors.toList());
    }

    private Optional<Artist> findArtist(String artistName) {
        String url = String.format(URL, artistName);

        String authToken = String.format("Bearer %s", accessToken);
        Map<String, String> headerParams = Collections.singletonMap("Authorization", authToken);

        Optional<SpotifySearchResponse> response = httpClient.getWithHeader(
                url,
                SpotifySearchResponse.class,
                headerParams);

        if (response.isEmpty() || response.get().getArtists() == null || response.get().getArtists().getItems().isEmpty()) {
            // No artist found
            return Optional.empty();
        }

        SpotifySearchResponse.Item topArtist = response.get().getArtists().getItems().get(0);
        return Optional.of(responseToArtist(topArtist));
    }

    private Artist responseToArtist(SpotifySearchResponse.Item responseItem) {
        return new Artist(responseItem.getId(), responseItem.getName(), responseItem.getPopularity());
    }
}
