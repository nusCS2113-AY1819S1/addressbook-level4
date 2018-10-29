package seedu.address.ui;

import java.time.LocalTime;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.TimeTableChangedEvent;
import seedu.address.model.person.TimeSlot;
import seedu.address.model.person.TimeTable;

/**
 * TODO ALEXIS: currently morphing this from BrowserPanel into a TimeTablePanel.
 *
 * This is TimeTablePanel, it is a panel where the TimeTable elements reside in:
 * Contains these Classes: (where * represents any number of)
 *
 * TimeTablePanel
 *  |-PanelTop (just a divider in javafx )
 *  |   |-TimeTablePanelTimeMarkerGrid (invisible grid to hold the timing objects)
 *  |       |-*TimeTablePanelTimingMarker (visually the timing markers at the top of the grid; eg: 0900 or 1500)
 *  |
 *  |-PanelBottom (just a divider in javafx )
 *  |   |-TimeTablePanelMainGrid (visually the gridlines in the timetable)
 *  |       |---*TimeTablePanelTimeSlot
 *  |       |       (represents a timeSlot; visually a square inside the timetable, just like in NUSMODS)
 *  |       |---*TimeTablePanelDaySlot
 *  |               (represents a daySlot on the leftmost column of timetable; visually a square)
 *  |
 *  |-UI logic:  (handles logic such as: SCALING of grid, ADDING/REMOVAL of timeslots, HANDLING TIMESLOT INDEXES, etc)
 *
 *____________________
 */

public class TimeTablePanel extends UiPart<Region> {
    public static final int DEFAULT_START_HOUR = 10;
    public static final int DEFAULT_END_HOUR = 19;

    private static final String FXML = "TimeTablePanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private TimeTable timeTableLastLoaded;

    // TODO: remove hardcoding here
    private LocalTime currStartHour = LocalTime.parse("10:00");
    private LocalTime currEndHour = LocalTime.parse("19:00");
    private int currNumRow = 5;
    private int currNumCol = 9;

    private double currRowDimensions;
    private double currColDimensions;

    private TimeTablePanelTimeMarkerGrid timeTablePanelTimeMarkerGrid;
    private TimeTablePanelDayMarkerGrid timeTablePanelDayMarkerGrid;
    private TimeTablePanelMainGrid timeTablePanelMainGrid;

    @FXML
    private GridPane timeTablePanelTimeMarkerGridPlaceholder;

    @FXML
    private GridPane timeTablePanelDayMarkerGridPlaceholder;

    @FXML
    private GridPane timeTablePanelMainGridPlaceholder;

    public TimeTablePanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        timeTableLastLoaded = new TimeTable();
        fillInnerParts();
        updateDimensions();

        timeTablePanelMainGrid.getRoot().widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                reloadTimeTable();
            }
        });

        registerAsAnEventHandler(this);
    }

    /**
     * Fills up all the placeholders of this TimeTablePanel.
     */
    private void fillInnerParts() {
        timeTablePanelTimeMarkerGrid = new TimeTablePanelTimeMarkerGrid();
        timeTablePanelTimeMarkerGridPlaceholder.getChildren().add(timeTablePanelTimeMarkerGrid.getRoot());

        timeTablePanelDayMarkerGrid = new TimeTablePanelDayMarkerGrid();
        timeTablePanelDayMarkerGridPlaceholder.getChildren().add(timeTablePanelDayMarkerGrid.getRoot());

        timeTablePanelMainGrid = new TimeTablePanelMainGrid();
        timeTablePanelMainGridPlaceholder.getChildren().add(timeTablePanelMainGrid.getRoot());
    }

    private void updateDimensions() {
        currRowDimensions = timeTablePanelMainGrid.getRoot().getHeight() / currNumRow;
        currColDimensions = timeTablePanelMainGrid.getRoot().getWidth() / currNumCol;
    }

    /**
     * Reloads the last loaded timetable. Used for when the window is resized
     */
    private void reloadTimeTable() {
        timeTablePanelMainGrid.clearGrid();
        updateDimensions();

        for (TimeSlot timeSlot : timeTableLastLoaded.getTimeSlots()) {
            timeTablePanelMainGrid.addTimeSlot(
                    timeSlot, currRowDimensions, currColDimensions, currStartHour, currEndHour);
        }

    }

    /**
     * Loads a TimeTable from the TimeTable object it is given.
     */
    private void loadTimeTable(TimeTable timeTable) {
        timeTableLastLoaded = timeTable;
        timeTablePanelMainGrid.clearGrid();
        updateDimensions();

        for (TimeSlot timeSlot : timeTable.getTimeSlots()) {
            timeTablePanelMainGrid.addTimeSlot(
                    timeSlot, currRowDimensions, currColDimensions, currStartHour, currEndHour);
        }
    }

    public double getCurrRowDimensions() {
        return currRowDimensions;
    }

    public double getCurrColDimensions() {
        return currColDimensions;
    }


    @Subscribe
    private void handleTimeTableChangedEvent(TimeTableChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadTimeTable(event.getNewTimeTable());
    }
}
