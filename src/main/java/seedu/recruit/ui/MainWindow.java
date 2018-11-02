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
import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.core.GuiSettings;
import seedu.recruit.commons.core.LogsCenter;
import seedu.recruit.commons.events.ui.ExitAppRequestEvent;
import seedu.recruit.commons.events.ui.ShowCandidateBookRequestEvent;
import seedu.recruit.commons.events.ui.ShowCompanyBookRequestEvent;
import seedu.recruit.commons.events.ui.ShowEmailPreviewEvent;
import seedu.recruit.commons.events.ui.ShowHelpRequestEvent;
import seedu.recruit.commons.events.ui.ShowLastViewedBookRequestEvent;
import seedu.recruit.commons.events.ui.ShowShortlistPanelRequestEvent;
import seedu.recruit.commons.events.ui.ShowUpdateJobListRequestEvent;
import seedu.recruit.commons.events.ui.SwitchBookRequestEvent;
import seedu.recruit.logic.Logic;
import seedu.recruit.logic.commands.SwitchBookCommand;
import seedu.recruit.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    //Tracks whether an instance of MainWindow exists
    private static boolean exists = false;

    private static final String FXML = "MainWindow.fxml";
    private static String currentBook = "candidateBook";
    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private Config config;
    private Logic logic;
    private UserPrefs prefs;
    private HelpWindow helpWindow;
    private EmailPreview emailPreview;
    private CandidateDetailsPanel candidateDetailsPanel;
    private CompanyJobDetailsPanel companyJobDetailsPanel;
    private ShortlistPanel shortlistPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem companyBookMenuItem;

    @FXML
    private MenuItem candidateBookMenuItem;

    @FXML
    private StackPane panelViewPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML, primaryStage);
        this.exists = true;

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
        emailPreview = new EmailPreview();
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

    /**
     * RecruitBook's default panelViewPlaceHolder shows the list of
     * companies and their list of jobs, and at the same time
     * fills up all the other placeholders of this window.
     */
    void fillInnerParts() {

        panelViewPlaceholder.getChildren().add(getCandidateDetailsPanel().getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(
                prefs.getCandidateBookFilePath(), prefs.getCompanyBookFilePath(),
                logic.getFilteredPersonList().size(), logic.getFilteredCompanyList().size());
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

    private void clearResultDisplay() {
        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
    }

    /**
     * Handles the menu Switch Book from Company Book to Candidate Book.
     */
    @FXML
    public void handleChangeToCandidateBookMenuItem() {
        EventsCenter.getInstance().post(new ShowCandidateBookRequestEvent());
        clearResultDisplay();
    }

    /**
     * Handles the menu Switch Book from Candidate Book to Company Book.
     */
    @FXML
    public void handleChangeToCompanyBookMenuItem() {
        EventsCenter.getInstance().post(new ShowCompanyBookRequestEvent());
        clearResultDisplay();
    }

    /**
     * Handles the Help menu item to open the help window or focus on it if it's already opened.
     */
    @FXML
    public void handleHelpMenuItem() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        clearResultDisplay();
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    private void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    /**
     * Opens the email preview window.
     */
    public void handleEmailPreview(String preview) {
        emailPreview.setEmailPreview(preview);

        if (!emailPreview.isShowing()) {
            emailPreview.show();
        } else {
            emailPreview.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    private CandidateDetailsPanel getCandidateDetailsPanel() {
        candidateDetailsPanel = new CandidateDetailsPanel(logic.getFilteredPersonList());
        return candidateDetailsPanel;
    }

    private CompanyJobDetailsPanel getCompanyJobDetailsPanel() {
        companyJobDetailsPanel = new CompanyJobDetailsPanel(logic.getFilteredCompanyList(),
                logic.getFilteredCompanyJobList());
        return companyJobDetailsPanel;
    }

    private ShortlistPanel getShortlistPanel() {
        shortlistPanel = new ShortlistPanel(logic.getFilteredPersonList(), logic.getFilteredCompanyList(),
                logic.getFilteredCompanyJobList());
        return shortlistPanel;
    }

    private StackPane getPanelViewPlaceholder() {
        return panelViewPlaceholder;
    }

    public static String getDisplayedBook() {
        if (currentBook.contentEquals("companyBook")) {
            return "companyBook";
        } else if (currentBook.contentEquals("candidateBook")) {
            return "candidateBook";
        } else {
            return "Error in Switching Book";
        }
    }

    private void setDisplayedBookToCandidateBook() {
        currentBook = "candidateBook";
    }

    private void setDisplayedBookToCompanyBook() {
        currentBook = "companyBook";
    }

    /**
     * Switches the view on panelViewPlaceholder
     * from Company Book to Candidate Book.
     */
    private void switchToCandidateBook() {
        if (!getPanelViewPlaceholder().getChildren().isEmpty()) {
            getPanelViewPlaceholder().getChildren().remove(0);
            getPanelViewPlaceholder().getChildren().add(getCandidateDetailsPanel().getRoot());
            setDisplayedBookToCandidateBook();
        }
    }

    /**
     * Switches the view on panelViewPlaceholder
     * from Candidate Book to Company Book.
     */
    private void switchToCompanyBook() {
        if (!getPanelViewPlaceholder().getChildren().isEmpty()) {
            getPanelViewPlaceholder().getChildren().remove(0);
            getPanelViewPlaceholder().getChildren().add(getCompanyJobDetailsPanel().getRoot());
            setDisplayedBookToCompanyBook();
        }
    }

    /**
     * Switches the view on panelViewPlaceholder
     * to the last viewed book.
     */
    private void switchToLastViewedBook() {
        switch (getDisplayedBook()) {
        case "companyBook":
            switchToCompanyBook();
            break;

        case "candidateBook":
            switchToCandidateBook();
            break;
        default:
        }
    }

    /**
     * Switches the view on panelViewPlaceholder
     * from Candidate/Company Book to carry out the Shortlist command.
     */
    private void switchToShortlistPanel() {
        if (!getPanelViewPlaceholder().getChildren().isEmpty()) {
            getPanelViewPlaceholder().getChildren().remove(0);
            getPanelViewPlaceholder().getChildren().add(getShortlistPanel().getRoot());
        }
    }

    /**
     * Returns true if an instance of MainWindow exists, false otherwise
     */
    public static boolean isExisting() {
        return exists;
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
    private void handleEmailPreviewEvent(ShowEmailPreviewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleEmailPreview(event.getEmailPreview());
    }

    @Subscribe
    private void handleShowCandidateBookEvent(ShowCandidateBookRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (currentBook.contentEquals("companyBook")) {
            switchToCandidateBook();
        }
    }

    @Subscribe
    private void handleShowCompanyBookEvent(ShowCompanyBookRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        //if (currentBook.contentEquals("candidateBook")) {
         //   switchToCompanyBook();
        //}
        switchToCompanyBook();
    }

    @Subscribe
    private void handleShowShortlistPanelEvent(ShowShortlistPanelRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchToShortlistPanel();
    }

    @Subscribe
    private void handleSwitchBookEvent (SwitchBookRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (getDisplayedBook().contentEquals("companyBook")) {
            switchToCandidateBook();
            SwitchBookCommand.setMessage("Switched to Candidate Book successfully.");
        } else if (getDisplayedBook().contentEquals("candidateBook")) {
            switchToCompanyBook();
            SwitchBookCommand.setMessage("Switched to Company Book successfully.");
        }
    }

    @Subscribe
    private void handleShowLastViewedBookEvent (ShowLastViewedBookRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchToLastViewedBook();
    }

    @Subscribe
    private void handleUpdateJobListEvent(ShowUpdateJobListRequestEvent event) {
        switchToCompanyBook();
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }
}
