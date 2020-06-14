package calendar.songkick;

@SuppressWarnings("unused")
public class SongkickLocationSearchResponse {
    private ResultsPage resultsPage;

    public SongkickLocationSearchResponse() {}

    public SongkickLocationSearchResponse(ResultsPage resultsPage) {
        setResultsPage(resultsPage);
    }

    public ResultsPage getResultsPage() {
        return resultsPage;
    }

    public void setResultsPage(ResultsPage resultsPage) {
        this.resultsPage = resultsPage;
    }

    public static class ResultsPage {
        private String status;
        private Results results;
        private int perPage;
        private int page;
        private int totalEntries;

        public ResultsPage() {}

        public ResultsPage(String status, Results results, int perPage, int page, int totalEntries) {
            setStatus(status);
            setResults(results);
            setPerPage(perPage);
            setPage(page);
            setTotalEntries(totalEntries);
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Results getResults() {
            return results;
        }

        public void setResults(Results results) {
            this.results = results;
        }

        public int getPerPage() {
            return perPage;
        }

        public void setPerPage(int perPage) {
            this.perPage = perPage;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotalEntries() {
            return totalEntries;
        }

        public void setTotalEntries(int totalEntries) {
            this.totalEntries = totalEntries;
        }
    }

    public static class Results {
        private Location[] location;

        public Results() {}

        public Results(Location[] location) {
            setLocation(location);
        }

        public Location[] getLocation() {
            return location;
        }

        public void setLocation(Location[] location) {
            this.location = location;
        }
    }

    public static class Location {
        private City city;
        private MetroArea metroArea;

        public Location() {}

        public Location(City city, MetroArea metroArea) {
            setCity(city);
            setMetroArea(metroArea);
        }

        public City getCity() {
            return city;
        }

        public void setCity(City city) {
            this.city = city;
        }

        public MetroArea getMetroArea() {
            return metroArea;
        }

        public void setMetroArea(MetroArea metroArea) {
            this.metroArea = metroArea;
        }
    }

    public static class City {
        private float lat;
        private float lng;
        private Country country;
        private String displayName;

        public City() {}

        public City(float lat, float lng, Country country, String displayName) {
            setLat(lat);
            setLng(lng);
            setCountry(country);
            setDisplayName(displayName);
        }

        public float getLat() {
            return lat;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }

        public float getLng() {
            return lng;
        }

        public void setLng(float lng) {
            this.lng = lng;
        }

        public Country getCountry() {
            return country;
        }

        public void setCountry(Country country) {
            this.country = country;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
    }

    public static class Country {
        private String displayName;

        public Country() {}

        public Country(String displayName) {
            setDisplayName(displayName);
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
    }

    public static class MetroArea {
        private float lat;
        private float lng;
        private Country country;
        private String uri;
        private String displayName;
        private String id;

        public MetroArea() {}

        public MetroArea(float lat, float lng, Country country, String uri, String displayName, String id) {
            setLat(lat);
            setLng(lng);
            setCountry(country);
            setUri(uri);
            setDisplayName(displayName);
            setId(id);
        }

        public float getLat() {
            return lat;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }

        public float getLng() {
            return lng;
        }

        public void setLng(float lng) {
            this.lng = lng;
        }

        public Country getCountry() {
            return country;
        }

        public void setCountry(Country country) {
            this.country = country;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}

