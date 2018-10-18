package seedu.address.storage;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A class to access UserAccount stored in the hard disk as a JSON file
 */
public class JsonUserStorage {

    private Path folderPath;
    private String filePathString;

    public JsonUserStorage(Path folderPath, Path filePath) throws IOException {
        this.folderPath = folderPath;
        filePathString = "./" + filePath.toString();

        if (Files.notExists(filePath)) {
            createUserFile();
        }
    }

    /**
     * Returns the user account JSON as a hash map JSON object.
     */
    public JsonObject getUserAccounts() throws IOException {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(new FileReader(filePathString));

        return jsonElement.getAsJsonObject();
    }

    /**
     * Creates a user account JSON file.
     */
    private void createUserFile() throws IOException {
        Files.createDirectory(folderPath);

        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("admin", "root");

        String json = gson.toJson(jsonObject);
        FileWriter file = new FileWriter(filePathString);
        file.write(json);
        file.flush();
    }
}
