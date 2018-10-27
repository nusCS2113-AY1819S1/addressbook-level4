package guitests.guihandles;

import javafx.stage.Stage;
import seedu.planner.ui.SummaryDisplay;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final RecordListPanelHandle recordListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    private final DetailedRecordCardHandle detailedRecordCard;

    public MainWindowHandle(Stage stage) {
        super(stage);

        recordListPanel = new RecordListPanelHandle(getChildNode(RecordListPanelHandle.RECORD_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        detailedRecordCard = new DetailedRecordCardHandle(getChildNode(DetailedRecordCardHandle.DETAILED_CARD_ID));
    }

    public RecordListPanelHandle getRecordListPanel() {
        return recordListPanel;
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

    public DetailedRecordCardHandle getDetailedRecordCard() {
        return detailedRecordCard;
    }

    /**
     * Finds the SummaryDisplay node and instantiates the SummaryDisplayHandle before returning its reference
     * @return SummaryDisplayHandle
     */
    public SummaryDisplayHandle getSummaryDisplay() {
        SummaryDisplayHandle summaryDisplayHandle;
        try {
            summaryDisplayHandle = new SummaryDisplayHandle(getChildNode(SummaryDisplayHandle.SUMMARY_DISPLAY_ID));
        } catch (Exception e) {
            return null;
        }
        return summaryDisplayHandle;
    }
}
