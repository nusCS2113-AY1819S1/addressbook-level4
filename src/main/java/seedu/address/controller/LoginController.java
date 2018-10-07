package seedu.address.controller;

//@@author tianhang
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import seedu.address.authentication.PasswordUtils;
import seedu.address.commons.core.CurrentUser;
import seedu.address.commons.core.LoginInfo;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.LoginInfoManager;
import seedu.address.ui.Ui;



/**
 * Manger event on LoginPage.fxml
 */
public class LoginController {

    protected static Ui ui;
    protected static LoginInfoManager loginInfoManager;
    private final Logger logger = LogsCenter.getLogger(LoginController.class);
    private String username;
    private String password;
    @FXML
    private javafx.scene.control.TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private javafx.scene.control.Label loginError;


    /**
     * handle when user press enter on login textfield or passwordField
     * @param key the key enter by user
     */
    @FXML
    public void handleEnterPressed(KeyEvent key) {
        if (key.getCode() == KeyCode.ENTER) {
            verifyLoginInfo ();
        }
    }
    /**
     * handle event when Login is clicked
     * @param clicked mouse event clicked
     */
    @FXML
    public void handleLoginButtonClick(ActionEvent clicked) {
        verifyLoginInfo ();
    }

    /**
     * Check for Login information such as username and password.
     *
     * @throws Exception
     */
    public void verifyLoginInfo () {
        getUsername();
        getPassword ();
        if (!isUsernameValid () || !isPasswordValid ()) {
            return;
        }
        passwordAndUserNameCheck();
    }

    /**
     * Close window
     *
     * @param e
     */
    @FXML
    private void handleClose(MouseEvent e) {
        Stage stageTheLabelBelongs = (Stage) passwordField.getScene().getWindow();
        stageTheLabelBelongs.close();
    }

    /**
     * gets the user name from textfield.
     */
    private void getUsername() {
        username = usernameField.getText();
        username = username.trim ();
    }

    /**
     * gets the password from passwordField.
     */
    private void getPassword() {
        password = passwordField.getText();
        password = password.trim ();
    }

    /**
     * Returns validity of username
     * If usernameField is empty, return false else return true
     *
     * @return boolean value
     */
    private boolean isUsernameValid() {
        if (username.equals ("")) {
            loginError.setText("Please enter username");
            System.out.println("Please enter username");
            return false;
        }
        return true;
    }
    /**
     * Returns validity of password.
     * If passwordField is empty, return false else return true.
     *
     * @return boolean value
     */
    private boolean isPasswordValid() {
        if (password.equals ("")) {
            loginError.setText("Please enter password");
            System.out.println("Please enter password");
            return false;
        }
        return true;
    }

    /**
     * check the password and username with logininfo list
     */
    private void passwordAndUserNameCheck() {
        LoginInfo userInfoInStorage = loginInfoManager.getLoginInfo (username);
        boolean usernameMatch = username.matches (userInfoInStorage.getUserName ());

        String securePassword = userInfoInStorage.getPassword ();
        boolean passwordMatch = PasswordUtils.verifyUserPassword(password, securePassword);

        if (passwordMatch && usernameMatch) {
            CurrentUser.setLoginInfo (username, userInfoInStorage.getAuthenticationLevel ());
            logger.info (String.format ("User has login with user name : " + CurrentUser.getUserName ()));
            changeStageToMainUi(userInfoInStorage);
        } else {
            loginError.setText("wrong password");
        }
    }

    /**
     *
     */
    private void changeStageToMainUi(LoginInfo userInfoInStorage) {
        Stage primaryStage = (Stage) passwordField.getScene().getWindow();
        primaryStage.close();
        ui.start(primaryStage);
    }

    /**
     * pass in LoginInfo list
     * @param loginInfoManager
     */
    public void getLoginInfoList (LoginInfoManager loginInfoManager) {
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


}
