package seedu.planner.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.planner.commons.core.LogsCenter;
//@@author tenvinc
/**
 * This UI component is responsible for displaying the breakdown of total income and expenses into categories
 * as a pie chart.
 */
public class CategoryBreakdown extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(CategoryBreakdown.class);

    private static final String FXML = "CategoryBreakdown.fxml";

    @FXML
    private AnchorPane categoryBreakdown;

    private ObservableList<PieChart.Data> pieChartData;
    private PieChart pieChart;

    public CategoryBreakdown(ObservableList<ChartData> toDisplay, String label, Double total) {
        super(FXML);
        pieChart = new CustomPieChart(toDisplay, total);
        initPieChart(label);
        categoryBreakdown.setStyle("-fx-background-color: grey");
        categoryBreakdown.getChildren().add(pieChart);
    }

    /** Sets up the pieChart's format */
    private void initPieChart(String label) {
        pieChart.setTitle(label);
        pieChart.setLabelsVisible(true);
        pieChart.setLabelLineLength(20);
        pieChart.setLegendSide(Side.RIGHT);

        pieChart.setPrefSize(800, 400);
        pieChart.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
        pieChart.maxHeight(Double.MAX_VALUE);
        pieChart.maxWidth(Double.MAX_VALUE);
        pieChart.minHeight(Control.USE_PREF_SIZE);
        pieChart.minHeight(Control.USE_PREF_SIZE);

        AnchorPane.setTopAnchor(pieChart, 0.0);
        AnchorPane.setBottomAnchor(pieChart, 0.0);
        AnchorPane.setRightAnchor(pieChart, 0.0);
        AnchorPane.setLeftAnchor(pieChart, 0.0);

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

    public void setTitlePosition(Side side) {
        pieChart.setTitleSide(side);
    }
}
