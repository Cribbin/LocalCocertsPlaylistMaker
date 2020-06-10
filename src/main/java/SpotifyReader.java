import io.mikael.urlbuilder.UrlBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SpotifyReader {
    private static final String PROTOCOL = "https";
    private static final String SPOTIFY_HOST = "api.spotify.com";
    private static final String SEARCH_PATH = "v1/search";
    private static final String SEARCH_PARAM = "q";
    private static final String TYPE_PARAM = "type";
    private static final String ARTIST_TOP_TRACKS_PATH = "v1/artists/%s/top-tracks";
    private static final String MARKET_PARAM = "market";

    private final HttpClient httpClient;

    public SpotifyReader() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public List<ArtistDetail> searchArtists(List<String> artistNames, String token) throws IOException, InterruptedException {

        List<ArtistDetail> artistDetails = new ArrayList<>();

        for (String artistName : artistNames) {
            artistName = artistName.replace(" ", "+");
            URI uri = UrlBuilder.empty()
                    .withScheme(PROTOCOL)
                    .withHost(SPOTIFY_HOST)
                    .withPath(SEARCH_PATH)
                    .addParameter(SEARCH_PARAM, artistName)
                    .addParameter(TYPE_PARAM, "artist")
                    .toUri();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .timeout(Duration.ofMinutes(1))
                    .header("Authorization", String.format("Bearer %s", token))
                    .GET()
                    .build();

            String response = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();

            JsonParser parser = new JsonParser();
            var parsedJson = parser.parse(response);
            var artists = (LinkedHashMap) parsedJson.get("artists");
            var items = (ArrayList) artists.get("items");

            if (items.isEmpty()) {
                continue;
            }

            LinkedHashMap artist = (LinkedHashMap) items.get(0);
            String id = (String) artist.get("id");
            String name = (String) artist.get("name");
            int popularity = (Integer) artist.get("popularity");

            artistDetails.add(new ArtistDetail(id, name, popularity));
        }

        return artistDetails.stream().sorted(ArtistDetail::compareByPopularity).collect(Collectors.toList());
    }

    public List<SongDetail> getTopSongForArtists(List<ArtistDetail> artists, String token) throws IOException, InterruptedException {
        List<SongDetail> songs = new ArrayList<>();

        for (ArtistDetail artist : artists) {
            String path = String.format(ARTIST_TOP_TRACKS_PATH, artist.getId());

            URI uri = UrlBuilder.empty()
                    .withScheme(PROTOCOL)
                    .withHost(SPOTIFY_HOST)
                    .withPath(path)
                    .addParameter(MARKET_PARAM, "IE")
                    .toUri();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .timeout(Duration.ofMinutes(1))
                    .header("Authorization", String.format("Bearer %s", token))
                    .GET()
                    .build();

            String response = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();

            JsonParser parser = new JsonParser();
            var parsedJson = parser.parse(response);

            var tracks = (ArrayList<Object>) parsedJson.get("tracks");

            if (tracks.isEmpty()) {
                continue;
            }

            var topTrack = (LinkedHashMap<String, Object>) tracks.get(0);
            var id = (String) topTrack.get("id");
            var name = (String) topTrack.get("name");

            songs.add(new SongDetail(id, name, artist));
        }

        return songs;
    }
}
