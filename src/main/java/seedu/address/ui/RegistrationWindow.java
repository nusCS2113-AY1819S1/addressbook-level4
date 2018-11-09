package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.events.ui.ExitRegisterEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.RegisterCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.security.SecurityAuthenticationException;

/***
 * Controller for the login
 */
public class RegistrationWindow extends UiPart<Stage> {

    private static final String FXML = "RegistrationWindow.fxml";
    private Logic logic;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private Label label;

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
    public RegistrationWindow(Logic logic) {
        this(new Stage());
        this.logic = logic;
        //Links with eventsCenter I believe
        registerAsAnEventHandler(this);
        label.setText(" ");
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
        try {
            logic.execute(RegisterCommand.COMMAND_WORD + " " + PREFIX_USERNAME + usernameTextField.getText()
                    + " " + PREFIX_PASSWORD + passwordTextField.getText() + " " + PREFIX_EMAIL
                    + emailTextField.getText() + " " + PREFIX_PHONE + phoneTextField.getText() + " "
                    + PREFIX_ADDRESS + addressTextField.getText());
        } catch (CommandException e) {
            label.setText(e.getMessage());
        } catch (ParseException e) {
            label.setText(e.getMessage());
        } catch (SecurityAuthenticationException e) {
            label.setText(e.getMessage());
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
