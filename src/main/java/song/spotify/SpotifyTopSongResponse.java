package song.spotify;

@SuppressWarnings("unused")
public class SpotifyTopSongResponse {
    private Track[] tracks;

    public SpotifyTopSongResponse() {
    }

    public SpotifyTopSongResponse(Track[] tracks) {
        setTracks(tracks);
    }

    public Track[] getTracks() {
        return tracks;
    }

    public void setTracks(Track[] tracks) {
        this.tracks = tracks;
    }

    public static class Track {
        private String id;
        private String name;

        public Track() {
        }

        public Track(String id, String name) {
            setId(id);
            setName(name);
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
    }
}
