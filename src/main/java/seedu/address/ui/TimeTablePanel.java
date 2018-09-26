package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

/**
 * TODO ALEXIS: currently morphing this from BrowserPanel into a TimeTablePanel.
 *
 * This is TimeTablePanel, it is a panel where the TimeTable elements reside in:
 * Contains these Classes:
 *
 * TimeTablePanel
 *  |-PanelTop (just a divider in javafx )
 *  |   |-TimetableTimings (visually the timing markers at the top of the grid; eg: 0900 or 1500)
 *  |
 *  |-PanelBottom (just a divider in javafx )
 *  |   |-TimetableGrid (visually the gridlines in the timetable)
 *  |       |---TimetableTimeslot (represents a timeSlot; visually a square inside the timetable, just like in NUSMODS)
 *  |       |---TimetableDay (represents a day marker on the leftmost column of timetable; visually a square that contains the day of the week)
 *  |
 *  |-UI logic:  (just to handle the logic of RENDERING/SCALING and ADDING/REMOVAL of timeslots and HANDLING TIMESLOT INDEXES, ETC)
 *
 *  
 *____________________
 */

public class TimeTablePanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";

    private static final String FXML = "TimeTablePanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public TimeTablePanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    private void loadPersonPage(Person person) {
        loadPage(SEARCH_PAGE_URL + person.getName().fullName);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection());
    }
}
