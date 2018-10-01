package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * TODO ALEXIS: working on this
 *
 * A invisible grid for the TimingMarkers to live in. It contains multiple TimeTablePanelTimingMarker(s)
 *
 * Refer to TimeTablePanel to better understand the relationships
 *
 * Note: we will scale the grid and add/remove columns in order to align with the TimeTablePanelMainGrid.
 * for now just a default size
 */

public class TimeTablePanelTimingGrid extends UiPart<Region> {

    // TODO ALEXIS: fxml file: need to tweak!
    private static final String FXML = "TimeTablePanelTimingGrid.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private GridPane timingGridPlaceholder;

    public TimeTablePanelTimingGrid() {
        super(FXML);

        // To prevent triggering events for typing inside the TimeTablePanelTimingGrid
        getRoot().setOnKeyPressed(Event::consume);

        //TODO ALEXIS: resizer, add, init functions.
    }

}
