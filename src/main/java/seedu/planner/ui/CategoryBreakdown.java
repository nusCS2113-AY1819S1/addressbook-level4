package seedu.planner.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
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
        pieChart = new PieChart(pieChartData) {
            @Override
            protected void layoutChartChildren(double top, double left, double contentWidth, double contentHeight) {
                if (getLabelsVisible()) {
                    getData().forEach(data -> addPercentages(data, pieChart));
                }
                super.layoutChartChildren(top, left, contentWidth, contentHeight);
            }
        };
        initPieChart();
        root.setStyle("-fx-background-color: grey");
        root.getChildren().add(pieChart);
    }

    /** Sets up the pieChart's format */
    private void initPieChart() {
        pieChart.setTitle(LABEL);
        pieChart.setLabelsVisible(true);
        pieChart.setLabelLineLength(50);
        pieChart.setLegendSide(Side.BOTTOM);
        pieChart.prefHeightProperty().bind(root.heightProperty());
        pieChart.prefWidthProperty().bind(root.widthProperty());
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

    /**
     * Adds percentages to the PieChart labels.
     */
    private void addPercentages(PieChart.Data data, PieChart pieChart) {
        Optional<Node> optionalNode = pieChart.lookupAll(".chart-pie-label")
                .stream().filter(n -> n instanceof Text && ((Text) n).getText().equals(data.getName())).findAny();
        if (optionalNode.isPresent()) {
            Text optionalTextNode = ((Text) optionalNode.get());
            optionalTextNode.setText(data.getName() + " " + data.getPieValue() + "%");
        }
    }
}
