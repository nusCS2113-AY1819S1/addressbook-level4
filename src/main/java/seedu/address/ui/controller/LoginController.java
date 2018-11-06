//@@author liu-tianhang
package seedu.address.ui.controller;

import static seedu.address.model.user.Password.MAX_LENGTH_FOR_PASSWORD;
import static seedu.address.model.user.UserName.MAX_LENGTH_FOR_USERNAME;
import static seedu.address.ui.controller.LoginControllerConstantMessage.EMPTY_PASSWORD_MESSAGE;
import static seedu.address.ui.controller.LoginControllerConstantMessage.EMPTY_STRING;
import static seedu.address.ui.controller.LoginControllerConstantMessage.EMPTY_USERNAME_MESSAGE;
import static seedu.address.ui.controller.LoginControllerConstantMessage.MAX_PASSWORD_LENGTH_MESSAGE;
import static seedu.address.ui.controller.LoginControllerConstantMessage.MAX_USERNAME_LENGTH_MESSAGE;
import static seedu.address.ui.controller.LoginControllerConstantMessage.WRONG_FORMAT_FOR_USERNAME_AND_PASSWORD_MESSAGE;
import static seedu.address.ui.controller.LoginControllerConstantMessage.WRONG_USERNAME_AND_PASSWORD_MESSAGE;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import seedu.address.authentication.LoginUtils;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.logic.ChangeHelpWindowEvent;
import seedu.address.commons.events.model.ChangeModelEvent;
import seedu.address.commons.events.model.InitInventoryListEvent;
import seedu.address.commons.events.ui.RestartUiEvent;
import seedu.address.commons.events.ui.StartUiEvent;
import seedu.address.model.LoginInfoManager;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;
import seedu.address.ui.LoginHelpWindow;


/**
 * Manger event on LoginPage.fxml
 */
public class LoginController {

    protected static LoginInfoManager loginInfoManager;
    private static boolean firstTimeLogin = true;
    private String username;
    private String password;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label loginError;
    @FXML
    private TextField commandBox;
    @FXML
    private Text help;

    private String commandInput;
    private final Logger logger = LogsCenter.getLogger(LoginController.class);

    public LoginController() {
        username = "";
        password = "";
    }

    /**
     * handle when help is clicked
     */
    @FXML
    public void handleHelpButtonClicked(MouseEvent event) {
        openHelpWindow();
    }

    private void openHelpWindow() {
        LoginHelpWindow loginHelpWindow = new LoginHelpWindow ();
        loginHelpWindow.show ();
    }

    /**
     * Get the input from the login command box
     * @param key key entered
     */
    @FXML
    public void handleInputFromCommandBox(KeyEvent key) {
        if (key.getCode() == KeyCode.ENTER) {
            commandInput = commandBox.getText ();
            setUserNameAndPassword();
            verifyLoginInfo ();
        }
    }

    /**
     * Set userName and password according to space
     * The string before the first space is username and after is password
     * Error message when space is more than one
     */
    private void setUserNameAndPassword() {
        String[] splited = commandInput.split("\\s");
        int count = 0;
        while (count < splited.length) {
            if (count == 0) {
                username = splited[count];
            } else if (count == 1) {
                password = splited[count];
            } else if (count > 1) {
                loginError.setText (WRONG_FORMAT_FOR_USERNAME_AND_PASSWORD_MESSAGE);
            }
            count++;
        }
    }
    private String setPasswordToStar() {

        String staredCommand = username + " ";
        int passwordLength = password.length();
        while (passwordLength > 0) {
            staredCommand += "*";
            passwordLength--;
        }
        return staredCommand;
    }
    /**
     * handle when user press enter on login textfield or passwordField
     * @param key the key enter by user
     */
    @FXML
    public void handleEnterPressed(KeyEvent key) {
        if (key.getCode() == KeyCode.ENTER) {
            getUsername();
            getPassword ();
            verifyLoginInfo ();
        }
    }
    /**
     * handle event when Login is clicked
     * @param clicked mouse event clicked
     */
    @FXML
    public void handleLoginButtonClick(ActionEvent clicked) {
        getUsername();
        getPassword ();
        verifyLoginInfo ();
    }
    /**
     * Close loginWindow when clicked
     */
    @FXML
    private void handleClose(MouseEvent e) {
        Stage stageTheLabelBelongs = (Stage) passwordField.getScene().getWindow();
        stageTheLabelBelongs.close();
    }


