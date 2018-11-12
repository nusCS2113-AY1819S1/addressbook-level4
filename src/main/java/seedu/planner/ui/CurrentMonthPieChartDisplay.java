package seedu.planner.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.scene.chart.PieChart;
import seedu.planner.commons.core.LogsCenter;

/**
 * This class inherits all properties of CategoryBreakdown but uses a different Java fxml file
 */
public class CurrentMonthPieChartDisplay extends CategoryBreakdown {

    private static final Logger logger = LogsCenter.getLogger(CategoryBreakdown.class);

    private static final String FXML = "CurrentMonthPieChartDisplay.fxml";

    public CurrentMonthPieChartDisplay(List<PieChart.Data> labelData, List<PieChart.Data> legendData, String label) {
        super(labelData, legendData, label, FXML);
    }
}
