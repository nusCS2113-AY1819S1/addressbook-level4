package seedu.planner.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.util.Pair;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.ui.UpdateWelcomePanelEvent;
import seedu.planner.model.Model;
import seedu.planner.model.summary.CategoryStatistic;
import seedu.planner.model.summary.CategoryStatisticsList;

/**
 * UI component that displays the default welcome page with statistic of the current month and a welcome message
 */
public class WelcomePanel extends UiPart<Region> implements Switchable {

    private static final Logger logger = LogsCenter.getLogger(WelcomePanel.class);

    private static final String FXML = "WelcomePanel.fxml";

    private final double prefPieChartWidth = 300.0;
    private final double prefPieChartHeight = 300.0;

    @FXML
    private AnchorPane expenseStats;

    @FXML
    private AnchorPane incomeStats;

    @FXML
    private Label welcomeMessage;

    public WelcomePanel(Model model) {
        super(FXML);
        welcomeMessage.setText("Welcome to FinancialPlanner!\n"
                + "To start off, press F1 or type help into the command box above for the help windows! Enjoy!");
        populateUi(new CategoryStatisticsList(model.getRecordsThisMonth()).getReadOnlyStatsList());
        registerAsAnEventHandler(this);
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

    /** Creates the CategoryBreakdown object with the total expense and tag of each CategoryStatistic */
    private Node createTotalExpenseBreakdown(Pair<ObservableList<ChartData>, Double> chartData) {
        if (chartData.getKey().size() == 0) {
            Label label = new Label("Nothing has been found! Please input a more appropriate range:)");
            return new AnchorPane(label);
        }
        CategoryBreakdown categoryBreakdown = new CategoryBreakdown(chartData.getKey(), "Total Expense for the period",
                chartData.getValue());
        categoryBreakdown.setPieChartSize(prefPieChartWidth, prefPieChartHeight);
        categoryBreakdown.disableLegend();
        categoryBreakdown.setTitlePosition(Side.BOTTOM);
        return categoryBreakdown.getRoot();
    }


    /** Extracts the label and expense information and places it in a list of ChartData */
    private Pair< ObservableList<ChartData>, Double> extractExpenseChartData(ObservableList<CategoryStatistic> data) {
        List<ChartData> chartDataList = new ArrayList();
        Double totalExpense = 0.0;
        for (CategoryStatistic d : data) {
            if (d.getTotalExpense() > 0.0) {
                chartDataList.add(new ChartData(d.getTags().toString(), d.getTotalExpense()));
                totalExpense += d.getTotalExpense();
            }
        }
        return new Pair<>(FXCollections.observableList(chartDataList), totalExpense);
    }

    /** Creates the CategoryBreakdown object with the total income and tag of each CategoryStatistic */
    private Node createTotalIncomeBreakdown(Pair<ObservableList<ChartData>, Double> chartData) {
        if (chartData.getKey().size() == 0) {
            Label label = new Label("Nothing has been found! Please input a more appropriate range:)");
            return new AnchorPane(label);
        }
        CategoryBreakdown categoryBreakdown = new CategoryBreakdown(chartData.getKey(), "Total Income for the period",
                chartData.getValue());
        categoryBreakdown.setPieChartSize(prefPieChartWidth, prefPieChartHeight);
        categoryBreakdown.disableLegend();
        categoryBreakdown.setTitlePosition(Side.BOTTOM);
        return categoryBreakdown.getRoot();
    }

    /** Extracts the label and income information and places it in a list of ChartData */
    private Pair< ObservableList<ChartData>, Double> extractIncomeChartData(ObservableList<CategoryStatistic> data) {
        List<ChartData> chartDataList = new ArrayList();
        Double totalIncome = 0.0;
        for (CategoryStatistic d : data) {
            if (d.getTotalIncome() > 0.0) {
                chartDataList.add(new ChartData(d.getTags().toString(), d.getTotalIncome()));
                totalIncome += d.getTotalIncome();
            }
        }
        return new Pair<>(FXCollections.observableList(chartDataList), totalIncome);
    }

    /** Populates the welcome panel with its UI elements */
    private void populateUi(ObservableList<CategoryStatistic> toDisplay) {
        Pair<ObservableList<ChartData>, Double> expenseChartData = extractExpenseChartData(toDisplay);
        if (expenseChartData.getKey().size() == 0) {
            return;
        }
        Node expenseNode = createTotalExpenseBreakdown(expenseChartData);
        expenseStats.getChildren().add(expenseNode);
        Pair<ObservableList<ChartData>, Double> incomeChartData = extractIncomeChartData(toDisplay);
        if (incomeChartData.getKey().size() == 0) {
            return;
        }
        Node incomeNode = createTotalIncomeBreakdown(incomeChartData);
        incomeStats.getChildren().add(incomeNode);
    }

    /**
     * Handles UpdateWelcomePanelEvent passed from MainWindow's delegate function
     * @param event event to be handled
     */
    public void handleUpdateWelcomePanelEvent(UpdateWelcomePanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        populateUi(event.data);
        show();
    }
}
