package seedu.planner.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.ui.ShowPieChartStatsEvent;
import seedu.planner.commons.events.ui.ShowSummaryTableEvent;
//@@author tenvinc
/**
 * This UI component is responsible for managing the tabs where the statistics will be displayed
 */
public class StatsDisplayPanel extends UiPart<Region> implements Switchable {

    private static final Logger logger = LogsCenter.getLogger(StatsDisplayPanel.class);

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
    private Node createTotalExpenseBreakdown(MixedPieChartDataList dataList) {
        if (dataList.isExpenseDataEmpty()) {
            Label label = new Label("Nothing has been found! Please input a more appropriate range:)");
            label.getStyleClass().add("label-bright");
            return new AnchorPane(label);
        }
        return new CategoryBreakdown(dataList.getExpenseChartLabelData(), dataList.getExpenseChartLegendData(),
                "Expense Breakdown for the period").getRoot();
    }

    /** Creates the CategoryBreakdown object with the total income and tag of each CategoryStatistic */
    private Node createTotalIncomeBreakdown(MixedPieChartDataList dataList) {
        if (dataList.isIncomeDataEmpty()) {
            Label label = new Label("Nothing has been found! Please input a more appropriate range:)");
            label.getStyleClass().add("label-bright");
            return new AnchorPane(label);
        }
        return new CategoryBreakdown(dataList.getIncomeChartLabelData(), dataList.getIncomeChartLegendData(),
                "Income Breakdown for the period").getRoot();
    }

    /**
     * Handles the ShowSummaryTableEvent that is passed from MainWindow's delegate function.
     * @param event event to be handled
     */
    public void handleShowSummaryTableEvent(ShowSummaryTableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Tab summaryTab = new Tab(SummaryDisplay.LABEL , new SummaryDisplay(event.data, event.totalExpense,
                event.totalIncome, event.total, event.totalLabel).getRoot());
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
        MixedPieChartDataList dataList = new MixedPieChartDataList(event.data);
        Tab categoryExpenseTab = new Tab("Category Breakdown For Expenses", createTotalExpenseBreakdown(
                dataList));
        Tab categoryIncomeTab = new Tab("Category Breakdown For Income", createTotalIncomeBreakdown(
                dataList));
        clearTabs();
        createTabs(categoryExpenseTab, categoryIncomeTab);
        show();
    }
}
