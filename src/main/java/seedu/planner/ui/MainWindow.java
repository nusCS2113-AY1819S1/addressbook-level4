package seedu.planner.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.planner.commons.core.Config;
import seedu.planner.commons.core.GuiSettings;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.ui.ExitAppRequestEvent;
import seedu.planner.commons.events.ui.RecordPanelSelectionChangedEvent;
import seedu.planner.commons.events.ui.ShowHelpRequestEvent;
import seedu.planner.commons.events.ui.ShowPieChartStatsEvent;
import seedu.planner.commons.events.ui.ShowSummaryTableEvent;
import seedu.planner.commons.events.ui.UpdateWelcomePanelEvent;
import seedu.planner.logic.Logic;
import seedu.planner.model.Model;
import seedu.planner.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private Model model;

    // Independent Ui parts residing in this Ui container
    private RecordListPanel recordListPanel;
    private Config config;
    private UserPrefs prefs;
    private HelpWindow helpWindow;
    // Panels in the main Ui Panel
    private DetailedRecordCard detailedRecordCard;
    private StatsDisplayPanel statsDisplayPanel;
    private WelcomePanel welcomePanel;

    @FXML
    private StackPane mainUiPanelPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem homeMenuItem;

    @FXML
    private StackPane recordListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic, Model model) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
        this.model = model;

        // Configure the UI
        setTitle(config.getAppTitle());
        setWindowDefaultSize(prefs);

        setAccelerators();
        registerAsAnEventHandler(this);

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, new KeyCodeCombination(KeyCode.F1));
        setAccelerator(homeMenuItem, new KeyCodeCombination(KeyCode.HOME));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
            if (event.getTarget() instanceof ListView && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        fillMainUiPanel();

        recordListPanel = new RecordListPanel(logic.getFilteredRecordList());
        recordListPanelPlaceholder.getChildren().add(recordListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getFinancialPlannerFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic, model);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Fills the main Ui Panel which will be used for all ui output like welcomePanel,statistics,
     * summary or selection
     */
    private void fillMainUiPanel() {
        statsDisplayPanel = new StatsDisplayPanel();
        mainUiPanelPlaceholder.getChildren().add(statsDisplayPanel.getRoot());

        detailedRecordCard = new DetailedRecordCard();
        mainUiPanelPlaceholder.getChildren().add(detailedRecordCard.getRoot());

        welcomePanel = new WelcomePanel(model);
        mainUiPanelPlaceholder.getChildren().add(welcomePanel.getRoot());
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
     * Switches the welcome panel to the front and displays it.
     */
    @FXML
    public void handleHome() {
        logger.info("=================== [Home key has been activated. Returning to HOME] ====================");
        for (Node node: mainUiPanelPlaceholder.getChildren()) {
            node.setVisible(false);
        }
        welcomePanel.show();
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

    public RecordListPanel getRecordListPanel() {
        return recordListPanel;
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    /* ------------------ Delegates for event management system for switching of panels ----------------------------- */
    //@@author tenvinc
    @Subscribe
    private void handleShowSummaryTableEvent(ShowSummaryTableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        recordListPanel.unselect();
        for (Node node: mainUiPanelPlaceholder.getChildren()) {
            node.setVisible(false);
        }
        statsDisplayPanel.handleShowSummaryTableEvent(event);
    }

    @Subscribe
    private void handleShowPieCharStatsEvent(ShowPieChartStatsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        recordListPanel.unselect();
        for (Node node: mainUiPanelPlaceholder.getChildren()) {
            node.setVisible(false);
        }
        statsDisplayPanel.handleShowPieChartStatsEvent(event);
    }

    @Subscribe
    private void handleRecordPanelSelectionChangedEvent(RecordPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getNewSelection() != null) {
            for (Node node: mainUiPanelPlaceholder.getChildren()) {
                node.setVisible(false);
            }
        }
        detailedRecordCard.handleRecordPanelSelectionChangedEvent(event);
        if (!detailedRecordCard.getRoot().isVisible()) {
            welcomePanel.show();
        }
    }

    @Subscribe
    private void handleUpdateWelcomePanelEvent(UpdateWelcomePanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        recordListPanel.unselect();
        for (Node node: mainUiPanelPlaceholder.getChildren()) {
            node.setVisible(false);
        }
        welcomePanel.handleUpdateWelcomePanelEvent(event);
    }
}
