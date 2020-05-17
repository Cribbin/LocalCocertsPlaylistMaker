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
}
