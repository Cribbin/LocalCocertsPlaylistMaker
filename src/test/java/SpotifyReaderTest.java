import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class SpotifyReaderTest {
    private static final String KEY = "<INSERT_KEY>";

    @Test
    public void searchArtists() throws IOException, InterruptedException {
        SpotifyReader reader = new SpotifyReader();
//        List<ArtistDetail> response = reader.searchArtists(List.of("Kendrick Lamar", "Kings of Leon"), KEY);
    }

    @Test
    public void getTopSong() throws IOException, InterruptedException {
        ArtistDetail artist1 = new ArtistDetail("2YZyLoL8N0Wb9xBt1NhZWg", "Kendrick Lamar", 90);
        ArtistDetail artist2 = new ArtistDetail("2qk9voo8llSGYcZ6xrBzKx", "Kings of Leon", 78);

        SpotifyReader reader = new SpotifyReader();

 //       List<SongDetail> response = reader.getTopSongForArtists(List.of(artist1, artist2), KEY);
    }
}
