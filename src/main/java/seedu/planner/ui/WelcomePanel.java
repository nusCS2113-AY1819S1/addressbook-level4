package seedu.planner.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.ui.UpdateWelcomePanelEvent;
import seedu.planner.model.Model;
import seedu.planner.model.summary.CategoryStatistic;
import seedu.planner.model.summary.CategoryStatisticsList;
//@@author tenvinc
/**
 * UI component that displays the default welcome page with statistic of the current month and a welcome message
 */
public class WelcomePanel extends UiPart<Region> implements Switchable {

    private static final Logger logger = LogsCenter.getLogger(WelcomePanel.class);

    private static final String FXML = "WelcomePanel.fxml";
    private static final String defaultWelcomeMessage = "Welcome to Savee!\n"
            + "To start off, press F1 or type help into the command box above for the help windows! Enjoy!";

    private final double prefPieChartWidth = 500.0;
    private final double prefPieChartHeight = 400.0;

    @FXML
    private AnchorPane expenseStats;

    @FXML
    private AnchorPane incomeStats;

    @FXML
    private Label welcomeMessage;

    public WelcomePanel(Model model) {
        super(FXML);
        welcomeMessage.setText(defaultWelcomeMessage);
        populateUi(new CategoryStatisticsList(model.getRecordsThisMonth()).getReadOnlyStatsList(),
                model.getCurrentMonth().toString());
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
    private Node createTotalExpenseBreakdown(MixedPieChartDataList dataList, String monthLabel) {
        CategoryBreakdown categoryBreakdown = new CurrentMonthPieChartDisplay(dataList.getExpenseChartLabelData(),
                dataList.getExpenseChartLegendData(), String.format("%s< Total expenses: $%.2f>",
                monthLabel, dataList.getTotalExpense()));
        categoryBreakdown.setPieChartSize(prefPieChartWidth, prefPieChartHeight);
        categoryBreakdown.disableLegend();
        categoryBreakdown.setTitlePosition(Side.BOTTOM);
        return categoryBreakdown.getRoot();
    }

    /** Creates the CategoryBreakdown object with the total income and tag of each CategoryStatistic */
    private Node createTotalIncomeBreakdown(MixedPieChartDataList dataList, String monthLabel) {
        CategoryBreakdown categoryBreakdown = new CurrentMonthPieChartDisplay(dataList.getIncomeChartLabelData(),
                dataList.getIncomeChartLegendData(), String.format("%s< Total income: $%.2f>",
                monthLabel, dataList.getTotalIncome()));
        categoryBreakdown.setPieChartSize(prefPieChartWidth, prefPieChartHeight);
        categoryBreakdown.disableLegend();
        categoryBreakdown.setTitlePosition(Side.BOTTOM);
        return categoryBreakdown.getRoot();
    }

    /** Populates the welcome panel with its UI elements */
    private void populateUi(ObservableList<CategoryStatistic> toDisplay, String monthLabel) {
        MixedPieChartDataList dataList = new MixedPieChartDataList(toDisplay);
        if (dataList.isExpenseDataEmpty()) {
            expenseStats.getChildren().clear();
        } else {
            Node expenseNode = createTotalExpenseBreakdown(dataList, monthLabel);
            expenseStats.getChildren().add(expenseNode);
        }
        if (dataList.isIncomeDataEmpty()) {
            incomeStats.getChildren().clear();
        } else {
            Node incomeNode = createTotalIncomeBreakdown(dataList, monthLabel);
            incomeStats.getChildren().add(incomeNode);
        }
    }

    /**
     * Handles UpdateWelcomePanelEvent passed from MainWindow's delegate function
     * @param event event to be handled
     */
    public void handleUpdateWelcomePanelEvent(UpdateWelcomePanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        populateUi(event.data, event.monthLabel);
        show();
    }
}
