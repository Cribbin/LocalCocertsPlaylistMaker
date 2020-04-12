import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonParser {
    private final ObjectMapper mapper;
    private final MapType type;

    public JsonParser() {
        mapper = new ObjectMapper();
        type = mapper.getTypeFactory().constructMapType(
                Map.class, String.class, Object.class
        );
    }

    public LinkedHashMap<String, Object> parse(String json) throws IOException {
        return mapper.readValue(json, type);
    }
}
