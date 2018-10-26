package seedu.planner.ui;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.planner.commons.core.LogsCenter;

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

    public CategoryBreakdown(ObservableList<ChartData> toDisplay, String label, Double total) {
        super(FXML);
        pieChartData = convertToPieChartList(toDisplay, total);
        pieChart = new CustomPieChart(pieChartData);
        initPieChart(label);
        root.setStyle("-fx-background-color: grey");
        root.getChildren().add(pieChart);
    }

    /** Sets up the pieChart's format */
    private void initPieChart(String label) {
        pieChart.setTitle(label);
        pieChart.setLabelsVisible(true);
        pieChart.setLabelLineLength(10);
        pieChart.setLegendSide(Side.RIGHT);

        pieChart.setPrefSize(800, 400);
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

    /** Converts a given ObservableList containing {@see ChartData} to a list that can be read by {@link PieChart}
     * */
    private ObservableList<PieChart.Data> convertToPieChartList(ObservableList<ChartData> data, Double total) {
        List<PieChart.Data> dataList;
        if (total > 0.0) {
            dataList = data.stream().map(d -> new PieChart.Data(d.key, Double.parseDouble(
                    String.format("%.2f", d.value / total * 100.0))))
                    .collect(Collectors.toList());
        } else {
            dataList = data.stream().map(d -> new PieChart.Data(d.key, d.value)).collect(Collectors.toList());
        }
        return FXCollections.observableList(dataList);
    }
}
