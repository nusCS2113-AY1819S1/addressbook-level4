package seedu.address.ui;

import static seedu.address.authentication.AuthenticationLevelConstant.AUTH_ACCOUNTANT;
import static seedu.address.authentication.AuthenticationLevelConstant.AUTH_ADMIN;
import static seedu.address.authentication.AuthenticationLevelConstant.AUTH_MANAGER;
import static seedu.address.authentication.AuthenticationLevelConstant.AUTH_STOCK_TAKER;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.CurrentUser;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.logic.ChangeHelpWindowEvent;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_FILE_PATH = "/docs/HelpWindow.html";
    public static final String USERGUIDE_FILE_PATH_ADMIN = "/help/AdminHelpWindow.html";
    public static final String USERGUIDE_FILE_PATH_ACCOUNTANT = "/help/AccountantHelpWindow.html";
    public static final String USERGUIDE_FILE_PATH_MANAGER = "/help/ManagerHelpWindow.html";
    public static final String USERGUIDE_FILE_PATH_STOCK_TAKER = "/help/StockTakerHelpWindow.html";
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";
    @FXML
    private WebView browser;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        String fileOpening = setFilePathAccordingToRole();
        String userGuideUrl = getClass().getResource(fileOpening).toString();
        browser.getEngine().load(userGuideUrl);
        registerAsAnEventHandler(this);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help loginWindow.
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
        logger.fine("Showing help page about the application.");
        getRoot().show();
    }

    /**
     * Returns true if the help loginWindow is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the help loginWindow.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Return {@code filepath}based on CurrentUser
     */
    private String setFilePathAccordingToRole() {
        if (CurrentUser.checkAuthenticationLevel (AUTH_ADMIN)) {
            return USERGUIDE_FILE_PATH_ADMIN;
        }
        if (CurrentUser.checkAuthenticationLevel (AUTH_ACCOUNTANT)) {
            return USERGUIDE_FILE_PATH_ACCOUNTANT;
        }
        if (CurrentUser.checkAuthenticationLevel (AUTH_MANAGER)) {
            return USERGUIDE_FILE_PATH_MANAGER;
        }
        if (CurrentUser.checkAuthenticationLevel (AUTH_STOCK_TAKER)) {
            return USERGUIDE_FILE_PATH_STOCK_TAKER;
        }
        return USERGUIDE_FILE_PATH;
    }
    @Subscribe
    private void handleChangeHelpWindowEvent(ChangeHelpWindowEvent event) {
        String fileOpening = setFilePathAccordingToRole();
        String userGuideUrl = getClass().getResource(fileOpening).toString();
        browser.getEngine().load(userGuideUrl);
    }
}
