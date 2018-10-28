package seedu.planner.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.util.Pair;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.ui.ShowPieChartStatsEvent;
import seedu.planner.commons.events.ui.ShowSummaryTableEvent;
import seedu.planner.model.summary.CategoryStatistic;

/**
 * This UI component is responsible for managing the tabs where the statistics will be displayed
 */
public class StatsDisplayPanel extends UiPart<Region> implements Switchable {

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    private static final String FXML = "StatsDisplayPanel.fxml";

    private static final String untaggedLabel = "<<untagged>>";

    @FXML
    private TabPane tabManager;

    public StatsDisplayPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
        hide();
    }

    @Override
    public void show() {
        getRoot().toFront();
        getRoot().setVisible(true);
    }

    @Override
    public void hide() {
        getRoot().toBack();
        getRoot().setVisible(false);
    }

    /** Removes all existing tabs from the {@code TabPane} */
    private void clearTabs() {
        tabManager.getTabs().clear();
    }

    /**
     * Deletes existing tabs of the same text if any and creates a new one.
     * This prevents the system from creating duplicates.
     * @param tabs Can be 1 tab or multiple tabs given as input
     */
    private void createTabs(Tab... tabs) {
        for (Tab tab : tabs) {
            tabManager.getTabs().add(tab);
            tabManager.getSelectionModel().select(tab);
        }
    }

    /** Creates the CategoryBreakdown object with the total expense and tag of each CategoryStatistic */
    private Node createTotalExpenseBreakdown(ObservableList<CategoryStatistic> data) {
        Pair< ObservableList<ChartData>, Double> chartData = extractExpenseChartData(data);
        if (chartData.getKey().size() == 0) {
            Label label = new Label("Nothing has been found! Please input a more appropriate range:)");
            label.getStyleClass().add("label-bright");
            return new AnchorPane(label);
        }
        return new CategoryBreakdown(chartData.getKey(), "Total Expense for the period",
                chartData.getValue()).getRoot();
    }


    /** Extracts the label and expense information and places it in a list of ChartData */
    private Pair< ObservableList<ChartData>, Double> extractExpenseChartData(ObservableList<CategoryStatistic> data) {
        List<ChartData> chartDataList = new ArrayList();
        Double totalExpense = 0.0;
        for (CategoryStatistic d : data) {
            if (d.getTotalExpense() > 0.0) {
                String label;
                if (d.getTags().isEmpty()) {
                    label = untaggedLabel;
                } else {
                    label = d.getTags().toString();
                }
                chartDataList.add(new ChartData(label, d.getTotalExpense()));
                totalExpense += d.getTotalExpense();
            }
        }
        return new Pair<>(FXCollections.observableList(chartDataList), totalExpense);
    }

    /** Creates the CategoryBreakdown object with the total income and tag of each CategoryStatistic */
    private Node createTotalIncomeBreakdown(ObservableList<CategoryStatistic> data) {
        Pair< ObservableList<ChartData>, Double> chartData = extractIncomeChartData(data);
        if (chartData.getKey().size() == 0) {
            Label label = new Label("Nothing has been found! Please input a more appropriate range:)");
            label.getStyleClass().add("label-bright");
            return new AnchorPane(label);
        }
        return new CategoryBreakdown(chartData.getKey(), "Total Income for the period",
                chartData.getValue()).getRoot();
    }

    /** Extracts the label and income information and places it in a list of ChartData */
    private Pair< ObservableList<ChartData>, Double> extractIncomeChartData(ObservableList<CategoryStatistic> data) {
        List<ChartData> chartDataList = new ArrayList();
        Double totalIncome = 0.0;
        for (CategoryStatistic d : data) {
            if (d.getTotalIncome() > 0.0) {
                String label;
                if (d.getTags().isEmpty()) {
                    label = untaggedLabel;
                } else {
                    label = d.getTags().toString();
                }
                chartDataList.add(new ChartData(label, d.getTotalIncome()));
                totalIncome += d.getTotalIncome();
            }
        }
        return new Pair<>(FXCollections.observableList(chartDataList), totalIncome);
    }

    /**
     * Handles the ShowSummaryTableEvent that is passed from MainWindow's delegate function.
     * @param event event to be handled
     */
    public void handleShowSummaryTableEvent(ShowSummaryTableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Tab summaryTab = new Tab(SummaryDisplay.LABEL , new SummaryDisplay(event.data).getRoot());
        clearTabs();
        createTabs(summaryTab);
        show();
    }

    /**
     * Handles the ShowPieChartStatsEvent that is passed from MainWindow's delegate function.
     * @param event event to be handled
     */
    public void handleShowPieChartStatsEvent(ShowPieChartStatsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Tab categoryExpenseTab = new Tab("Category Breakdown For Expenses", createTotalExpenseBreakdown(
                event.data));
        Tab categoryIncomeTab = new Tab("Category Breakdown For Income", createTotalIncomeBreakdown(
                event.data));
        clearTabs();
        createTabs(categoryExpenseTab, categoryIncomeTab);
        show();
    }
}
