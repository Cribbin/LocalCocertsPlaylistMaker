package calendar.songkick;

import http.HttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;
import java.util.Properties;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import static calendar.songkick.SongkickLocationSearchResponse.City;
import static calendar.songkick.SongkickLocationSearchResponse.Country;
import static calendar.songkick.SongkickLocationSearchResponse.Location;
import static calendar.songkick.SongkickLocationSearchResponse.MetroArea;
import static calendar.songkick.SongkickLocationSearchResponse.Results;
import static calendar.songkick.SongkickLocationSearchResponse.ResultsPage;

class SongkickLocationRetrieverTest {
    private static final String AREA_ID_1 = "123ABC";
    private static final String AREA_ID_2 = "456DEF";

    private SongkickLocationRetriever songkickLocationRetriever;

    @Mock
    private HttpClient<SongkickLocationSearchResponse> httpClient;

    @Mock
    private Properties properties;

    @BeforeEach
    void setUp() {
        initMocks(this);
        when(properties.getProperty("songkick-api-key")).thenReturn("any-key");
        songkickLocationRetriever = new SongkickLocationRetriever(httpClient, properties);
    }

    @Test
    public void shouldReturnFirstLocation() {
        when(httpClient.get(any(), any())).thenReturn(sampleResponse());

        Optional<String> areaId = songkickLocationRetriever.getMetroAreaIdFor("Some location");

        assertTrue(areaId.isPresent());
        assertEquals(AREA_ID_1, areaId.get());
    }

    private static Optional<SongkickLocationSearchResponse> sampleResponse() {
        Results results = new Results(locations(AREA_ID_1, AREA_ID_2));
        ResultsPage resultsPage = new ResultsPage("ok", results, 10, 1, 2);
        return Optional.of(new SongkickLocationSearchResponse(resultsPage));
    }

    private static Location[] locations(String... locationIds) {
        Location[] locations = new Location[locationIds.length];
        for (int i = 0; i < locations.length; i++) {
            locations[i] = location(locationIds[i]);
        }
        return locations;
    }

    private static Location location(String locationId) {
        Country country = new Country("Country " + locationId);
        City city = new City(1L, 1L, country, "City " + locationId);
        MetroArea metroArea = new MetroArea(1L, 1L, country, "URI", "MetroArea" + locationId, locationId);

        return new Location(city, metroArea);
    }
}