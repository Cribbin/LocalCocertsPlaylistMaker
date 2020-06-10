public class SongDetail {
    private String id;
    private String name;
    private ArtistDetail artist;

    public SongDetail(String id, String name, ArtistDetail artist) {
        this.id = id;
        this.name = name;
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "SongDetail{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", artist=" + artist +
                '}';
    }
}
