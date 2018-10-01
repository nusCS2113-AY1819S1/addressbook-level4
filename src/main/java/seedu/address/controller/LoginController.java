package seedu.address.controller;

//@@author tianhang
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import seedu.address.authentication.PasswordUtils;
import seedu.address.ui.Ui;


/**
 * Manger event on LoginPage.fxml
 */
public class LoginController {

    protected static Ui ui;
    @FXML
    private javafx.scene.control.TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private javafx.scene.control.Label loginError;


    private final FXMLLoader fxmlLoader = new FXMLLoader();

    /**
     * set ui as mainWindow ui of address book
     * @param ui
     */
    public void mainWindowInterface (Ui ui) {
        if (ui == null) {
            System.out.println ("ui is null");
        }
        this.ui = ui;
    }

    /**
     * Check for password when login is clicked
     * @throws Exception
     */
    @FXML
    public void handleButtonAction(ActionEvent e) throws Exception {

        String username = usernameField.getText();
        String password = passwordField.getText();
        System.out.println(username);
        System.out.println(password);

        String providedPassword = password;

        //Encrypted and Base64 encoded password read from database
        //will change this to a array of key value pair
        String securePassword = "HhaNvzTsVYwS/x/zbYXlLOE3ETMXQgllqrDaJY9PD/U=";

        //Salt value stored in database
        String salt = "EqdmPh53c9x33EygXpTpcoJvc4VXLK";

        boolean passwordMatch = PasswordUtils.verifyUserPassword(providedPassword, securePassword, salt);
        boolean usernameMatch = username.matches("tianhang|minjia|scoot|xuanhao");
        if (passwordMatch && usernameMatch) {
            Stage stageTheLabelBelongs = (Stage) passwordField.getScene().getWindow();
            stageTheLabelBelongs.close();
            try {
                ui.start(stageTheLabelBelongs);
            } catch (Exception e1) {
                System.out.println ("the exception is " + e1);
                e1.printStackTrace ();
            }
        } else {
            loginError.setText("wrong password");
            System.out.println("Provided password is incorrect");
        }



    }

    /**
     * Close window
     * @param e
     */
    @FXML
    private void handleClose(MouseEvent e) {
        Stage stageTheLabelBelongs = (Stage) passwordField.getScene().getWindow();
        stageTheLabelBelongs.close();
    }


}
