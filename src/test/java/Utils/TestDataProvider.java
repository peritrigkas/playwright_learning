package Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class TestDataProvider {

    // Reusable: reads a JSON array of objects into one map per object
    public static List<Map<String, String>> readJson(String filePath) throws IOException {
        String json = Files.readString(Path.of(filePath));
        return new Gson().fromJson(json, new TypeToken<List<Map<String, String>>>() {}.getType());
    }
}
