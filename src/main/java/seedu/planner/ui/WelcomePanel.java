package seedu.planner.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.ui.UpdateWelcomePanelEvent;
import seedu.planner.model.summary.CategoryStatistic;

/**
 * UI component that displays the default welcome page with statistic of the current month and a welcome message
 */
public class WelcomePanel extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    private static final String FXML = "WelcomePanel.fxml";

    @FXML
    private AnchorPane expenseStats;

    @FXML
    private AnchorPane incomeStats;

    public WelcomePanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /** Populates the welcome panel with its UI elements */
    private void populateUi(ObservableList<CategoryStatistic> toDisplay) {
        CategoryBreakdown categoryBreakdown = new CategoryBreakdown(toDisplay, "expense");
        categoryBreakdown.setPieChartSize(600.0, 300.0);
        categoryBreakdown.disableLegend();
        expenseStats.getChildren().add(categoryBreakdown.getRoot());
        expenseStats.setTopAnchor(categoryBreakdown.getRoot(), 0.0);
        expenseStats.setBottomAnchor(categoryBreakdown.getRoot(), 0.0);
        expenseStats.setRightAnchor(categoryBreakdown.getRoot(), 0.0);
        expenseStats.setLeftAnchor(categoryBreakdown.getRoot(), 0.0);
        categoryBreakdown = new CategoryBreakdown(toDisplay, "income");
        categoryBreakdown.setPieChartSize(600.0, 300.0);
        categoryBreakdown.disableLegend();
        incomeStats.getChildren().add(categoryBreakdown.getRoot());
        incomeStats.setTopAnchor(categoryBreakdown.getRoot(), 0.0);
        incomeStats.setBottomAnchor(categoryBreakdown.getRoot(), 0.0);
        incomeStats.setRightAnchor(categoryBreakdown.getRoot(), 0.0);
        incomeStats.setLeftAnchor(categoryBreakdown.getRoot(), 0.0);
    }

    @Subscribe
    public void handleUpdateWelcomePanelEvent(UpdateWelcomePanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        populateUi(event.data);
    }
}
