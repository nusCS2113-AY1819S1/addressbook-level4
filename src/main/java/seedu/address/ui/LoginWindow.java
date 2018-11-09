package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.events.security.SuccessfulLoginEvent;
import seedu.address.commons.events.security.UnsuccessfulLoginEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.ShowRegisterEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.security.SecurityAuthenticationException;

/***
 * Controller for the login
 */
public class LoginWindow extends UiPart<Stage> {

    private static final String FXML = "LoginWindow.fxml";
    private Logic logic;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
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
    public LoginWindow(Logic logic) {
        this(new Stage());
        this.logic = logic;
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
        try {
            logic.execute(LoginCommand.COMMAND_WORD + " " + PREFIX_USERNAME + usernameTextField.getText()
                    + " " + PREFIX_PASSWORD + passwordTextField.getText());
        } catch (CommandException | ParseException | SecurityAuthenticationException e) {
            label.setText(e.getMessage());
        }
    }


    /***
     * Runs whenever the login button is clicked
     */
    public void handleRegisterClick() {
        //Raise Event to create new window
        raise(new ShowRegisterEvent());
    }

    @Subscribe
    public void handleSuccessfulLoginEvent(SuccessfulLoginEvent loginSuccess) {
        getRoot().hide();
    }

    @Subscribe
    public void handleUnsuccessfulLoginEvent(UnsuccessfulLoginEvent loginFailure) {
        label.setText("Incorrect Username/Password");
    }

    @Subscribe
    public void handleNewResultAvailableEvent(NewResultAvailableEvent e) {
        //Prevents this message from showing on login box, but only on ResultDisplay
        if (!e.message.equals("Successfully Displayed UI")) {
            label.setText(e.message);
        }
    }
}
