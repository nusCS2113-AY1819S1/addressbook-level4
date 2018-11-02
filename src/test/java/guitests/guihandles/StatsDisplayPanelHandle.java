package guitests.guihandles;

import guitests.guihandles.exceptions.NodeNotFoundException;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

/**
 * Handle for the GUI component StatsDisplayPanel
 */
public class StatsDisplayPanelHandle extends NodeHandle<Node> {

    public static final String STATS_DISPLAY_HANDLE = "#tabManager";
    public static final String EXPENSE_BREAKDOWN_LABEL = "Category Breakdown For Expenses";
    public static final String INCOME_BREAKDOWN_LABEL = "Category Breakdown For Income";

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
    public CategoryBreakdownHandle getCategoryBreakdown(Tab tab) throws NullPointerException {
        CategoryBreakdownHandle categoryBreakdownHandle;
        try {
            categoryBreakdownHandle = new CategoryBreakdownHandle((AnchorPane) tab.getContent());
        } catch (NodeNotFoundException nfe) {
            return null;
        }
        return categoryBreakdownHandle;
    }

    public Tab getChildTab(String label) {
        ObservableList<Tab> tabs = tabManager.getTabs();
        for (Tab t : tabs) {
            if (t.getText().equals(label)) {
                return t;
            }
        }
        return null;
    }

    public boolean isTabSelected(Tab tab) {
        int indexOfTab = tabManager.getTabs().indexOf(tab);
        return tabManager.getSelectionModel().isSelected(indexOfTab);
    }
}
