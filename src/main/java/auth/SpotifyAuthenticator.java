package auth;

import http.HttpClient;

import java.util.Base64;
import java.util.Collections;
import java.util.Properties;

public class SpotifyAuthenticator {
    private static final String URL = "https://accounts.spotify.com/api/token";
    private final HttpClient<SpotifyApiTokenResponse> httpClient;
    private final String encodedKey;

    public SpotifyAuthenticator(HttpClient<SpotifyApiTokenResponse> httpClient, Properties properties) {
        this.httpClient = httpClient;
        String clientId = properties.getProperty("spotify-client-id");
        String clientSecret = properties.getProperty("spotify-client-secret");
        this.encodedKey = encodeKeys(clientId, clientSecret);
    }

    public String getAccessToken() {
        var bodyParameters = Collections.singletonMap("grant_type", "client_credentials");
        var headerParameters = Collections.singletonMap("Authorization", encodedKey);
        var response = httpClient.post(URL, SpotifyApiTokenResponse.class, bodyParameters, headerParameters);

        return response.orElseThrow().getAccess_token();
    }

    private static String encodeKeys(String clientId, String clientSecret) {
        String keys = String.format("%s:%s", clientId, clientSecret);
        String base64ClientId = Base64.getEncoder().encodeToString(keys.getBytes());
        return String.format("Basic %s", base64ClientId);
    }
}
