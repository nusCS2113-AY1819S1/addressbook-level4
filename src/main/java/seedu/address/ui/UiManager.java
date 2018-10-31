package seedu.address.ui;

import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.controlsfx.control.Notifications;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import seedu.address.MainApp;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.storage.DataRestoreExceptionEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.ui.NewNotificationAvailableEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {

    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    public static final String FILE_OPS_ERROR_DIALOG_STAGE_TITLE = "File Op Error";
    public static final String FILE_OPS_ERROR_DIALOG_HEADER_MESSAGE = "Could not save data";
    public static final String FILE_OPS_ERROR_DIALOG_CONTENT_MESSAGE = "Could not save data to file";

    public static final String FILE_OPS_BACKUP_SUCCESS_DIALOG_STAGE_TITLE = "Backup Status";
    public static final String FILE_OPS_BACKUP_SUCCESS_DIALOG_HEADER_MESSAGE = "Backup operation success";
    public static final String FILE_OPS_BACKUP_SUCCESS_DIALOG_CONTENT_MESSAGE = "Data backup to %s";

    public static final String FILE_OPS_RESTORE_ERROR_DIALOG_STAGE_TITLE = "Backup Restore Error";
    public static final String FILE_OPS_RESTORE_ERROR_DIALOG_HEADER_MESSAGE = "Restore operation failed";
    public static final String FILE_OPS_RESTORE_ERROR_DIALOG_CONTENT_MESSAGE = "Could not restore data";

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/student_planner_64.png";

    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private MainWindow mainWindow;

    public UiManager(Logic logic, Config config, UserPrefs prefs) {
        super();
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, config, prefs, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        mainWindow.hide();
        mainWindow.releaseResources();
    }

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + ":\n" + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, FILE_OPS_ERROR_DIALOG_STAGE_TITLE, description, content);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    /**
     * Shows an alert dialog on {@code owner} with the given parameters.
     * This method only returns after the user has closed the alert dialog.
     */
    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    /**
     * Shows an error alert dialog with {@code title} and error message, {@code e},
     * and exits the application after the user has closed the alert dialog.
     */
    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    //==================== Event Handling Code ===============================================================

    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait(FILE_OPS_ERROR_DIALOG_HEADER_MESSAGE, FILE_OPS_ERROR_DIALOG_CONTENT_MESSAGE,
                event.exception);
    }

    //@@author QzSG
    @Subscribe
    private void handleDataRestoreExceptionEvent(DataRestoreExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait(FILE_OPS_RESTORE_ERROR_DIALOG_HEADER_MESSAGE,
                FILE_OPS_RESTORE_ERROR_DIALOG_CONTENT_MESSAGE, event.exception);
    }


    /**
     * Creates an shows a notification with the relevant details provided
     * @param title Title of the notification
     * @param message Notification message for the user
     * @param duration Duration to show the notification for before it disappears
     */
    private void showNotification(String title, String message, Duration duration) {
        try {
            ImageView imageIcon = new ImageView(
                    Paths.get("./src/main/resources/view/images/dialog-info.png").toUri().toURL().toExternalForm());
            Notifications.create()
                    .title(title)
                    .text(message)
                    .hideAfter(duration)
                    .owner(mainWindow.getPrimaryStage())
                    .graphic(imageIcon)
                    .show();
        } catch (MalformedURLException e) {
            throw new IllegalStateException("This should always be valid");
        }
    }

    @Subscribe
    private void handleNewNotificationAvailableEvent(NewNotificationAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> showNotification(event.title, event.message, event.duration));
    }
}
