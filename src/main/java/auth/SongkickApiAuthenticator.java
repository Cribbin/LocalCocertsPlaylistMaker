package auth;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SongkickApiAuthenticator {
    private static final String API_KEY_FILEPATH = "src/main/resources/songkick_api_key.txt";

    private SongkickApiAuthenticator() { /* Remove public constructor */ }

    public static String getApiKey() throws FileNotFoundException {
        File apiKeyfile = new File(API_KEY_FILEPATH);
        Scanner scanner = new Scanner(apiKeyfile);

        return scanner.nextLine();
    }
}
