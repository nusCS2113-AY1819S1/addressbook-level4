package seedu.address.controller;

import static java.util.Objects.requireNonNull;
import static seedu.address.MainApp.ui;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import seedu.address.authentication.PasswordUtils;



/**
 * Manger event on LoginPage.fxml
 */
public class LoginController {


    @FXML
    private javafx.scene.control.TextField usernameF;
    @FXML
    private PasswordField passwordF;
    @FXML
    private javafx.scene.control.Label label;

    private final FXMLLoader fxmlLoader = new FXMLLoader();

    /**
     * Check for password when login is clicked
     * @param e
     * @throws Exception
     */
    @FXML
    public void handleButtonAction(ActionEvent e) throws Exception {

        String username = usernameF.getText();
        String password = passwordF.getText();
        System.out.println(username);
        System.out.println(password);

        String providedPassword = password;

        //Encrypted and Base64 encoded password read from database
        String securePassword = "HhaNvzTsVYwS/x/zbYXlLOE3ETMXQgllqrDaJY9PD/U=";

        //Salt value stored in database
        String salt = "EqdmPh53c9x33EygXpTpcoJvc4VXLK";

        boolean passwordMatch = PasswordUtils.verifyUserPassword(providedPassword, securePassword, salt);
        boolean usernameMatch = username.equals("tianhang");
        if(passwordMatch && usernameMatch) { 
            Stage stageTheLabelBelongs = (Stage) passwordF.getScene().getWindow();
            stageTheLabelBelongs.close();
            ui.start(stageTheLabelBelongs);
        } else {
            label.setText("wrong password");
            System.out.println("Provided password is incorrect");
        }



    }

    /**
     * Close window
     * @param e
     */
    @FXML
    private void handleClose(MouseEvent e){
        System.exit(0);
    }


}
