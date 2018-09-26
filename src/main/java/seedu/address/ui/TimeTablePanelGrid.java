package seedu.address.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.TimeTable;

import java.net.URL;
import java.util.logging.Logger;

/**
 * TODO ALEXIS: working on this
 *
 * A visible grid for the timetable. It contains the TimeTablePanelTimeSlot(s) and TimeTablePanelDaySlot(s)
 *
 * Refer to TimeTablePanel to better understand the relationships
 *
 * Note: we specify a 0x0 GridPane in the .fxml; because this is the convention they gave us lol...
 * We need a dynamic timetable, but following the AB4 convention will only give us a static timetable.
 */

public class TimeTablePanelGrid extends UiPart<Region> {

    // TODO ALEXIS: fxml file: need to tweak!
    private static final String FXML = "TimeTablePanelGrid.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private GridPane timeTableGrid;

    public TimeTablePanelGrid() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        //loadBlankTimeTable(1,5,8,19); //dummy parameters: (1,5 means monday to friday) (8,19 means 8am to 7pm (1900 HRS))
        //loadDefaultTimeTable();
        //registerAsAnEventHandler(this);
    }

    /**
     * Loads a blank timetable of default size.
     */
    private void loadDefaultTimeTable() {
        this.clear();
        this.resize(1,5,8,19);

    }

    private void loadPersonPage(Person person) {

    }

    /**
     * Loads a timetable, using the given parameters.
     */
    public void loadTimeTable(TimeTable timeTable) {

    }

    /**
     * Loads a blank timetable with the given parameters.
     */
    private void loadBlankTimeTable(int dayStart, int dayEnd, int timeStart, int timeEnd) {

    }

    /**
     * Resizes current timetable
     */
    private void resize(int dayStart, int dayEnd, int timeStart, int timeEnd){


    }

    /**
     * Deletes entries in current timetable
     */
    private void clear(){


    }

    /*@Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection());
    }*/
}
