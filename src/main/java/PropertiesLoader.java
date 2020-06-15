import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesLoader {
    private static final String PROPERTIES_FILE_PATH = "./src/main/resources/config.properties";

    private PropertiesLoader() {
    }

    public static Properties load() throws IOException {
        String path = Paths.get(PROPERTIES_FILE_PATH).normalize().toAbsolutePath().toString();

        Properties properties = new Properties();

        try (InputStream input = new FileInputStream(path)) {
            properties.load(input);
        } catch (FileNotFoundException e) {
            System.err.println(String.format("Properties file not found: %s", path));
            throw e;
        } catch (IOException e) {
            System.err.println(String.format("Error loading file: %s", path));
            throw e;
        }

        return properties;
    }
}
