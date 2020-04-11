import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class LocalArtistsFinderTest {
    private LocalArtistsFinder localArtistsFinder;

    @BeforeEach
    void setUp() {
        localArtistsFinder = new LocalArtistsFinder();
    }

    @Test
    public void test() throws IOException, InterruptedException {
        localArtistsFinder.findUpcomingConcertsIn("London");
    }
}