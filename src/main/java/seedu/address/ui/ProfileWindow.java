package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a profile page
 */
public class ProfileWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(ProfileWindow.class);
    private static final String FXML = "ProfileWindow.fxml";
    private static final String userProfileUrl = "file:" + System.getProperty("user.dir") + "\\ProfileWindow.html";

    @FXML
    private WebView browser;

    /**
     * Creates a new ProfileWindow.
     *
     * @param root Stage to use as the root of the ProfileWindow.
     */
    public ProfileWindow(Stage root) {

        super(FXML, root);

        browser.getEngine().load(userProfileUrl);
    }

    /**
     * Creates a new ProfileWindow.
     */
    public ProfileWindow() {
        this(new Stage());
    }

    /**
     * Shows the profile window.
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
        logger.fine("Showing profile page about the application.");
        browser.getEngine().load(userProfileUrl);

        getRoot().show();
    }

    /**
     * Returns true if the profile window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the profile window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
