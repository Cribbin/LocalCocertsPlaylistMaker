package auth;

import http.HttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Properties;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SpotiyAuthenticatorTest {
    private static final String ACCESS_TOKEN_VALUE = "abcdef123456";

    @Mock
    private HttpClient<SpotifyApiTokenResponse> httpClient;

    @Mock
    private Properties properties;

    private SpotifyAuthenticator authenticator;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        authenticator = new SpotifyAuthenticator(httpClient, properties);
    }

    @Test
    public void shouldReturnAccessToken() {
        when(httpClient.post(any(), any(), any(), any())).thenReturn(bearerToken());
        String token = authenticator.getAccessToken();
        assertEquals(ACCESS_TOKEN_VALUE, token);
    }

    @Test
    public void shouldThrowExceptionIfNoToken() {
        when(httpClient.post(any(), any(), any(), any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> authenticator.getAccessToken());
    }

    private static Optional<SpotifyApiTokenResponse> bearerToken() {
        return Optional.of(new SpotifyApiTokenResponse(ACCESS_TOKEN_VALUE, "bearer", 3600, ""));
    }
}
