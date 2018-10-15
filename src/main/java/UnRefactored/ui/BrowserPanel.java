package UnRefactored.ui;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import UnRefactored.commons.core.LogsCenter;
import UnRefactored.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.task.Task;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?title=";

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

    private void loadPersonPage(Task task) {
        try {
            URIBuilder uribuilder = new URIBuilder();
            URL path = MainApp.class.getResource(FXML_FILE_FOLDER + "DummySearchPage.html");
            uribuilder.addPath(path);
            uribuilder.addQuery("title", task.getTitle());
            uribuilder.addQuery("description", task.getDescription());
            uribuilder.addQuery("priorityLevel", task.getPriorityLevel().toString());
            logger.info(uribuilder.getURL());
            loadPage(uribuilder.getURL());
        } catch (MalformedURLException | UnsupportedEncodingException | URISyntaxException e) {
            logger.warning(e.getMessage());
        }
        //        URL url = MainApp.class.getResource(FXML_FILE_FOLDER + "DummySearchPage.html");
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
