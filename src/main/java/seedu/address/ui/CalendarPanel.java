package seedu.address.ui;

import java.net.URL;
import java.time.YearMonth;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;

//@@author linnnruo
/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {

    public static final String BLANK_COLOR_PAGE = "CalendarBackground.html";
    public static final String DEFAULT_PAGE = "default.html";
    private static final String FXML = "CalendarPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private CalendarLayout calendarLayout;
    private YearMonth currentYearMonth;

    @FXML
    private StackPane calendar;
    @FXML
    private WebView browser;

    public CalendarPanel() {
        super(FXML);

        calendarLayout = new CalendarLayout();
        currentYearMonth = YearMonth.now();

        getRoot().setOnKeyPressed(Event::consume);
        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Creates the view of the calendar
     */
    private void createMainView() {
        calendarLayout.getCalendarLayout(currentYearMonth);
        calendar.getChildren().add(calendarLayout.getRoot());
        loadUpdatedPage();
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
    private void loadUpdatedPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + BLANK_COLOR_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        createMainView();
    }

    public CalendarLayout getCalendarLayout() {
        return calendarLayout;
    }
}
