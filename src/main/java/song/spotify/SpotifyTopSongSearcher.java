package song.spotify;

import artist.Artist;
import http.HttpClient;
import song.Song;
import song.TopSongSearcher;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SpotifyTopSongSearcher implements TopSongSearcher {
    private static final String URL = "https://api.spotify.com/v1/artists/%s/top-tracks?market=%s";

    private final HttpClient<SpotifyTopSongResponse> httpClient;
    private final String accessToken;
    private final String location;

    public SpotifyTopSongSearcher(HttpClient<SpotifyTopSongResponse> httpClient, String accessToken, String location) {
        this.httpClient = httpClient;
        this.accessToken = accessToken;
        this.location = location;
    }

    @Override
    public List<Song> getTopSongsFor(List<Artist> artists) {
        return artists.stream()
                .map(this::getTopSongFor)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<Song> getTopSongFor(Artist artist) {
        String url = String.format(URL, artist.getId(), location);

        String authToken = String.format("Bearer %s", accessToken);
        Map<String, String> headerParams = Collections.singletonMap("Authorization", authToken);

        Optional<SpotifyTopSongResponse> response = httpClient.getWithHeader(
                url,
                SpotifyTopSongResponse.class,
                headerParams);

        if (response.isEmpty() || response.get().getTracks() == null || response.get().getTracks().length == 0) {
            return Optional.empty();
        }

        return Optional.of(extractTopSongFrom(response.get(), artist));
    }

    private static Song extractTopSongFrom(SpotifyTopSongResponse response, Artist artist) {
        SpotifyTopSongResponse.Track topTrack = response.getTracks()[0];
        return new Song(topTrack.getId(), topTrack.getName(), artist);
    }
}
