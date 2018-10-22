package seedu.address.ui;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.GroupPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.grade.Test;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
//@@author clara1234566
/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String STUDENT_GRADE_PAGE_URL = "/docs/StudentGradeSummary.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * The window to show student tests && grades.
     */
    private void loadPersonPage(Person person) {
        String urlToLoad = getClass().getResource(STUDENT_GRADE_PAGE_URL).toString();
        int i = 0;
        Set<Test> testList = new HashSet<>();
        testList.addAll(person.getTests());
        String json = "[";
        for (Test test: testList) {
            if (i++ == testList.size() - 1) {
                json = json + "{\"name\":\"" + test.getTestName() + "\",\"marks\":\"" + test.getMarks() + "\"}]";
            } else {
                json = json + "{\"name\":\"" + test.getTestName() + "\",\"marks\":\"" + test.getMarks() + "\"},";
            }
        }

        loadPage(urlToLoad + "?data=" + json);

    }

    private void loadGroupPage(Group group) {
        loadPage(SEARCH_PAGE_URL + group.getGroupName().groupName);
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

    @Subscribe
    private void handleGroupPanelSelectionChangedEvent(GroupPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadGroupPage(event.getNewSelection());
    }
}
