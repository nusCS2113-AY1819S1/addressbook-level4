package seedu.planner.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.ui.ShowPieChartStatsEvent;
import seedu.planner.commons.events.ui.ShowSummaryTableEvent;

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

    /**
     * Deletes existing tabs of the same text if any and creates a new one.
     * This prevents the system from creating duplicates.
     * @param tabs Can be 1 tab or multiple tabs given as input
     */
    private void createTabs(Tab... tabs) {
        for (Tab tab : tabs) {
            if (tabManager.getTabs().stream().anyMatch(t -> t.getText().equals(tab.getText()))) {
                tabManager.getTabs().remove(tab.getText());
            }
            tabManager.getTabs().add(tab);
            tabManager.getSelectionModel().select(tab);
        }
    }

    @Subscribe
    public void handleShowSummaryTableEvent(ShowSummaryTableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        CustomTab summaryTab = new CustomTab(SummaryDisplay.LABEL , new SummaryDisplay(event.data).getRoot());
        createTabs(summaryTab);
        show();
    }

    @Subscribe
    public void handleShowPieChartStatsEvent(ShowPieChartStatsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        CustomTab categoryTab = new CustomTab(CategoryBreakdown.LABEL, new CategoryBreakdown(event.data).getRoot());
        createTabs(categoryTab);
        show();
    }
}
