package http;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class DefaultHttpClient<T> implements HttpClient<T> {
    private final java.net.http.HttpClient httpClient;

    public DefaultHttpClient() {
        this.httpClient = java.net.http.HttpClient.newHttpClient();
    }

    @Override
    public Optional<T> get(String url, Class<T> responseClass) {
        return getWithHeader(url, responseClass, Collections.emptyMap());
    }

    @Override
    public Optional<T> getWithHeader(String url, Class<T> responseClass, Map<String, String> headerParameters) {
        try {
            var request = jsonRequestBuilder(url, headerParameters)
                    .GET()
                    .build();

            String response = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();

            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return Optional.of(mapper.readValue(response, responseClass));

        } catch (Exception e) {
            // TODO Add error handling
            return Optional.empty();
        }
    }

    @Override
    public Optional<T> post(String url, Class<T> responseClass, Map<String, String> bodyParameters, Map<String, String> headerParameters) {
        var request = xWwwFormRequestBuilder(url, headerParameters)
                .POST(ofFormData(bodyParameters))
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

    public static HttpRequest.BodyPublisher ofFormData(Map<String, String> data) {
        var builder = new StringBuilder();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

    private static HttpRequest.Builder jsonRequestBuilder(String url, Map<String, String> headerParams) {
        return requestBuilder(url, "application/json", headerParams);
    }

    private static HttpRequest.Builder xWwwFormRequestBuilder(String url, Map<String, String> headerParams) {
        return requestBuilder(url, "application/x-www-form-urlencoded", headerParams);
    }

    private static HttpRequest.Builder requestBuilder(String url, String contentType, Map<String, String> headerParams) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", contentType);

        for (Map.Entry<String, String> param : headerParams.entrySet()) {
            requestBuilder = requestBuilder.header(param.getKey(), param.getValue());
        }

        return requestBuilder;
    }
}
