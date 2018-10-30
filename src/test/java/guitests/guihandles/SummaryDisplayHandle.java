package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import seedu.planner.ui.SummaryEntry;

/**
 * Provides a handle for {@code SummaryDisplay}
 */
public class SummaryDisplayHandle extends NodeHandle<Node> {

    public static final String SUMMARY_DISPLAY_ID = "#summaryDisplay";
    public static final String SUMMARY_TABLE_ID = "#summaryTable";

    private TableView summaryTable;

    public SummaryDisplayHandle(AnchorPane node) {
        super(node);
        summaryTable = getChildNode(SUMMARY_TABLE_ID);
    }

    /**
     * Checks whether the panel is visible to the user or not
     */
    public boolean isPanelVisible() {
        return summaryTable.isVisible();
    }

    public ObservableList<SummaryEntry> getSummaryTableList() {
        return summaryTable.getItems();
    }
}
