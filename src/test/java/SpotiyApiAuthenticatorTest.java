import auth.SpotifyApiAuthenticator;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class SpotiyApiAuthenticatorTest {
    @Test
    public void test() throws FileNotFoundException {
        System.out.println(SpotifyApiAuthenticator.getAccessToken());
    }
}
