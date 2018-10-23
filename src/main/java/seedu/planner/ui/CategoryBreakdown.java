package seedu.planner.ui;

import java.util.logging.Logger;

import javax.security.auth.login.AccountNotFoundException;

import org.apache.poi.hwpf.model.PieceDescriptor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
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

    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Iphone 5S", 13),
            new PieChart.Data("Samsung Grand", 25),
            new PieChart.Data("MOTO G", 10),
            new PieChart.Data("Nokia Lumia", 22));

    public CategoryBreakdown() {
        super(FXML);
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Mobile Sales");
        pieChart.setLabelsVisible(true);
        pieChart.setLabelLineLength(10);
        pieChart.setLegendSide(Side.RIGHT);
        pieChart.setMinWidth(500);
        pieChart.setMinHeight(500);
        pieChart.relocate(0,0 );
        root.getChildren().add(pieChart);
    }
}
