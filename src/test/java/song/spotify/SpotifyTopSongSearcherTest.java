package song.spotify;

import artist.Artist;
import http.HttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import song.Song;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SpotifyTopSongSearcherTest {
    private static final String SONG_1_NAME = "Other People";
    private static final String SONG_2_NAME = "All Your Yeahs";
    private static final String SONG_3_NAME = "Lemon Glow";

    private static final String ARTIST_NAME = "Beach House";
    private static final Artist ARTIST = new Artist(id(ARTIST_NAME), ARTIST_NAME, 50);

    @Mock
    private HttpClient<SpotifyTopSongResponse> httpClient;

    private SpotifyTopSongSearcher songSearcher;

    @BeforeEach
    void setUp() {
        initMocks(this);
        songSearcher = new SpotifyTopSongSearcher(httpClient, "AccessToken", "AnyLocation");
    }

    @Test
    public void shouldReturnFirstSongWhenMultiple() {
        when(httpClient.getWithHeader(any(), any(), any())).thenReturn(topSongResponse(SONG_1_NAME, SONG_2_NAME, SONG_3_NAME));

        List<Song> topSongs = songSearcher.getTopSongsFor(Collections.singletonList(ARTIST));
        assertEquals(1, topSongs.size());
        assertEquals(song(SONG_1_NAME, ARTIST), topSongs.get(0));
    }

    private static Optional<SpotifyTopSongResponse> topSongResponse(String... songs) {
        SpotifyTopSongResponse.Track[] tracks = new SpotifyTopSongResponse.Track[songs.length];
        for (int i = 0; i < songs.length; i++) {
            tracks[i] = track(songs[i]);
        }
        return Optional.of(new SpotifyTopSongResponse(tracks));
    }

    private static SpotifyTopSongResponse.Track track(String name) {
        return new SpotifyTopSongResponse.Track(id(name), name);
    }

    private static Song song(String songName, Artist artist) {
        return new Song(id(songName), songName, artist);
    }

    private static String id(String name) {
        return String.format("id:%s", name);
    }
}
