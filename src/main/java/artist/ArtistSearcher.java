package artist;

import java.util.List;
import java.util.Set;

public interface ArtistSearcher {
    /**
     * Searches for artist by artist name
     */
    List<Artist> findArtists(Set<String> artistNames);
}
