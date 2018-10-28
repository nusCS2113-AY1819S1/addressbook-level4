package seedu.planner.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.ui.UpdateWelcomePanelEvent;
import seedu.planner.model.Model;
import seedu.planner.model.summary.CategoryStatistic;
import seedu.planner.model.summary.CategoryStatisticsList;

/**
 * UI component that displays the default welcome page with statistic of the current month and a welcome message
 */
public class WelcomePanel extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(WelcomePanel.class);

    private static final String FXML = "WelcomePanel.fxml";

    @FXML
    private AnchorPane expenseStats;

    @FXML
    private AnchorPane incomeStats;

    public WelcomePanel(Model model) {
        super(FXML);
        populateUi(new CategoryStatisticsList(model.getRecordsThisMonth()).getReadOnlyStatsList());
        registerAsAnEventHandler(this);
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
        return new CategoryBreakdown(FXCollections.observableList(chartDataList), "Total Expense for this month",
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
        return new CategoryBreakdown(FXCollections.observableList(chartDataList), "Total Income for this month",
                totalIncome);
    }

    /** Populates the welcome panel with its UI elements */
    private void populateUi(ObservableList<CategoryStatistic> toDisplay) {
        CategoryBreakdown categoryBreakdown = createTotalExpenseBreakdown(toDisplay);
        categoryBreakdown.setPieChartSize(450.0, 300.0);
        categoryBreakdown.disableLegend();
        categoryBreakdown.setTitlePosition(Side.BOTTOM);
        expenseStats.getChildren().add(categoryBreakdown.getRoot());
        categoryBreakdown = createTotalIncomeBreakdown(toDisplay);
        categoryBreakdown.setPieChartSize(450.0, 300.0);
        categoryBreakdown.disableLegend();
        categoryBreakdown.setTitlePosition(Side.BOTTOM);
        incomeStats.getChildren().add(categoryBreakdown.getRoot());
    }

    @Subscribe
    public void handleUpdateWelcomePanelEvent(UpdateWelcomePanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        populateUi(event.data);
    }
}
