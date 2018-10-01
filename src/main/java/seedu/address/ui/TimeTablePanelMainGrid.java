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
 * This is a visible grid for the timetable.
 * It will contain children: TimeTablePanelTimeSlot(s) and TimeTablePanelDaySlot(s)
 *
 * Refer to TimeTablePanel class to better understand the relationships
 */

public class TimeTablePanelMainGrid extends UiPart<Region> {

    // TODO ALEXIS: fxml file: need to tweak!
    private static final String FXML = "TimeTablePanelMainGrid.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private GridPane mainGrid;


    public TimeTablePanelMainGrid() {
        super(FXML);

        // To prevent triggering events for typing inside the timeTablePanelMainGrid
        getRoot().setOnKeyPressed(Event::consume);

        populateDays();

        //registerAsAnEventHandler(this);
    }

    /**
     * Populates the days from monday to friday on the left-most column.
     */
    private void populateDays() {
        mainGrid.add(new Label("Monday"), 0, 0);
        mainGrid.add(new Label("Tuesday"), 0, 1);
        mainGrid.add(new Label("Wednesday"), 0, 2);
        mainGrid.add(new Label("Thursday"), 0, 3);
        mainGrid.add(new Label("Friday"), 0, 4);
    }
}
