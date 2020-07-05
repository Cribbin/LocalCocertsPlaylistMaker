package calendar.songkick;

import http.HttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import static calendar.songkick.SongkickCalendarSearchResponse.Artist;
import static calendar.songkick.SongkickCalendarSearchResponse.Event;
import static calendar.songkick.SongkickCalendarSearchResponse.Performance;
import static calendar.songkick.SongkickCalendarSearchResponse.Results;
import static calendar.songkick.SongkickCalendarSearchResponse.ResultsPage;

class SongkickCalendarRetrieverTest {
    private static final String ARTIST_1 = "Elton John";
    private static final String ARTIST_2 = "Beach House";
    private static final String ARTIST_3 = "Kanye West";

    private SongkickCalendarRetriever songkickCalendarRetriever;

    @Mock
    private HttpClient<SongkickCalendarSearchResponse> httpClient;

    @Mock
    private Properties properties;

    @BeforeEach
    void setUp() {
        initMocks(this);
        when(properties.getProperty("songkick-api-key")).thenReturn("any-key");
        songkickCalendarRetriever = new SongkickCalendarRetriever(httpClient, properties);
    }

    @Test
    public void shouldReturnEventCalendar() {
        when(httpClient.get(any(), any())).thenReturn(sampleResponse()).thenReturn(emptyResponse());

        Set<String> response = songkickCalendarRetriever.getArtistsPlayingIn("any");

        assertEquals(3, response.size());
        assertTrue(response.contains(ARTIST_1));
        assertTrue(response.contains(ARTIST_2));
        assertTrue(response.contains(ARTIST_3));
    }

    private static Optional<SongkickCalendarSearchResponse> sampleResponse() {
        return response(events(performances(ARTIST_1, ARTIST_2), performances(ARTIST_3)), 3);
    }

    private static Optional<SongkickCalendarSearchResponse> emptyResponse() {
        return response(null, 0);
    }

    private static Optional<SongkickCalendarSearchResponse> response(Event[] events, int totalEntries) {
        ResultsPage resultsPage = new ResultsPage("ok", new Results(events), 10, 1, totalEntries);
        return Optional.of(new SongkickCalendarSearchResponse(resultsPage));
    }

    private static Event[] events(Performance[]... performances) {
        Event[] events = new Event[performances.length];
        for (int i = 0; i < events.length; i++) {
            events[i] = new Event("ok", performances[i]);
        }
        return events;
    }

    private static Performance[] performances(String... artistNames) {
        Performance[] performances = new Performance[artistNames.length];
        for (int i = 0; i < performances.length; i++) {
            performances[i] = new Performance(new Artist(artistNames[i]));
        }
        return performances;
    }
}