import calendar.CalendarRetriever;
import calendar.songkick.SongkickCalendarRetriever;
import calendar.songkick.SongkickLocationRetriever;
import http.DefaultHttpClient;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        SongkickLocationRetriever songkickLocationRetriever = new SongkickLocationRetriever(new DefaultHttpClient<>());

        String locationId = songkickLocationRetriever.getMetroAreaIdFor("London").orElseThrow();

        CalendarRetriever calendarRetriever = new SongkickCalendarRetriever(new DefaultHttpClient<>());

        Set<String> artistsPlaying = calendarRetriever.getArtistsPlayingIn(locationId);

        System.out.println(artistsPlaying);
    }
}
