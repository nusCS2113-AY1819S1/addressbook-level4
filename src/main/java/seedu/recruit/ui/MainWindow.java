package seedu.recruit.ui;

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

import seedu.recruit.commons.core.Config;
import seedu.recruit.commons.core.GuiSettings;
import seedu.recruit.commons.core.LogsCenter;
import seedu.recruit.commons.events.ui.ExitAppRequestEvent;
import seedu.recruit.commons.events.ui.ShowCandidateBookRequestEvent;
import seedu.recruit.commons.events.ui.ShowCompanyBookRequestEvent;
import seedu.recruit.commons.events.ui.ShowHelpRequestEvent;
import seedu.recruit.logic.Logic;
import seedu.recruit.model.UserPrefs;

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
    private BrowserPanel browserPanel;
    private Config config;
    private UserPrefs prefs;
    private HelpWindow helpWindow;
    private CandidateDetailsPanel candidateDetailsPanel;
    private CompanyJobDetailsPanel companyJobDetailsPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem companyBook;

    @FXML
    private MenuItem candidateBook;

    @FXML
    private StackPane panelViewPlaceHolder;

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

        setAccelerators();
        registerAsAnEventHandler(this);

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
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
        });
    }

    @FXML
    public void handleChangeToCandidateDetailsPanel() {
        candidateBook.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * Handles switch in panel view in RecruitBook main window
             * when user switches from Company Book to Candidate Book
             */
            @Override
            public void handleBookChange(ActionEvent event) {
                if (!panelViewPlaceHolder.getChildren().isEmpty()) {
                    panelViewPlaceHolder.getChildren().remove(0);
                    panelViewPlaceHolder.getChildren().add(candidateDetailsPanel.getRoot());
                }
            }
        });
    }

    @FXML
    public void handleChangeToCompanyJobDetailsPanel() {
        companyBook.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * Handles switch in panel view in RecruitBook main window
             * when user switches from Candidate Book to Company Book
             */
            @Override
            public void handleBookChange(ActionEvent event) {
                if (!panelViewPlaceHolder.getChildren().isEmpty()) {
                    panelViewPlaceHolder.getChildren().remove(0);
                    panelViewPlaceHolder.getChildren().add(companyJobDetailsPanel.getRoot());
                }
            }
        });
    }

    /**
     * RecruitBook's default panelViewPlaceHolder shows the list of
     * companies and their list of jobs, and at the same time
     * fills up all the other placeholders of this window.
     */
    void fillInnerParts() {
        candidateDetailsPanel = new CandidateDetailsPanel(logic.getFilteredPersonList());
        companyJobDetailsPanel = new CompanyJobDetailsPanel(logic.getFilteredCompanyList(),
                                        logic.getFilteredCompanyJobList());
        panelViewPlaceHolder.getChildren().add(companyJobDetailsPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getCandidateBookFilePath(),
                logic.getFilteredCompanyList().size());
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

    public CandidateDetailsPanel getCandidateDetailsPanel() {
        return candidateDetailsPanel;
    }

    public CompanyJobDetailsPanel getCompanyJobDetailsPanel() {
        return companyJobDetailsPanel;
    }

    void releaseResources() {
        browserPanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    @Subscribe
    private void handleShowCandidateBookEvent(ShowCandidateBookRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleChangeToCandidateDetailsPanel();
    }

    @Subscribe
    private void handleShowCompanyBookEvent(ShowCompanyBookRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleChangeToCompanyJobDetailsPanel();
    }
}
