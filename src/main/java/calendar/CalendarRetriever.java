package calendar;

import java.util.Set;

public interface CalendarRetriever {
    /**
     * Gets a list of all all artist names playing in area.
     * @param area Area to search for. What this represents varies depends on underlying implementation
     * @return A set of artists names with upcoming concerts
     */
    Set<String> getArtistsPlayingIn(String area);
}
