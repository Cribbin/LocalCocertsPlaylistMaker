package song;

import artist.Artist;

import java.util.List;

public interface TopSongSearcher {
    /**
     * Returns a list of the most popular {@link Song} for each supplied {@link Artist}
     */
    List<Song> getTopSongsFor(List<Artist> artists);
}
