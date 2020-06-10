public class ArtistDetail {
    private String id;
    private String name;
    private int popularity;

    public ArtistDetail(String id, String name, int popularity) {
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

    public int compareByPopularity(ArtistDetail other) {
        if (this.getPopularity() == other.getPopularity()) {
            return 0;
        } else if (this.getPopularity() < other.getPopularity()) {
            return -1;
        }
        return 1;
    }

    @Override
    public String toString() {
        return "ArtistDetail{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", popularity=" + popularity +
                '}';
    }
}
