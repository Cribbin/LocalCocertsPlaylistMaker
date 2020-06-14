package http;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

public class DefaultHttpClient<T> implements HttpClient<T> {
    private final java.net.http.HttpClient httpClient;

    public DefaultHttpClient() {
        this.httpClient = java.net.http.HttpClient.newHttpClient();
    }

    @Override
    public Optional<T> get(String url, Class<T> responseClass) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            String response = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();

            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return Optional.of(mapper.readValue(response, responseClass));

        } catch (Exception e) {
            // TODO Add error handling
            return Optional.empty();
        }
    }
}
