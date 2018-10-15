package seedu.address.storage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import seedu.address.model.user.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonUserStorage {

    private Path filePath;

    public JsonUserStorage(Path filePath) throws IOException {
        this.filePath = filePath;
        createUserFile();
    }

    public boolean userExists(User user) {
        String loggedUsername = user.getUsername().toString();
        String loggedPassword = user.getPassword().toString();
        JSONParser parser = new JSONParser();
        boolean isPresent = false;

        try {
            Object object = parser.parse(new FileReader("./" + filePath.toString()));

            JSONObject jsonObject = (JSONObject) object;
            String password = (String) jsonObject.get(loggedUsername);
            isPresent = password.equals(loggedPassword);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return isPresent;
    }

    private void createUserFile() throws IOException {
        Path folder = Paths.get("data");
        if (!Files.exists(filePath)) {
            Files.createDirectory(folder);
            Files.createFile(filePath);
        }

        JSONObject object = new JSONObject();
        object.put("admin", "root");

        FileWriter file = new FileWriter("./" + filePath.toString());
        file.write(object.toJSONString());
        file.flush();
    }
}
