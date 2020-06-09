package auth;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Base64;
import java.util.Scanner;

public class SpotifyApiAuthenticator {
    private static final String CLIENT_ID_FILEPATH = "src/main/resources/spotify_client_id.txt";
    private static final String CLIENT_SECRET_FILEPATH = "src/main/resources/spotify_client_secret.txt";

    private SpotifyApiAuthenticator() {}

    public static String getAccessToken() throws FileNotFoundException {
        String clientId = loadFile(CLIENT_ID_FILEPATH);
        String clientSecret = loadFile(CLIENT_SECRET_FILEPATH);

        String authValue = String.format("Basic %s:%s", clientId, clientSecret);

        return Base64.getEncoder().encodeToString(authValue.getBytes());
    }

    private static String loadFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);

        Scanner scanner = new Scanner(file);

        if (!scanner.hasNextLine()) {
            throw new FileNotFoundException("File " + filePath + " is empty.");
        }

        return scanner.nextLine();
    }
}
