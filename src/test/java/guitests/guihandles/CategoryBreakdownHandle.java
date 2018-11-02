package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import seedu.planner.ui.CustomPieChart;

/**
 * A handle for the GUI component CategoryBreakdown
 */
public class CategoryBreakdownHandle extends NodeHandle<Node> {

    public static final String CATEGORY_BREAKDOWN_HANDLE = "categoryBreakdown";

    private AnchorPane node;
    private CustomPieChart pieChart;

    public CategoryBreakdownHandle(AnchorPane node) {
        super(node);
        this.node = node;
    }

    public boolean isVisible() {
        return node.isVisible();
    }

    public ObservableList<PieChart.Data> getPieChartData() {
        return pieChart.getData();
    }
}
