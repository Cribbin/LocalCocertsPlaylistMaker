package artist;

import java.util.Objects;

public class Artist {
    private final String id;
    private final String name;
    private final int popularity;

    public Artist(String id, String name, int popularity) {
        this.id = id;
        this.name = name;
        this.popularity = popularity;
    }

    public String getId() {
        return id;
    }

    public int getPopularity() {
        return popularity;
    }

    public int compareByPopularity(Artist other) {
        if (this.getPopularity() == other.getPopularity()) {
            return 0;
        } else if (this.getPopularity() < other.getPopularity()) {
            return -1;
        }
        return 1;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", popularity=" + popularity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return popularity == artist.popularity &&
                Objects.equals(id, artist.id) &&
                Objects.equals(name, artist.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, popularity);
    }

}
