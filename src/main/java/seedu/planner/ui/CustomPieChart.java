package seedu.planner.ui;

import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
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

    public CustomPieChart(ObservableList<ChartData> pieChartData, Double total) {
        super();
        ObservableList<Data> dataAsPercentages = convertToPercentages(pieChartData, total);
        getStylesheets().add(CSS_FILE);
        setLegend(new CustomLegend(this, pieChartData));
        setData(dataAsPercentages);
        dataAsPercentages.forEach(data ->
                data.nameProperty().bind(Bindings.concat(data.getName(), " ", data.pieValueProperty(), "%")));
    }

    /** Converts a given ObservableList containing {@see ChartData} to a list that can be read by {@link PieChart}
     * */
    private ObservableList<PieChart.Data> convertToPercentages(ObservableList<ChartData> data, Double total) {
        List<Data> dataList;
        if (total > 0.0) {
            dataList = data.stream().map(d -> new PieChart.Data(d.key, Double.parseDouble(
                    String.format("%.2f", d.value / total * 100.0))))
                    .collect(Collectors.toList());
        } else {
            dataList = data.stream().map(d -> new PieChart.Data(d.key, d.value)).collect(Collectors.toList());
        }
        return FXCollections.observableList(dataList);
    }

    /**
     * This class represents a customized legend panel for the pieChart
     */
    class CustomLegend extends GridPane {

        CustomLegend(PieChart chart, ObservableList<ChartData> pieChartData) {
            setHgap(10);
            setVgap(10);
            int index = 0;
            for (ChartData d : pieChartData) {
                addRow(index, createSymbol(pieChartData.indexOf(d)), createLabel(d.key),
                        createLabel(convertToMoney(d.value)));
                index++;
            }
        }

        private Node createLabel(String text) {
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
