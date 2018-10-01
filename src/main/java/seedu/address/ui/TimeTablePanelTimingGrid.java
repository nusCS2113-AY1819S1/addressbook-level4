package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private GridPane timingGrid;

    public TimeTablePanelTimingGrid() {
        super(FXML);

        // To prevent triggering events for typing inside the TimeTablePanelTimingGrid
        getRoot().setOnKeyPressed(Event::consume);

        //TODO ALEXIS: resizer, add, init functions.

        populateTimings();
    }

    /**
     * Populates the timings on the top row from 1000 to 1800
     */
    private void populateTimings() {
        int start_time = 1000;
        int end_time = 1800;

        for (int temp = start_time,col = 0; temp <= end_time; temp += 100, col++) {
            String myLabel = Integer.toString(temp);
            timingGrid.add(new Label(myLabel), col, 0);
        }
    }
}
