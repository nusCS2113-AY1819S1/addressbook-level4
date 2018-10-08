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
 * This is a visible grid for the timetable.
 * It will contain children: TimeTablePanelTimeSlot(s) and TimeTablePanelDaySlot(s)
 *
 * Refer to TimeTablePanel class to better understand the relationships
 */

public class TimeTablePanelMainGrid extends UiPart<Region> {
    private static final String FXML = "TimeTablePanelMainGrid.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private GridPane mainGrid;

    public TimeTablePanelMainGrid() {
        super(FXML);

        // To prevent triggering events for typing inside the timeTablePanelMainGrid
        getRoot().setOnKeyPressed(Event::consume);

        //registerAsAnEventHandler(this);
    }
}
