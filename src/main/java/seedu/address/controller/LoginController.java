package seedu.address.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import seedu.address.authentication.PasswordUtils;

import java.io.IOException;
import java.net.URL;
import static java.util.Objects.requireNonNull;
import static seedu.address.MainApp.ui;

public class LoginController {
    private final FXMLLoader fxmlLoader = new FXMLLoader();
    public static final String FXML_FILE_FOLDER = "/view/";
   // private Ui ui;
    @FXML
    private javafx.scene.control.TextField usernameF;
    @FXML
    private PasswordField passwordF;
    @FXML
    private javafx.scene.control.Label label;
    @FXML
    private Button button;
    @FXML
    public void handleButtonAction(ActionEvent e) throws Exception{

        String username = usernameF.getText();
        String password = passwordF.getText();
        System.out.println(username);
        System.out.println(password);

        String providedPassword = password;

        // Encrypted and Base64 encoded password read from database
        String securePassword = "HhaNvzTsVYwS/x/zbYXlLOE3ETMXQgllqrDaJY9PD/U=";

        // Salt value stored in database
        String salt = "EqdmPh53c9x33EygXpTpcoJvc4VXLK";

        boolean passwordMatch = PasswordUtils.verifyUserPassword(providedPassword, securePassword, salt);
        boolean usernameMatch = username.equals("tianhang");
        if(passwordMatch && usernameMatch)
        {
            Stage stageTheLabelBelongs = (Stage) passwordF.getScene().getWindow();
            stageTheLabelBelongs.close();
            ui.start(stageTheLabelBelongs);
        } else {
            label.setText("wrong password");
            System.out.println("Provided password is incorrect");
        }



    }
    private Parent loadFxmlFile(URL location, Stage root) {
        requireNonNull(location);
        fxmlLoader.setLocation(location);
        Parent rooting = null;
        try {
            rooting = fxmlLoader.load();

        } catch (IOException e) {
            System.out.println("the exception is " + e);
            //throw new AssertionError(e);
        }
        return rooting;
    }
    @FXML
    private void handleClose(MouseEvent e){
        System.exit(0);
    }


}
