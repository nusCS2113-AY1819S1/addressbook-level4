package seedu.address.ui;

import java.time.LocalTime;
import java.util.logging.Logger;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
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

    public TimeTablePanelTimeMarkerGrid(LocalTime start, LocalTime end, int numCols) {
        super(FXML);
        loadColumns(start, end, numCols);

        // To prevent triggering events for typing inside the TimeTablePanelTimeMarkerGrid
        getRoot().setOnKeyPressed(Event::consume);
    }

    /**
     * Loads a grid with the specified number of columns
     * @param numCol Number of columns to be loaded
     */
    public void loadColumns(LocalTime start, LocalTime end, int numCol) {
        timingGrid.getColumnConstraints().clear();
        timingGrid.getChildren().clear();

        // Add column to align with day markers
        timingGrid.getColumnConstraints().add(new ColumnConstraints(60.0, 60.0, 60.0));

        for (int i = 0; i < numCol * 2; i++) {
            ColumnConstraints col = new ColumnConstraints(5.0, 500.0, 500.0);
            timingGrid.getColumnConstraints().add(col);
        }

        populateTimings(start.getHour(), end.getHour());
    }

    /**
     * Populates the timings on the top row from {@code startHour} to {@code endHour}
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
