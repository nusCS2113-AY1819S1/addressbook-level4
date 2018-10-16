package seedu.address.storage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * A class to access UserAccount stored in the hard disk as a JSON file
 */
public class JsonUserStorage {

    private Path folderPath;
    private Path filePath;
    private String folderPathString;

    public JsonUserStorage(Path folderPath, Path filePath) throws IOException {
        this.filePath = filePath;
        this.folderPath = folderPath;
        folderPathString = "./" + folderPath.toString();

        if (Files.notExists(filePath)) {
            createUserFile();
        }
    }

    /**
     * Returns the user account JSON as a hash map JSON object.
     */
    public JsonObject getUserAccounts() throws IOException {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(new FileReader(folderPathString));

        return jsonElement.getAsJsonObject();
    }

    /**
     * Creates a user account JSON file.
     */
    private void createUserFile() throws IOException {
        Files.createDirectory(folderPath);
        Files.createFile(filePath);

        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("admin", "root");

        String json = gson.toJson(jsonObject);
        FileWriter file = new FileWriter(folderPathString);
        file.write(json);
        file.flush();
    }
}
