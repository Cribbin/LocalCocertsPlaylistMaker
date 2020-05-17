import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

class LocalArtistsFinderTest {
    private LocalArtistsFinder localArtistsFinder;

    @BeforeEach
    void setUp() {
        localArtistsFinder = new LocalArtistsFinder();
    }

    @Test
    public void getMetroAreaId() throws IOException, InterruptedException {
        Optional<String> response = localArtistsFinder.getMetroAreaIdFor("London");

        var metroAreaId = response.orElseThrow(AssertionError::new);
    }

   @Test
   public void getMetroAreaCalendar() throws IOException, InterruptedException {
        var londonMetroAreaId = "24426";

        List<Event> events = localArtistsFinder.getMetroAreaCalendarFor(londonMetroAreaId);
   }
}