import java.util.Objects;

public class Event {
    private final String artist;
    private final double popularity;

    public Event(String artist, double popularity) {
        this.artist = artist;
        this.popularity = popularity;
    }

    public String getArtist() {
        return artist;
    }

    public double getPopularity() {
        return popularity;
    }

    public int compareByPopularity(Event other) {
        if (this.getPopularity() == other.getPopularity()) {
            return 0;
        } else if (this.getPopularity() < other.getPopularity()) {
            return -1;
        }
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return artist.equals(event.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist);
    }
}