    /**
     * Check for Login information such as username and password.
     *
     * @throws Exception
     */
    public void verifyLoginInfo () {
        if (!isLengthOfUserNameValid () || !isLengthOfPasswordValid ()) {
            return;
        }
        if (!isFormatOfUserNameAndPasswordCorrect ()) {
            return;
        }
        if (isLoginInfoCorrect ()) {
            changeStageToMainUi();
            clearLoginInput();
        } else {
            clearLoginInput();
            loginError.setText(WRONG_USERNAME_AND_PASSWORD_MESSAGE);
        }
    }

    /**
     * Check with storage about the account
     */
    private boolean isLoginInfoCorrect () {
        UserName userName = new UserName (this.username);
        Password password = new Password (this.password);
        LoginUtils loginUtils = new LoginUtils (userName, password, loginInfoManager);

        if (loginUtils.isPasswordAndUserNameValid ()) {
            return true;
        } else {
            return false;
        }
    }
    /**
     *Returns true if the input follow the format of UserName and Password
     */
    private boolean isFormatOfUserNameAndPasswordCorrect () {
        if (!UserName.isValidUserName (this.username)) {
            loginError.setText(EMPTY_USERNAME_MESSAGE);
            return false;
        } else if (!Password.isValidPassword (this.password)) {
            loginError.setText(EMPTY_PASSWORD_MESSAGE);
            return false;
        }
        return true;
    }
    /**
     *Return false if userName length is more than {@code MAX_LENGTH_FOR_USERNAME}
     *
     */
    private boolean isLengthOfUserNameValid() {
        if (username.length () > MAX_LENGTH_FOR_USERNAME) {
            loginError.setText(MAX_USERNAME_LENGTH_MESSAGE);
            return false;
        }
        return true;
    }
    /**
     * Return false if password length is more than {@code MAX_LENGTH_FOR_PASSWORD}
     */
    private boolean isLengthOfPasswordValid() {
        if (password.length () > MAX_LENGTH_FOR_PASSWORD) {
            loginError.setText(MAX_PASSWORD_LENGTH_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Set back the ui into clean state
     */
    private void clearLoginInput() {
        usernameField.setText (EMPTY_STRING);
        passwordField.setText (EMPTY_STRING);
        commandBox.setText (EMPTY_STRING);
        loginError.setText (EMPTY_STRING);
        username = EMPTY_STRING;
        password = EMPTY_STRING;

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
     *change the stage to main UI
     */
    private void changeStageToMainUi() {
        Stage primaryStage = (Stage) passwordField.getScene().getWindow();
        primaryStage.hide();
        Stage stage = new Stage ();
        if (firstTimeLogin) {
            EventsCenter.getInstance ().post (new InitInventoryListEvent ());
            EventsCenter.getInstance ().post (new StartUiEvent (stage));
            firstTimeLogin = false;
        } else {
            EventsCenter.getInstance ().post (new ChangeModelEvent ());
            EventsCenter.getInstance ().post (new ChangeHelpWindowEvent ());
            EventsCenter.getInstance ().post (new RestartUiEvent (stage));
        }

    }
    /**
     * pass in LoginInfo list
     * @param loginInfoManager
     */
    public void getLoginInfoList (LoginInfoManager loginInfoManager) {
        this.loginInfoManager = loginInfoManager;
    }



}
