package seedu.planner.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.model.summary.CategoryStatistic;

/**
 * This UI component is responsible for displaying the breakdown of total income and expenses into categories
 * as a pie chart.
 */
public class CategoryBreakdown extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    private static final String FXML = "CategoryBreakdown.fxml";

    @FXML
    private AnchorPane root;

    private ObservableList<PieChart.Data> pieChartData;

    public CategoryBreakdown(ObservableList<CategoryStatistic> toDisplay) {
        super(FXML);
        pieChartData = convertToPieChartList(toDisplay);
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Category Breakdown for financial activity");
        pieChart.setLabelsVisible(true);
        pieChart.setLabelLineLength(10);
        pieChart.setLegendSide(Side.RIGHT);
        pieChart.relocate(0, 0);
        pieChart.prefHeightProperty().bind(root.heightProperty());
        pieChart.prefWidthProperty().bind(root.widthProperty());
        root.setStyle("-fx-background-color: grey");
        root.getChildren().add(pieChart);
    }

    /** Converts a given ObservableList to a list that can be read by {@link PieChart}*/
    private ObservableList<PieChart.Data> convertToPieChartList(ObservableList<CategoryStatistic> toDisplay) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (CategoryStatistic stat : toDisplay) {
            pieChartData.add(new PieChart.Data(stat.getTags().toString(), stat.getTotalExpense()));
        }
        return pieChartData;
    }
}
