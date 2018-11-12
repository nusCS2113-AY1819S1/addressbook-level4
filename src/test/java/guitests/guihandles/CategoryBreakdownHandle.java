package guitests.guihandles;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import seedu.planner.ui.CustomPieChart;

/**
 * A handle for the GUI component CategoryBreakdown
 */
public class CategoryBreakdownHandle extends NodeHandle<Node> {

    private CustomPieChart pieChart;
    private AnchorPane root;

    public CategoryBreakdownHandle(Node node) {
        super(node);
        if (node instanceof AnchorPane) {
            root = (AnchorPane) node;
        }
        Node childNode = root.getChildren().get(0);
        if (childNode instanceof CustomPieChart) {
            pieChart = (CustomPieChart) childNode;
        }
    }

    public boolean isVisible() {
        return root.isVisible();
    }

    /**
     * Checks if the data of current pieChart's labels is the same as expectedPieChartLabels and if the data of
     * current pieChart's legend is the same as expectedPieChartLegends
     * @param expectedPieChartLabels
     * @param expectedPieChartLegends
     */
    public boolean checkIfDataIsSame(List<PieChart.Data> expectedPieChartLabels,
                                     List<PieChart.Data> expectedPieChartLegends) {
        PieChart expectedPieChart = new CustomPieChart(expectedPieChartLabels, expectedPieChartLegends);
        return pieChart.equals(expectedPieChart);
    }
}
