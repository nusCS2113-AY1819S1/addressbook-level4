package seedu.address.controller;

import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import seedu.address.authentication.LoginUtils;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.InitInventoryListEvent;
import seedu.address.commons.events.ui.ChangeMainStageEvent;
import seedu.address.model.LoginInfoManager;



/**
 * Manger event on LoginPage.fxml
 */
public class LoginController {

    protected static LoginInfoManager loginInfoManager;
    private String username = "";
    private String password = "";
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label loginError;
    @FXML
    private TextField commandBox;
    private String commandInput;
    private boolean isPasswordGoingToEnter = false;
    private final Logger logger = LogsCenter.getLogger(LoginController.class);

    @FXML
    public void handleInputFromCommandBox(KeyEvent key){
        //        commandInput = commandBox.getText ();
        //        int caretPosition = commandBox.getCaretPosition();
        //        setUserNameAndPassword();
        //        //commandBox.setText (setPasswordToStar ());
        //        commandBox.positionCaret (caretPosition);
        if (key.getCode() == KeyCode.ENTER) {
            commandInput = commandBox.getText ();
            setUserNameAndPassword();
            verifyLoginInfo ();
        }
    }
    private void setUserNameAndPassword(){
        String[] splited = commandInput.split("\\s");
        int count = 0;
        while( count < splited.length){
            if (count ==0){
                username = splited[count];
            }else if(count ==1){
                password = splited[count];
            }else if(count >1){
                loginError.setText ("Wrong format for username and password");
            }
            count++;
        }
    }
    private String setPasswordToStar(){

        String staredCommand = username + " ";
        int passwordLength = password.length();
        while (passwordLength > 0 ){
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
     * Close loginWindow
     *
     * @param e
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
        LoginUtils loginUtils = new LoginUtils (username, password, loginInfoManager);
        if (!loginUtils.isUsernameEmpty ()) {
            loginError.setText("Please enter username");
            return;
        } else if (!loginUtils.isPasswordEmpty ()) {
            loginError.setText("Please enter password");
            return;
        }
        if (loginUtils.isPasswordAndUserNameValid ()) {
            changeStageToMainUi();
            clearLoginInput();
        } else {
            clearLoginInput();
            loginError.setText("wrong username or password");
        }
    }

    /**
     * Set back the ui into clean state
     */
    private void clearLoginInput(){
        usernameField.setText ("");
        passwordField.setText ("");
        commandBox.setText ("");
        loginError.setText ("");
        username = "";
        password = "";

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
        EventsCenter.getInstance().post(new InitInventoryListEvent ());
        EventsCenter.getInstance().post(new ChangeMainStageEvent (stage));
    }
    /**
     * pass in LoginInfo list
     * @param loginInfoManager
     */
    public void getLoginInfoList (LoginInfoManager loginInfoManager) {
        this.loginInfoManager = loginInfoManager;
    }



}
