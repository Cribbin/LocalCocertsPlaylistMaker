package song;

import artist.Artist;

import java.util.Objects;

public class Song {
    private final String id;
    private final String name;
    private final Artist artist;

    public Song(String id, String name, Artist artist) {
        this.id = id;
        this.name = name;
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "song.Song{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", artist=" + artist +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(id, song.id) &&
                Objects.equals(name, song.name) &&
                Objects.equals(artist, song.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, artist);
    }
}
