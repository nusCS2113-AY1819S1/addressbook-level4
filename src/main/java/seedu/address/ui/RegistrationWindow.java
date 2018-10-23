package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.events.ui.ExitRegisterEvent;
import seedu.address.commons.events.ui.SuccessfulRegisterEvent;
import seedu.address.security.Security;

/***
 * Controller for the login
 */
public class RegistrationWindow extends UiPart<Stage> {

    private static final String FXML = "RegistrationWindow.fxml";
    private Security user;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private Label label;
    private Integer registerFlag;

    /**
     * Creates a new Registration Window.
     *
     * @param root Stage to use as the root of the Registration Window.
     */
    public RegistrationWindow(Stage root) {
        super(FXML, root);


        root.setTitle("Register");
        root.initModality(Modality.APPLICATION_MODAL);
        root.setMinWidth(250);

    }

    /**
     * Creates a new Registration Window.
     */
    public RegistrationWindow(Security user) {
        this(new Stage());
        this.user = user;
        //Links with eventsCenter I believe
        registerAsAnEventHandler(this);
    }

    /**
     * Shows the LoginWindow.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        getRoot().show();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /***
     * Hides the Login Window
     */
    public void hide() {
        //logger.fine("Showing help page about the application.");
        getRoot().hide();
    }


    /***
     * Runs whenever the register button is clicked
     */
    public void handleRegister() {
        //TODO
        registerFlag = user.register(usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText(),
             phoneTextField.getText(), addressTextField.getText());
        System.out.println(registerFlag);
        if (registerFlag == 1) {
            raise(new SuccessfulRegisterEvent());
        }
    }



    /**
     * Closes the register window and open the login window
     */
    @FXML
    private void handleExit() {
        raise(new ExitRegisterEvent());
    }

}
