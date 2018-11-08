package seedu.planner.ui;

import java.util.ArrayList;
import java.util.Arrays;
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
//@@author tenvinc
/**
 * This class is a custom PieChart class that can be used to display statistics in FinancialPlanner
 */
public class CustomPieChart extends PieChart {

    public static final String CSS_FILE = "view/DarkTheme.css";
    private ObservableList<String> observableData;

    public CustomPieChart(List<Data> labelData, List<Data> legendData) {
        super();
        getStylesheets().add(CSS_FILE);
        setLegend(new CustomLegend(this, legendData));
        setData(FXCollections.observableList(labelData));
        labelData.forEach(data ->
                data.nameProperty().bind(Bindings.concat(customTextWrap(data.getName()), "\n",
                        data.pieValueProperty(), "%")));
        observableData = FXCollections.observableList(getData().stream()
                .map(d -> String.format("%s %f", d.getName(), d.getPieValue()))
                .collect(Collectors.toList()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CustomPieChart // instanceof handles nulls
                && getLegend().equals(((CustomPieChart) other).getLegend())
                && observableData.equals(((CustomPieChart) other).observableData));
    }

    /**
     * It will slice the label input based on commas as delimiters. Each slice of the label will be on a new line to
     * simulate the text wrap feature of java
     * @param label label to be wrapped
     * @return wrapped label
     */
    private String customTextWrap(String label) {
        List<String> labelArr = Arrays.asList(label.split(","));
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : labelArr) {
            s.trim();
            stringBuilder.append(s);
            if (labelArr.indexOf(s) < labelArr.size() - 1) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * This class represents a customized legend panel for the pieChart
     */
    class CustomLegend extends GridPane {

        private static final int defaultVGap = 10;
        private static final int defaultHGap = 10;
        private static final int defaultSymbolXPos = 10;
        private static final int defaultSymbolYPos = 10;
        private static final int defaultSymbolWidth = 10;
        private static final int defaultSymbolHeight = 10;

        private List<String> labelList;

        CustomLegend(PieChart chart, List<Data> pieChartData) {
            labelList = new ArrayList<>();
            setHgap(defaultHGap);
            setVgap(defaultVGap);
            int index = 0;
            for (Data d : pieChartData) {
                Label legendText = createLabel(d.getName() + "   " + convertToMoney(d.getPieValue()));
                legendText.setWrapText(true);
                addRow(index, createSymbol(pieChartData.indexOf(d)), legendText);
                labelList.add(legendText.getText());
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
            Shape symbol = new Rectangle(defaultSymbolXPos, defaultSymbolYPos, defaultSymbolWidth, defaultSymbolHeight);
            symbol.getStyleClass().add(String.format("default-color%d-chart-pie-legend", index % 8));
            return symbol;
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                    || (other instanceof CustomLegend// instanceof handles nulls
                    && labelList.equals((((CustomLegend) other).labelList)));
        }

    }
}
