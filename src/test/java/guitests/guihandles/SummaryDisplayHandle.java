package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import seedu.planner.ui.SummaryEntry;

public class SummaryDisplayHandle extends NodeHandle<Node> {

    public static final String SUMMARY_DISPLAY_ID = "#summaryDisplay";
    public static final String SUMMARY_TABLE_ID = "#summaryTable";

    private TableView summaryTable;

    private ObservableList<SummaryEntry> lastRememberedTableList;

    public SummaryDisplayHandle(TableView node) {
        super(node);
        summaryTable = getChildNode(SUMMARY_TABLE_ID);
    }

    /**
     * Checks the current list of items in current TableView with the previously recorded list
     * @param tableView
     * @return
     */
    public boolean isSummaryTableChanged(TableView tableView) {
        return tableView.getItems().equals(lastRememberedTableList);
    }

    /**
     * Remembers the current state of the panel
     */
    public void rememberSummaryTable() {
        lastRememberedTableList = summaryTable.getItems();
    }

    /**
     * Checks whether the panel is visible to the user or not
     */
    public boolean isPanelVisible() {
        return summaryTable.isVisible();
    }
}
