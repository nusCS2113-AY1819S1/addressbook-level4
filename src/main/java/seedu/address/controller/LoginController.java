package seedu.address.controller;

//@@author tianhang
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import seedu.address.authentication.PasswordUtils;
import seedu.address.commons.core.CurrentUser;
import seedu.address.model.LoginInfoManager;
import seedu.address.ui.Ui;


/**
 * Manger event on LoginPage.fxml
 */
public class LoginController {
    protected static Ui ui;
    protected static LoginInfoManager loginInfoManager;
    @FXML
    private javafx.scene.control.TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private javafx.scene.control.Label loginError;

    public void getLoginInfoList (LoginInfoManager loginInfoManager){
        this.loginInfoManager = loginInfoManager;
    }
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
     * handle when user press enter on login textfield or passwordField
     * @param key the key enter by user
     */
    public void handleEnterPressed(KeyEvent key){
        if( key.getCode() == KeyCode.ENTER ){
            verifyLoginInfo ();
        }
    }
    /**
     * handle event when Login is clicked
     * @param clicked mouse event clicked
     */
    public void handleLoginButtonClick( ActionEvent clicked){
        verifyLoginInfo ();
    }
    /**
     * Check for Login information such as username and password
     * @throws Exception
     */
    @FXML
    public void verifyLoginInfo (){

        String username = usernameField.getText();
        String password = passwordField.getText();
        username = username.trim ();
        password = password.trim ();
        if (username.equals ("")){
            loginError.setText("Please enter username");
            System.out.println("Please enter username");
            return;
        }
        if (password.equals ("")){
            loginError.setText("Please enter password");
            System.out.println("Please enter password");
            return;
        }

        String providedPassword = password;
        String securePassword = loginInfoManager.getLoginInfo (username).getPassword ();


        boolean passwordMatch = PasswordUtils.verifyUserPassword(providedPassword, securePassword);
        boolean usernameMatch = username.matches("tianhang|minjia|scoot|xuanhao");
        if (passwordMatch && usernameMatch) {
            Stage stageTheLabelBelongs = (Stage) passwordField.getScene().getWindow();
            stageTheLabelBelongs.close();
            CurrentUser currentUser = new CurrentUser (loginInfoManager.getLoginInfo (username).getUserName (),
                                                        loginInfoManager.getLoginInfo (username).getAuthenticationLevel ());
            ui.start(stageTheLabelBelongs);

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
