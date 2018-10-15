package seedu.address.storage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonUserStorage {

    private Path folderPath;
    private Path filePath;

    public JsonUserStorage(Path folderPath, Path filePath) throws IOException {
        this.folderPath = folderPath;
        this.filePath = filePath;
        createUserFile();
    }

    public JSONObject getUserAccounts() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader(("./" + filePath.toString())));
        return (JSONObject) object;
    }

    private void createUserFile() throws IOException {
        if (!Files.exists(filePath)) {
            Files.createDirectory(folderPath);
            Files.createFile(filePath);
        }

        JSONObject object = new JSONObject();
        object.put("admin", "root");

        FileWriter file = new FileWriter("./" + filePath.toString());
        file.write(object.toJSONString());
        file.flush();
    }
}
