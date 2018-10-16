package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
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

public class TimeTablePanelTimeMarkerGrid extends UiPart<Region> {
    private static final String FXML = "TimeTablePanelTimeMarkerGrid.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private GridPane timingGrid;

    public TimeTablePanelTimeMarkerGrid(int startHour, int endHour) {
        super(FXML);

        // To prevent triggering events for typing inside the TimeTablePanelTimeMarkerGrid
        getRoot().setOnKeyPressed(Event::consume);

        //TODO ALEXIS: resizer, add, init functions.

        populateTimings(startHour, endHour);
    }

    public TimeTablePanelTimeMarkerGrid() {
        this(TimeTablePanel.DEFAULT_START_HOUR, TimeTablePanel.DEFAULT_END_HOUR);
    }

    /**
     * Populates the timings on the top row from 1000 to 1800
     */
    private void populateTimings(int startHour, int endHour) {
        for (int currHour = startHour, col = 0; currHour < endHour; currHour++, col += 2) {
            Label hourLabel = new Label(Integer.toString(currHour));
            GridPane.setHalignment(hourLabel, HPos.RIGHT);
            GridPane.setValignment(hourLabel, VPos.BOTTOM);
            timingGrid.add(hourLabel, col, 0);

            Label minuteLabel = new Label("00");
            GridPane.setHalignment(minuteLabel, HPos.LEFT);
            GridPane.setValignment(minuteLabel, VPos.BOTTOM);
            timingGrid.add(minuteLabel, col + 1, 0);
        }
    }
}
