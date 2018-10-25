package seedu.planner.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.planner.commons.core.EventsCenter;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.ui.RequestDataForWelcomePanelEvent;
import seedu.planner.commons.events.ui.UpdateWelcomePanelEvent;

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
        EventsCenter.getInstance().post(new RequestDataForWelcomePanelEvent(this.getClass().getSimpleName()));
        registerAsAnEventHandler(this);
    }

    @Subscribe
    public void handleUpdateWelcomePanelEvent(UpdateWelcomePanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }
}
