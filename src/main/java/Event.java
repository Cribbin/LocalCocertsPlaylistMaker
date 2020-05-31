import java.util.stream.Collectors;

public class Event {
    private final String artist;
    private final double popularity;

    public Event(String artist, double popularity) {
        this.artist = artist;
        this.popularity = popularity;
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
}
