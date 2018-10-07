package guitests.guihandles;

import javafx.stage.Stage;

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
}
