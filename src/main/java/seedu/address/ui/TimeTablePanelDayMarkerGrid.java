package seedu.address.ui;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 * Displays the days of the week on the left of {@code TimeTablePanel}
 */
public class TimeTablePanelDayMarkerGrid extends UiPart<Region> {
    private static final String FXML = "TimeTablePanelDayMarkerGrid.fxml";

    @FXML
    private GridPane dayMarkerGrid;

    public TimeTablePanelDayMarkerGrid() {
        super(FXML);
        populateDays();

        // To prevent triggering events for typing inside the TimeTablePanelTimeMarkerGrid
        getRoot().setOnKeyPressed(Event::consume);
    }

    /**
     * Populates the timings on the top row from 1000 to 1800
     */
    private void populateDays() {
        DayOfWeek[] days = DayOfWeek.values();

        for (int i = 0; i < 5; i++) {
            Label label = new Label((days[i].getDisplayName(TextStyle.SHORT, Locale.ENGLISH)).toUpperCase());
            dayMarkerGrid.add(label, 0, i);
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }
}

