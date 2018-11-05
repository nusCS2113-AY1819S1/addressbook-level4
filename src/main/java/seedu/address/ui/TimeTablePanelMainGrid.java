package seedu.address.ui;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.logging.Logger;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.TimeSlot;

/**
 * Placeholder grid for {@code TimeTablePanelTimeSlot}s on the center of {@code TimeTablePanel}
 */

public class TimeTablePanelMainGrid extends UiPart<Region> {
    private static final String FXML = "TimeTablePanelMainGrid.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private GridPane mainGrid;

    public TimeTablePanelMainGrid(int numCol) {
        super(FXML);
        loadColumns(numCol);

        // To prevent triggering events for typing inside the timeTablePanelMainGrid
        getRoot().setOnKeyPressed(Event::consume);
    }

    public void clearGrid() {
        mainGrid.getChildren().retainAll(mainGrid.getChildren().get(0));
    }

    /**
     * Loads a grid with the specified number of columns
     * @param numCol Number of columns to be loaded
     */
    public void loadColumns(int numCol) {
        mainGrid.getColumnConstraints().clear();

        for (int i = 0; i < numCol; i++) {
            ColumnConstraints col = new ColumnConstraints(5.0, 1000.0, 1000.0);
            mainGrid.getColumnConstraints().add(col);
        }
    }

    /**
     * Adds a {@code TimeTablePanelTimeSlot} to the {@code TimeTablePanelMainGrid}
     * @param input {@code TimeSlot} to add
     * @param currRowDim Dimensions of the rows in the current grid
     * @param currColDim Dimensions of the columns in the current grid
     * @param currStart Start hour in the grid
     */
    public void addTimeSlot(TimeSlot input, double currRowDim, double currColDim, LocalTime currStart) {

        // Currently only supports 5-day work week
        if (input.getDayOfWeek() == DayOfWeek.SATURDAY || input.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return;
        }

        TimeTablePanelTimeSlot panelTimeSlot = new TimeTablePanelTimeSlot(input, currRowDim, currColDim);
        mainGrid.add(panelTimeSlot.getBox(), getColIndex(input, currStart), getRowIndex(input));
    }

    private int getColIndex(TimeSlot timeSlot, LocalTime currStartHour) {
        return timeSlot.getStartTime().getHour() - currStartHour.getHour();
    }

    private int getRowIndex(TimeSlot timeSlot) {
        return timeSlot.getDayOfWeek().getValue() - 1;
    }
}
