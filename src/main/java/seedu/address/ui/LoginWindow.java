package seedu.address.ui;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.events.security.SuccessfulLoginEvent;
import seedu.address.commons.events.security.UnsuccessfulLoginEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.security.Security;

/***
 * Controller for the login
 */
public class LoginWindow extends UiPart<Stage> {

    private static final String FXML = "LoginWindow.fxml";
    private Security user;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label label;

    /**
     * Creates a new LoginWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public LoginWindow(Stage root) {
        super(FXML, root);


        root.setTitle("Login");
        root.initModality(Modality.APPLICATION_MODAL);
        root.setMinWidth(250);

    }

    /**
     * Creates a new LoginWindow.
     */
    public LoginWindow(Security user) {
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
        //logger.fine("Showing help page about the application.");
        getRoot().show();
    }

    /***
     * Hides the Login Window
     */
    public void hide() {
        //logger.fine("Showing help page about the application.");
        getRoot().hide();
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
     * Runs whenever the login button is clicked
     */
    public void handleLoginClick() {
        user.login(usernameTextField.getText(), passwordTextField.getText());
    }

    @Subscribe
    public void handleSuccessfulLoginEvent(SuccessfulLoginEvent loginSuccess) {
        getRoot().hide();
    }

    @Subscribe
    public void handleUnsuccessfulLoginEvent(UnsuccessfulLoginEvent loginFailure) {
        label.setText("Incorrect Username/Password");
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }
}
