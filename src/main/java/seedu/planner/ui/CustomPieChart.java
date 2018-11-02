package seedu.planner.ui;

import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
//@@author tenvinc
/**
 * This class is a custom PieChart class that can be used to display statistics in FinancialPlanner
 */
public class CustomPieChart extends PieChart {

    public static final String CSS_FILE = "view/DarkTheme.css";

    public CustomPieChart(List<Data> labelData, List<Data> legendData) {
        super();
        getStylesheets().add(CSS_FILE);
        setLegend(new CustomLegend(this, legendData));
        setData(FXCollections.observableList(labelData));
        labelData.forEach(data ->
                data.nameProperty().bind(Bindings.concat(data.getName(), " ", data.pieValueProperty(), "%")));
    }
    /**
     * This class represents a customized legend panel for the pieChart
     */
    class CustomLegend extends GridPane {

        CustomLegend(PieChart chart, List<Data> pieChartData) {
            setHgap(10);
            setVgap(10);
            int index = 0;
            for (Data d : pieChartData) {
                Label legendText = createLabel(d.getName() + "   " + convertToMoney(d.getPieValue()));
                legendText.setWrapText(true);
                addRow(index, createSymbol(pieChartData.indexOf(d)), legendText);
                index++;
            }
        }

        private Label createLabel(String text) {
            Label label = new Label(text);
            label.getStyleClass().add("default-legend-text");
            return label;
        }

        private String convertToMoney(Double data) {
            return String.format("$%.2f", data);
        }

        /**
         * Creates the symbol to be placed at the side of the label
         */
        private Node createSymbol(int index) {
            Shape symbol = new Rectangle(10, 10, 10, 10);
            symbol.getStyleClass().add(String.format("default-color%d-chart-pie-legend", index % 8));
            return symbol;
        }

    }
}
