import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertTrue;

public class IntegrationTest {
    private static final String KEY = "<INSERT_KEY>";

    @Test
    public void test() throws IOException, InterruptedException {
        LocalArtistsFinder localArtistsFinder = new LocalArtistsFinder();
        Optional<String> metroAreaId = localArtistsFinder.getMetroAreaIdFor("London");

        assertTrue(metroAreaId.isPresent());

        List<Event> events = localArtistsFinder.getMetroAreaCalendarFor(metroAreaId.get());
        List<String> artists = events.stream().map(Event::getArtist).collect(Collectors.toList());

        int startingIndex = Math.max(0, artists.size() - 50);
        artists = artists.subList(startingIndex, artists.size());

        SpotifyReader spotifyReader = new SpotifyReader();

        List<ArtistDetail> artistDetails = spotifyReader.searchArtists(artists, KEY);

        List<SongDetail> songDetails = spotifyReader.getTopSongForArtists(artistDetails, KEY);

        songDetails.forEach(song -> System.out.println(String.format("- %s", song)));
    }
}
