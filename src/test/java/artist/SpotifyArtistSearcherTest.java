package artist;

import http.HttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SpotifyArtistSearcherTest {
    private static final String ARTIST_NAME_1 = "Elton John";
    private static final String ARTIST_NAME_2 = "Beach House";
    private static final String ARTIST_NAME_3 = "Courtney Barnett";

    private static final int DEFAULT_POPULARITY = 50;

    @Mock
    private HttpClient<SpotifySearchResponse> httpClient;

    private SpotifyArtistSearcher artistSearcher;

    @BeforeEach
    void setUp() {
        initMocks(this);
        artistSearcher = new SpotifyArtistSearcher(httpClient, "AccessToken");
    }

    @Test
    public void shouldReturnMultipleArtists() {
        Set<String> artistNames = Set.of(ARTIST_NAME_1, ARTIST_NAME_2, ARTIST_NAME_3);

        when(httpClient.getWithHeader(any(), any(), any()))
                .thenReturn(searchResponse(ARTIST_NAME_1))
                .thenReturn(searchResponse(ARTIST_NAME_2))
                .thenReturn(searchResponse(ARTIST_NAME_3));

        List<Artist> foundArtists = artistSearcher.findArtists(artistNames);

        assertEquals(3, foundArtists.size());
        assertEquals(artist(ARTIST_NAME_1), foundArtists.get(0));
        assertEquals(artist(ARTIST_NAME_2), foundArtists.get(1));
        assertEquals(artist(ARTIST_NAME_3), foundArtists.get(2));
    }

    private static Optional<SpotifySearchResponse> searchResponse(String name) {
        List<SpotifySearchResponse.Item> items = items(name);
        SpotifySearchResponse.Artist artist = new SpotifySearchResponse.Artist(items);
        SpotifySearchResponse spotifySearchResponse = new SpotifySearchResponse(artist);

        return Optional.of(spotifySearchResponse);
    }

    private static List<SpotifySearchResponse.Item> items(String... names) {
        List<SpotifySearchResponse.Item> items = new ArrayList<>();
        for (String name : names) {
            items.add(new SpotifySearchResponse.Item(id(name), name, DEFAULT_POPULARITY, "artist"));
        }
        return items;
    }

    private static Artist artist(String name) {
        return new Artist(id(name), name, DEFAULT_POPULARITY);
    }

    private static String id(String name) {
        return String.format("id:%s", name);
    }
}
