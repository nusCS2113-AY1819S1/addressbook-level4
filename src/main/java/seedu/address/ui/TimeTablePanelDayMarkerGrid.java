package seedu.address.ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * A invisible grid for the TimingMarkers to live in. It contains multiple TimeTablePanelTimingMarker(s)
 *
 * Refer to TimeTablePanel to better understand the relationships
 *
 * Note: we will scale the grid and add/remove columns in order to align with the TimeTablePanelMainGrid.
 * for now just a default size
 */

public class TimeTablePanelDayMarkerGrid extends UiPart<Region> {
    private static final String FXML = "TimeTablePanelDayMarkerGrid.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private enum Day {
        MON,
        TUE,
        WED,
        THU,
        FRI
    }

    @FXML
    private GridPane dayMarkerGrid;

    public TimeTablePanelDayMarkerGrid() {
        super(FXML);

        // To prevent triggering events for typing inside the TimeTablePanelTimeMarkerGrid
        getRoot().setOnKeyPressed(Event::consume);

        //TODO ALEXIS: resizer, add, init functions.

        populateDays();
    }

    /**
     * Populates the timings on the top row from 1000 to 1800
     */
    private void populateDays() {
        int daysAdded = 0;

        for (Day day : Day.values()) {
            Label label = new Label(day.name());
            dayMarkerGrid.add(label, 0, daysAdded);
            daysAdded++;
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }
}

