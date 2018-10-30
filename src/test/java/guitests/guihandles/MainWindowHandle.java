package guitests.guihandles;

import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private boolean isCompanyBookInitialized = false;
    private CandidateDetailsPanelHandle candidateDetailsPanel;
    private CompanyJobDetailsPanelHandle companyJobDetailsPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    //private final BrowserPanelHandle browserPanel;

    public MainWindowHandle(Stage stage) {
        super(stage);
        candidateDetailsPanel = new
                CandidateDetailsPanelHandle(getChildNode(CandidateDetailsPanelHandle.CANDIDATE_DETAILS_VIEW_ID));

        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        //browserPanel = new BrowserPanelHandle(getChildNode(BrowserPanelHandle.BROWSER_ID));
    }

    public boolean isCompanyBookInitialized() {
        return isCompanyBookInitialized;
    }

    public CandidateDetailsPanelHandle getCandidateDetailsPanel() {
        return candidateDetailsPanel;
    }

    /**
     * Initializes the companyJobDetailsPanel when switching from Candidate Book to Company Book for
     * Company Book tests.
     * @return companyJobDetailsPanel
     */
    public CompanyJobDetailsPanelHandle initializeCompanyJobDetailsPanel() {
        companyJobDetailsPanel = new
                CompanyJobDetailsPanelHandle(getChildNode(CompanyJobDetailsPanelHandle.COMPANY_DETAILS_VIEW_ID));
        isCompanyBookInitialized = true;
        return companyJobDetailsPanel;
    }

    public CompanyJobDetailsPanelHandle getCompanyJobDetailsPanel() {
        return companyJobDetailsPanel;
    }

    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return statusBarFooter;
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

    /**
    public BrowserPanelHandle getBrowserPanel() {
        return browserPanel;
    }*/
}
