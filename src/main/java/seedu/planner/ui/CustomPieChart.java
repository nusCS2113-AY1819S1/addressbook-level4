package seedu.planner.ui;

import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 * This class is a custom PieChart class that can be used to display statistics in FinancialPlanner
 */
public class CustomPieChart extends PieChart {

    public static final String CSS_FILE = "DarkTheme.css";

    private final ObservableList<PieChart.Data> pieChartData;

    public CustomPieChart(ObservableList<Data> pieChartData) {
        super(pieChartData);
        getStylesheets().add(CSS_FILE);
        setLegend(new CustomLegend(this));
        this.pieChartData = pieChartData;
    }

    @Override
    protected void layoutChartChildren(double top, double left, double contentWidth, double contentHeight) {
        if (getLabelsVisible()) {
            getData().forEach(data -> addPercentages(data));
        }
        super.layoutChartChildren(top, left, contentWidth, contentHeight);
    }

    /**
     * Adds percentages to the PieChart labels.
     */
    private void addPercentages(PieChart.Data data) {
        Optional<Node> optionalNode = lookupAll(".chart-pie-label")
                .stream().filter(n -> n instanceof Text && ((Text) n).getText().equals(data.getName())).findAny();
        if (optionalNode.isPresent()) {
            Text optionalTextNode = ((Text) optionalNode.get());
            optionalTextNode.setText(data.getName() + " " + data.getPieValue() + "%");
        }
    }

    class CustomLegend extends GridPane {

        CustomLegend(PieChart chart) {
            setHgap(10);
            setVgap(10);
            int index= 0;
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
