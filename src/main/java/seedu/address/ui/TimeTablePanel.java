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
 * TODO: currently morphing this from browserpanel into a timetablepanel.
 * This is timetable panel, it is an object  that contains the other parts of the timetable:
 * (visually a bounding box.)
 *
 * Encapsulates these Classes:
 * __________________________________
 * UI objects:
 * >TimetableGrid (visually the gridlines in the timetable)
 * >TimetableTimings (visually the timing markers at the top of the grid; eg: 0900 or 1500)
 * >TimetableDay (represents a day marker on the leftmost column of timetable; visually a square that contains the day of the week)
 * >TimetableTimeslot (represents a timeSlot; visually a square inside the timetable, just like in NUSMODS)
 *
 * UI logic:
 * >TimetableLogic (this will handle logic for THIS specific instance of timetable.)
 * >>> it can vary the dimensions of the timetable based on input parameters, start-end time, etc)
 * >>> it will handle the adding and removal of timeslots.
 * >>> etc..
 * __________________________________
 */
public class TimetablePanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";

    private static final String FXML = "TimetablePanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public TimetablePanel() {
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
