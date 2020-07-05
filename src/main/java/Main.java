import artist.Artist;
import artist.ArtistSearcher;
import artist.SpotifyArtistSearcher;
import auth.SpotifyAuthenticator;
import calendar.CalendarRetriever;
import calendar.songkick.SongkickCalendarRetriever;
import calendar.songkick.SongkickLocationRetriever;
import http.DefaultHttpClient;
import song.Song;
import song.TopSongSearcher;
import song.spotify.SpotifyTopSongSearcher;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Properties properties;

        try {
            properties = PropertiesLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String locationId = getLocationIdFor("London", properties);
        Set<String> artistNames = getArtistsPlayingIn(locationId, properties);
        String spotifyAccessToken = getSpotifyAccessToken(properties);
        List<Artist> artists = findArtists(artistNames, spotifyAccessToken);
        List<Song> songs = getTopSongsFor(artists, spotifyAccessToken, "IE");
        String playlistUrl = createPlaylistOf(songs);
    }

    private static String getLocationIdFor(String location, Properties properties) {
        SongkickLocationRetriever songkickLocationRetriever = new SongkickLocationRetriever(new DefaultHttpClient<>(), properties);
        return songkickLocationRetriever.getMetroAreaIdFor(location).orElseThrow();
    }

    private static Set<String> getArtistsPlayingIn(String locationId, Properties properties) {
        CalendarRetriever calendarRetriever = new SongkickCalendarRetriever(new DefaultHttpClient<>(), properties);
        return calendarRetriever.getArtistsPlayingIn(locationId);
    }

    private static String getSpotifyAccessToken(Properties properties) {
        SpotifyAuthenticator authenticator = new SpotifyAuthenticator(new DefaultHttpClient<>(), properties);
        return authenticator.getAccessToken();
    }

    private static List<Artist> findArtists(Set<String> artistNames, String spotifyAccessToken) {
        ArtistSearcher spotifyArtistSearcher = new SpotifyArtistSearcher(new DefaultHttpClient<>(), spotifyAccessToken);
        return spotifyArtistSearcher.findArtists(artistNames);
    }

    private static List<Song> getTopSongsFor(List<Artist> artists, String spotifyAccessToken, String region) {
        TopSongSearcher topSongSearcher = new SpotifyTopSongSearcher(new DefaultHttpClient<>(), spotifyAccessToken, region);
        return topSongSearcher.getTopSongsFor(artists);
    }

    private static String createPlaylistOf(List<Song> songs) {
        // TODO
        return "";
    }
}
