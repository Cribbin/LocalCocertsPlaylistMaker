import calendar.CalendarRetriever;
import calendar.songkick.SongkickCalendarRetriever;
import calendar.songkick.SongkickLocationRetriever;
import http.DefaultHttpClient;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Properties properties;

        try {
            properties = PropertiesLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        SongkickLocationRetriever songkickLocationRetriever = new SongkickLocationRetriever(new DefaultHttpClient<>(), properties);

        String locationId = songkickLocationRetriever.getMetroAreaIdFor("London").orElseThrow();

        CalendarRetriever calendarRetriever = new SongkickCalendarRetriever(new DefaultHttpClient<>(), properties);

        Set<String> artistsPlaying = calendarRetriever.getArtistsPlayingIn(locationId);

        System.out.println(artistsPlaying);
    }
}
