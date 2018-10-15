package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.TimeSlot;

/**
 *
 * This is a visible grid for the timetable.
 * It will contain children: TimeTablePanelTimeSlot(s)
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
    }

    public void clearGrid() {
        mainGrid.getChildren().retainAll(mainGrid.getChildren().get(0));
    }

    public void addTimeSlot(TimeSlot timeSlot, double currRowDimensions, double currColDimensions, int currStartHour, int currEndHour) {
        if (timeSlot.getStartTime().getHour() < currStartHour
                || timeSlot.getEndTime().getHour() > currEndHour
                || timeSlot.getDayOfWeek().getValue() > 5) {
            return;
        }

        TimeTablePanelTimeSlot panelTimeSlot = new TimeTablePanelTimeSlot(timeSlot, currRowDimensions, currColDimensions);
        mainGrid.add(panelTimeSlot.getBox(), getColIndex(timeSlot, currStartHour), getRowIndex(timeSlot));
    }

    private int getColIndex(TimeSlot timeSlot, int currStartHour) {
        return timeSlot.getStartTime().getHour() - currStartHour;
    }

    private int getRowIndex(TimeSlot timeSlot) {
        return timeSlot.getDayOfWeek().getValue() - 1;
    }
}
