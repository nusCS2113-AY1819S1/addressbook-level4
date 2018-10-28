package seedu.planner.ui;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * This class is a custom PieChart class that can be used to display statistics in FinancialPlanner
 */
public class CustomPieChart extends PieChart {

    public static final String CSS_FILE = "view/DarkTheme.css";

    public CustomPieChart(ObservableList<Data> pieChartData) {
        super(pieChartData);
        getStylesheets().add(CSS_FILE);
        setLegend(new CustomLegend(this));
        pieChartData.forEach(data ->
                data.nameProperty().bind(Bindings.concat(data.getName(), " ", data.pieValueProperty(), "%")));
    }

    /**
     * This class represents a customized legend panel for the pieChart
     */
    class CustomLegend extends GridPane {

        CustomLegend(PieChart chart) {
            setHgap(10);
            setVgap(10);
            int index = 0;
            ObservableList<PieChart.Data> dataList = chart.getData();
            for (PieChart.Data d : dataList) {
                addRow(index, createSymbol(dataList.indexOf(d)), new Label(d.getName()), new Label(convertToMoney(d)));
                index++;
            }
        }

        private String convertToMoney(PieChart.Data data) {
            return String.format("$%.2f", data.getPieValue());
        }

        private Node createSymbol(int index) {
            Shape symbol = new Rectangle(10, 10, 10, 10);
            symbol.getStyleClass().add(String.format("default-color%d-chart-pie-legend", index));
            return symbol;
        }

    }
}
