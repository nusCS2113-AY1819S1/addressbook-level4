package seedu.planner.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.model.summary.CategoryStatistic;

/**
 * This UI component is responsible for displaying the breakdown of total income and expenses into categories
 * as a pie chart.
 */
public class CategoryBreakdown extends UiPart<Region> {

    public static final String LABEL = "Category Breakdown for financial activity";

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    private static final String FXML = "CategoryBreakdown.fxml";

    @FXML
    private AnchorPane root;

    private ObservableList<PieChart.Data> pieChartData;
    private PieChart pieChart;

    public CategoryBreakdown(ObservableList<CategoryStatistic> toDisplay) {
        super(FXML);
        pieChartData = convertToPieChartList(toDisplay);
        pieChart = new CustomPieChart(pieChartData);
        initPieChart();
        root.setStyle("-fx-background-color: grey");
        root.getChildren().add(pieChart);
    }

    /** Sets up the pieChart's format */
    private void initPieChart() {
        pieChart.setTitle(LABEL);
        pieChart.setLabelsVisible(true);
        pieChart.setLabelLineLength(50);
        pieChart.setLegendSide(Side.RIGHT);

        pieChart.setPrefSize(800, 400);
        pieChart.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
        pieChart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        root.setTopAnchor(pieChart, 0.0);
        root.setBottomAnchor(pieChart, 0.0);
        root.setRightAnchor(pieChart, 0.0);
        root.setLeftAnchor(pieChart, 0.0);

    }

    /** Converts a given ObservableList to a list that can be read by {@link PieChart}*/
    private ObservableList<PieChart.Data> convertToPieChartList(ObservableList<CategoryStatistic> toDisplay) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (CategoryStatistic stat : toDisplay) {
            if (stat.getTotalExpense() != 0) {
                pieChartData.add(new PieChart.Data(stat.getTags().toString(), stat.getTotalExpense()));
            }
        }
        return pieChartData;
    }
}
