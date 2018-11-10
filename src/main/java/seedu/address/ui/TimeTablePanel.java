package seedu.address.ui;

import java.time.Duration;
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
import seedu.address.model.person.DeconflictTimeTable;
import seedu.address.model.person.TimeSlot;
import seedu.address.model.person.TimeTable;

/**
 * Contains all elements related to timetables
 */
public class TimeTablePanel extends UiPart<Region> {
    public static final LocalTime DEFAULT_START = LocalTime.parse("10:00");
    public static final LocalTime DEFAULT_END = LocalTime.parse("19:00");

    private static final String FXML = "TimeTablePanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private TimeTable timeTableLastLoaded;

    private LocalTime currStartHour;
    private LocalTime currEndHour;

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

        currStartHour = DEFAULT_START;
        currEndHour = DEFAULT_END;

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
        timeTablePanelTimeMarkerGrid = new TimeTablePanelTimeMarkerGrid(DEFAULT_START, DEFAULT_END, getCurrNumCol());
        timeTablePanelTimeMarkerGridPlaceholder.getChildren().add(timeTablePanelTimeMarkerGrid.getRoot());

        timeTablePanelDayMarkerGrid = new TimeTablePanelDayMarkerGrid();
        timeTablePanelDayMarkerGridPlaceholder.getChildren().add(timeTablePanelDayMarkerGrid.getRoot());

        timeTablePanelMainGrid = new TimeTablePanelMainGrid(getCurrNumCol());
        timeTablePanelMainGridPlaceholder.getChildren().add(timeTablePanelMainGrid.getRoot());
    }

    private void updateDimensions() {
        currRowDimensions = timeTablePanelMainGrid.getRoot().getHeight() / getCurrNumRow();
        currColDimensions = timeTablePanelMainGrid.getRoot().getWidth() / getCurrNumCol();
    }

    /**
     * Updates {@code currStartHour} and {@code currEndHour} according to the currently loaded {@code TimeTable}
     */
    private void updateStartAndEnd() {
        if (!timeTableLastLoaded.isEmpty() && timeTableLastLoaded.getEarliest().isBefore(DEFAULT_START)) {
            currStartHour = timeTableLastLoaded.getEarliest();
        } else {
            currStartHour = DEFAULT_START;
        }

        if (!timeTableLastLoaded.isEmpty() && timeTableLastLoaded.getLatest().isAfter(DEFAULT_END)) {
            currEndHour = timeTableLastLoaded.getLatest();
        } else {
            currEndHour = DEFAULT_END;
        }
    }

    /**
     * Loads a TimeTable from the TimeTable object it is given.
     */
    private void loadTimeTable(TimeTable timeTable) {
        timeTableLastLoaded = timeTable;
        updateStartAndEnd();

        timeTablePanelMainGrid.loadColumns(getCurrNumCol());
        timeTablePanelTimeMarkerGrid.loadColumns(currStartHour, currEndHour, getCurrNumCol());
        reloadTimeTable();
    }

    /**
     * Loads the {@code TimeTable} stored in this {@code TimeTablePanel}
     */
    private void reloadTimeTable() {
        timeTablePanelMainGrid.clearGrid();
        updateDimensions();

        for (TimeSlot timeSlot : timeTableLastLoaded.getTimeSlots()) {
            timeTablePanelMainGrid.addTimeSlot(timeSlot, currRowDimensions, currColDimensions, currStartHour);
        }

        if (timeTableLastLoaded instanceof DeconflictTimeTable) {
            DeconflictTimeTable inverse = ((DeconflictTimeTable) timeTableLastLoaded).getInverse();

            for (TimeSlot timeSlot : inverse.getTimeSlots()) {
                timeTablePanelMainGrid.addTimeSlot(timeSlot, currRowDimensions, currColDimensions, currStartHour);
            }
        }
    }

    // To be changed if 7-day week is desired
    private int getCurrNumRow() {
        return 5;
    }


    private int getCurrNumCol() {
        return (int) Duration.between(currStartHour, currEndHour).toHours();
    }

    @Subscribe
    private void handleTimeTableChangedEvent(TimeTableChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadTimeTable(event.getNewTimeTable());
    }
}
