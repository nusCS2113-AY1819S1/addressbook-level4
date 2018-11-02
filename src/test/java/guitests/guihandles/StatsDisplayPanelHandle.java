package guitests.guihandles;

import guitests.guihandles.exceptions.NodeNotFoundException;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Handle for the GUI component StatsDisplayPanel
 */
public class StatsDisplayPanelHandle extends NodeHandle<Node> {

    public static final String STATS_DISPLAY_HANDLE = "#tabManager";
    public static final String EXPENSE_BREAKDOWN_LABEL = "Total Expense for the period";
    public static final String INCOME_BREAKDOWN_LABEL = "Total Income for the period";

    private TabPane tabManager;

    public StatsDisplayPanelHandle(TabPane tabManager) {
        super(tabManager);
        this.tabManager = tabManager;
    }

    public boolean isVisible() {
        return tabManager.isVisible();
    }

    /**
     * Finds the CategoryBreakdown tabManager and instantiates the CategoryBreakdownHandle before returning its
     * reference
     * @return CategoryBreakdownHandle
     */
    public CategoryBreakdownHandle getCategoryBreakdown(String label) throws NullPointerException {
        CategoryBreakdownHandle categoryBreakdownHandle;
        Tab breakdownTab = getChildTab(label);
        try {
            categoryBreakdownHandle = new CategoryBreakdownHandle(getChildNode(
                    CategoryBreakdownHandle.CATEGORY_BREAKDOWN_HANDLE));
        } catch (NodeNotFoundException nfe) {
            return null;
        }
        return categoryBreakdownHandle;
    }

    private Tab getChildTab(String label) {
        ObservableList<Tab> tabs = tabManager.getTabs();
        for (Tab t : tabs) {
            if (t.getText().equals(label)) {
                return t;
            }
        }
        return null;
    }
}
