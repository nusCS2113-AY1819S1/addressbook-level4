package seedu.planner.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.ui.ShowPieChartStatsEvent;
import seedu.planner.commons.events.ui.ShowSummaryTableEvent;
import seedu.planner.model.summary.CategoryStatistic;

/**
 * This UI component is responsible for managing the tabs where the statistics will be displayed
 */
public class StatsDisplayPanel extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    private static final String FXML = "StatsDisplayPanel.fxml";

    @FXML
    private TabPane tabManager;

    public StatsDisplayPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
        hide();
    }

    private void show() {
        getRoot().toFront();
        getRoot().setVisible(true);
    }

    private void hide() {
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
    private CategoryBreakdown createTotalExpenseBreakdown(ObservableList<CategoryStatistic> data) {
        List<ChartData> chartDataList = new ArrayList();
        Double totalExpense = 0.0;
        for (CategoryStatistic d : data) {
            if (d.getTotalExpense() > 0.0) {
                chartDataList.add(new ChartData(d.getTags().toString(), d.getTotalExpense()));
                totalExpense += d.getTotalExpense();
            }
        }
        return new CategoryBreakdown(FXCollections.observableList(chartDataList), "Total Expense for the period",
                totalExpense);
    }

    /** Creates the CategoryBreakdown object with the total income and tag of each CategoryStatistic */
    private CategoryBreakdown createTotalIncomeBreakdown(ObservableList<CategoryStatistic> data) {
        List<ChartData> chartDataList = new ArrayList();
        Double totalIncome = 0.0;
        for (CategoryStatistic d : data) {
            if (d.getTotalIncome() > 0.0) {
                chartDataList.add(new ChartData(d.getTags().toString(), d.getTotalIncome()));
                totalIncome += d.getTotalIncome();
            }
        }
        return new CategoryBreakdown(FXCollections.observableList(chartDataList), "Total Income for the period",
                totalIncome);
    }

    @Subscribe
    public void handleShowSummaryTableEvent(ShowSummaryTableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        CustomTab summaryTab = new CustomTab(SummaryDisplay.LABEL , new SummaryDisplay(event.data).getRoot());
        clearTabs();
        createTabs(summaryTab);
        show();
    }

    @Subscribe
    public void handleShowPieChartStatsEvent(ShowPieChartStatsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        CustomTab categoryExpenseTab = new CustomTab("Category Breakdown For Expenses", createTotalExpenseBreakdown(
                event.data).getRoot());
        CustomTab categoryIncomeTab = new CustomTab("Category Breakdown For Income", createTotalIncomeBreakdown(
                event.data).getRoot());
        clearTabs();
        createTabs(categoryExpenseTab, categoryIncomeTab);
        show();
    }
}
