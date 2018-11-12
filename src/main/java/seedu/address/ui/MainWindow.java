package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.ui.ShowProfileRequestEvent;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    //private BrowserPanel browserPanel;
    private WorkoutListPanel workoutListPanel;
    private CurrentAndTrackPanel currentAndTrackPanel;
    private TrackedDataListPanel trackedDataListPanel;
    private Config config;
    private UserPrefs prefs;
    private HelpWindow helpWindow;
    private ProfileWindow profileWindow;

    //@FXML
    //private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem profileMenuItem;

    @FXML
    private StackPane workoutListPanelPlaceholder;

    @FXML
    private StackPane currentAndTrackPanelPlaceholder;

    @FXML
    private StackPane trackedDataListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setWindowDefaultSize(prefs);

        setHelpAccelerators();
        setProfileAccelerators();
        registerAsAnEventHandler(this);

        helpWindow = new HelpWindow();
        profileWindow = new ProfileWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setHelpAccelerators() {
        setHelpAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    private void setProfileAccelerators() {
        setProfileAccelerator(profileMenuItem, KeyCombination.valueOf("F3"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setHelpAccelerator(MenuItem helpMenuItem, KeyCombination keyCombination) {
        helpMenuItem.setAccelerator(keyCombination);

        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                helpMenuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    private void setProfileAccelerator(MenuItem profileMenuItem, KeyCombination keyCombination) {
        profileMenuItem.setAccelerator(keyCombination);

        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                profileMenuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        //browserPanel = new BrowserPanel();
        //browserPlaceholder.getChildren().add(browserPanel.getRoot());

        workoutListPanel = new WorkoutListPanel(logic.getFilteredWorkoutList());
        workoutListPanelPlaceholder.getChildren().add(workoutListPanel.getRoot());

        currentAndTrackPanel = new CurrentAndTrackPanel(logic.getFilteredTrackedData());
        currentAndTrackPanelPlaceholder.getChildren().add(currentAndTrackPanel.getRoot());

        trackedDataListPanel = new TrackedDataListPanel(logic.getFilteredTrackedDataList());
        trackedDataListPanelPlaceholder.getChildren().add(trackedDataListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getWorkoutBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Opens the profile window or focuses on it if it's already opened.
     */
    @FXML
    public void handleProfile() {
        if (!profileWindow.isShowing()) {
            profileWindow.show();
        } else {
            profileWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public WorkoutListPanel getWorkoutListPanel() {
        return workoutListPanel;
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    @Subscribe
    private void handleShowProfileEvent(ShowProfileRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleProfile();
    }

}
