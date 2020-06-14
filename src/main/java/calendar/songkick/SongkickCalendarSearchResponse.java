package calendar.songkick;

@SuppressWarnings("unused")
public class SongkickCalendarSearchResponse {
    private ResultsPage resultsPage;

    public SongkickCalendarSearchResponse() {}

    public SongkickCalendarSearchResponse(ResultsPage resultsPage) {
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
        private Event[] event;

        public Results() {}

        public Results(Event[] event) {
            setEvent(event);
        }

        public Event[] getEvent() {
            return event;
        }

        public void setEvent(Event[] event) {
            this.event = event;
        }
    }

    public static class Event {
        private String status;
        private Performance[] performance;

        public Event() {}

        public Event(String status, Performance[] performance) {
            setStatus(status);
            setPerformance(performance);
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Performance[] getPerformance() {
            return performance;
        }

        public void setPerformance(Performance[] performance) {
            this.performance = performance;
        }
    }

    public static class Performance {
        private Artist artist;

        public Performance() {}

        public Performance(Artist artist) {
            setArtist(artist);
        }

        public Artist getArtist() {
            return artist;
        }

        public void setArtist(Artist artist) {
            this.artist = artist;
        }
    }

    public static class Artist {
        private String displayName;

        public Artist() {}

        public Artist(String displayName) {
            setDisplayName(displayName);
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
    }
}
