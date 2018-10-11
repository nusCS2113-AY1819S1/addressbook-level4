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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import seedu.recruit.commons.core.Config;
import seedu.recruit.commons.core.GuiSettings;
import seedu.recruit.commons.core.LogsCenter;
import seedu.recruit.commons.events.ui.ExitAppRequestEvent;
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
    private PersonListPanel personListPanel;
    private Config config;
    private UserPrefs prefs;
    private HelpWindow helpWindow;
    private PersonDetailsPanel personDetailsPanel;
    private SwitchPanel switchPanel;
    private CompanyJobDetailsPanel companyJobDetailsPanel;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem CompanyBook;

    @FXML
    private MenuItem CandidateBook;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private BorderPane switchPanelPlaceholder;

    @FXML
    private StackPane personDetailsPanelPlaceholder;

    @FXML
    private StackPane companyJobDetailsPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane panelView;

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
    private void handleChangeView(ActionEvent event) {
        String menuItemID = ((MenuItem) event.getSource()).getId();
        if (menuItemID.contentEquals("CandidateBook")) {
            panelView.getChildren().add(personDetailsPanel.getRoot());
            panelView = personDetailsPanelPlaceholder;
            System.out.println("PersonDetails in handle");
        }
        else if (menuItemID.contentEquals("CompanyBook")) {
            panelView.getChildren().add(companyJobDetailsPanel.getRoot());
            panelView = companyJobDetailsPanelPlaceholder;
            System.out.println("Company in handle");
        }
    }
    /**
    @FXML
    public void handleChangeToCandidatePanelView(ActionEvent event) {
        registerAsAnEventHandler(this);
        //personDetailsPanel = new PersonDetailsPanel(logic.getFilteredPersonList());
        panelView.getChildren().add(personDetailsPanel.getRoot());
        panelView = personDetailsPanelPlaceholder;
    }

    @FXML
    public void handleChangeToCompanyPanelView(ActionEvent event) {
        String menuItemID = ((MenuItem) event.getSource()).getId();
        System.out.println(menuItemID);
        if (menuItemID.contentEquals("CompanyJobDetailsPanel")) {
            registerAsAnEventHandler(this);
            //companyJobDetailsPanel = new CompanyJobDetailsPanel(logic.getFilteredCompanyList(), logic.getFilteredCompanyJobList());
            panelView.getChildren().add(companyJobDetailsPanel.getRoot());
            panelView = companyJobDetailsPanelPlaceholder;
        }
    }*/

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        //browserPanel = new BrowserPanel();
        //browserPlaceholder.getChildren().add(browserPanel.getRoot());

        personDetailsPanel = new PersonDetailsPanel(logic.getFilteredPersonList());
        companyJobDetailsPanel = new CompanyJobDetailsPanel(logic.getFilteredCompanyList(), logic.getFilteredCompanyJobList());

        switchPanel = new SwitchPanel();
        switchPanelPlaceholder.getChildren().add(switchPanel.getRoot());

        if (panelView == personDetailsPanelPlaceholder) {
            panelView.getChildren().add(personDetailsPanel.getRoot());
            System.out.println("PersonDetails in fillInner");
        }
        else if (panelView == companyJobDetailsPanelPlaceholder) {
            panelView.getChildren().add(companyJobDetailsPanel.getRoot());
            System.out.println("CompnayDetails in fillInner");
        }

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

    //public PersonListPanel getPersonListPanel() {
        //return personListPanel;
    //}

    //public PersonDetailsPanel getPersonDetailsPanel() {return personDetailsPanel;}

    public CompanyJobDetailsPanel getCompanyJobDetailsPanel() {return companyJobDetailsPanel;}

    void releaseResources() {
        browserPanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }
}
