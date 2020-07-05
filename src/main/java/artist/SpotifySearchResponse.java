package artist;

import java.util.List;

@SuppressWarnings("unused")
public class SpotifySearchResponse {
    private Artist artists;

    public SpotifySearchResponse() {
    }

    public SpotifySearchResponse(Artist artists) {
        setArtists(artists);
    }

    public Artist getArtists() {
        return artists;
    }

    public void setArtists(Artist artists) {
        this.artists = artists;
    }

    public static class Artist {
        private List<Item> items;

        public Artist() {
        }

        public Artist(List<Item> items) {
            setItems(items);
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }
    }

    public static class Item {
        private String id;
        private String name;
        private int popularity;
        private String type;

        public Item() {
        }

        public Item(String id, String name, int popularity, String type) {
            setId(id);
            setName(name);
            setPopularity(popularity);
            setType(type);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPopularity() {
            return popularity;
        }

        public void setPopularity(int popularity) {
            this.popularity = popularity;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        private String uri;
    }
}
