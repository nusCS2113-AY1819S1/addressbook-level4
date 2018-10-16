package seedu.planner.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.ui.ShowSummaryTableEvent;

/**
 * This UI component is responsible for displaying the summary requested by the user
 */
public class SummaryDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    private static final String FXML = "SummaryDisplay.fxml";

    @FXML
    private AnchorPane summaryDisplay;

    public SummaryDisplay() {
        super(FXML);
        hide();
        registerAsAnEventHandler(this);
    }

    private void show() {
        getRoot().toFront();
        summaryDisplay.setVisible(true);
    }

    private void hide() {
        getRoot().toBack();
        summaryDisplay.setVisible(false);
    }

    @Subscribe
    public void handleShowSummaryTableEvent(ShowSummaryTableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        show();
    }
}
