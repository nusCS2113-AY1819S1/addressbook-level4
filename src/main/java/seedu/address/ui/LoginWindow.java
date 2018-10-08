package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.events.ui.ExitAppRequestEvent;

/***
 * Controller for the login
 */
public class LoginWindow extends UiPart<Stage> {

    private static final String FXML = "LoginWindow.fxml";

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
    public LoginWindow() {
        this(new Stage());
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

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }
}
