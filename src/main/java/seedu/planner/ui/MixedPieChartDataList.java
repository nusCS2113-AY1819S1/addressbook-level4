package seedu.planner.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import seedu.planner.model.summary.CategoryStatistic;

/**
 * This class is responsible for converting data as a {@code ObservableList<CategoryStatistic>} into data that can be
 * easily read by the custom pieChart implementation which requires different values for the label and the legend
 * Label refers to the value after converting to percentages and legend is the original value.
 */
public class MixedPieChartDataList {

    private static final String untaggedLabel = "<<untagged>>";

    private List<PieChart.Data> expenseChartLegendData;

    private List<PieChart.Data> incomeChartLegendData;
    private List<PieChart.Data> expenseChartLabelData;
    private List<PieChart.Data> incomeChartLabelData;

    private Double totalIncome = 0.0;
    private Double totalExpense = 0.0;

    public MixedPieChartDataList(ObservableList<CategoryStatistic> data) {
        expenseChartLegendData = new ArrayList<>();
        incomeChartLegendData = new ArrayList<>();
        for (CategoryStatistic d : data) {
            addToExpenseLegendData(d);
            addToIncomeLegendData(d);
        }
        expenseChartLabelData = expenseChartLegendData.stream().map(d -> convertToPercentages(d, totalExpense))
                .collect(Collectors.toList());
        incomeChartLabelData = incomeChartLegendData.stream().map(d -> convertToPercentages(d, totalIncome))
                .collect(Collectors.toList());
    }

    /**
     * Process given data and adds new PieChart.Data to the incomeChartLegendData
     * @param data data to be added
     */
    private void addToIncomeLegendData(CategoryStatistic data) {
        if (data.getTotalIncome() > 0.0) {
            String label;
            if (data.getTags().isEmpty()) {
                label = untaggedLabel;
            } else {
                label = data.getTags().toString();
            }
            incomeChartLegendData.add(new PieChart.Data(label, data.getTotalIncome()));
            totalIncome += data.getTotalIncome();
        } else {
            return;
        }
    }

    /**
     * Process given data and adds new PieChart.Data to the expenseChartLegendData
     * @param data data to be added
     */
    private void addToExpenseLegendData(CategoryStatistic data) {
        if (data.getTotalExpense() > 0.0) {
            String label;
            if (data.getTags().isEmpty()) {
                label = untaggedLabel;
            } else {
                label = data.getTags().toString();
            }
            expenseChartLegendData.add(new PieChart.Data(label, data.getTotalExpense()));
            totalExpense += data.getTotalExpense();
        } else {
            return;
        }
    }

    /**
     * Computes the proportion of a given {@code PieChart.Data} data over the given total amount
     * @param data data to be converted
     * @param total reference total amount
     * @return
     */
    private PieChart.Data convertToPercentages(PieChart.Data data, Double total) {
        if (total > 0.0) {
            Double newValue = Double.parseDouble(String.format("%.2f", data.getPieValue() / total * 100.0));
            return new PieChart.Data(data.getName(), newValue);
        } else {
            return data;
        }
    }

    public boolean isExpenseDataEmpty() {
        return expenseChartLegendData.size() == 0 && expenseChartLabelData.size() == 0;
    }

    public boolean isIncomeDataEmpty() {
        return incomeChartLegendData.size() == 0 && incomeChartLabelData.size() == 0;
    }

    public List<PieChart.Data> getExpenseChartLegendData() {
        return expenseChartLegendData;
    }

    public List<PieChart.Data> getIncomeChartLegendData() {
        return incomeChartLegendData;
    }

    public List<PieChart.Data> getExpenseChartLabelData() {
        return expenseChartLabelData;
    }

    public List<PieChart.Data> getIncomeChartLabelData() {
        return incomeChartLabelData;
    }
}
