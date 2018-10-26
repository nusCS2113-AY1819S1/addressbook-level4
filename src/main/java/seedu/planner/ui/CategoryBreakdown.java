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

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    private static final String FXML = "CategoryBreakdown.fxml";

    @FXML
    private AnchorPane root;

    private ObservableList<PieChart.Data> pieChartData;
    private PieChart pieChart;

    public CategoryBreakdown(ObservableList<CategoryStatistic> toDisplay, String label) {
        super(FXML);
        pieChartData = convertToPieChartList(toDisplay);
        pieChart = new CustomPieChart(pieChartData);
        initPieChart(label);
        root.setStyle("-fx-background-color: grey");
        root.getChildren().add(pieChart);
    }

    /** Sets up the pieChart's format */
    private void initPieChart(String label) {
        pieChart.setTitle(label);
        pieChart.setLabelsVisible(true);
        pieChart.setLabelLineLength(50);
        pieChart.setLegendSide(Side.RIGHT);

        pieChart.setPrefSize(400, 800);
        pieChart.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
        pieChart.maxHeight(Double.MAX_VALUE);
        pieChart.maxWidth(Double.MAX_VALUE);
        pieChart.minHeight(Control.USE_PREF_SIZE);
        pieChart.minHeight(Control.USE_PREF_SIZE);

        root.setTopAnchor(pieChart, 0.0);
        root.setBottomAnchor(pieChart, 0.0);
        root.setRightAnchor(pieChart, 0.0);
        root.setLeftAnchor(pieChart, 0.0);

    }

    public void setPieChartSize(Double prefWidth, Double prefHeight) {
        if (prefWidth != null) {
            pieChart.setPrefWidth(prefWidth);
        }
        if (prefHeight != null) {
            pieChart.setPrefHeight(prefHeight);
        }
    }

    public void disableLegend() {
        pieChart.setLegendVisible(false);
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
