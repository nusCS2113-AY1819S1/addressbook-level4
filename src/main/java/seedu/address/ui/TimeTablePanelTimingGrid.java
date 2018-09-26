package seedu.address.ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * TODO ALEXIS: working on this
 *
 * A invisible grid for the TimingMarkers to live in. It contains the TimeTablePanelTimingMarker(s)
 *
 * Refer to TimeTablePanel to better understand the relationships
 *
 * Note: we will scale the grid and add/remove columns in order to align with the TimeTablePanelMainGrid.
 * for now just a default size
 */

public class TimeTablePanelTimingGrid extends UiPart<Region>{

    // TODO ALEXIS: fxml file: need to tweak!
    private static final String FXML = "TimeTablePanelTimingGrid.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private GridPane timingGrid;


    public TimeTablePanelTimingGrid() {
        super(FXML);

        // To prevent triggering events for typing inside the TimeTablePanelTimingGrid
        getRoot().setOnKeyPressed(Event::consume);

        //TODO ALEXIS: resizer and init functions.
    }

    /**
     * Fills up the timingGrid with timings
     */
    private void populate(){
        int col;
        for (col=0; col<timingGrid.getColumnCount() ;col++) {
            timingGrid.add(new Rectangle(), col, 0);
        }
    }

}
