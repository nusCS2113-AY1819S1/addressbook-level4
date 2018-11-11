package seedu.address.ui;

import java.time.YearMonth;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.ui.CalendarLayout;

//@@author linnnruo
/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    private CalendarLayout CalendarLayout;
    private YearMonth currentYearMonth;

    @FXML
    private StackPane calendar;

    public CalendarPanel() {
        super(FXML);

        CalendarLayout = new CalendarLayout();
        currentYearMonth = YearMonth.now();
        createMainView();
    }

    /**
     * Creates the view of the calendar
     */
    private void createMainView() {
        CalendarLayout.getCalendarLayout(currentYearMonth);
        calendar.getChildren().add(CalendarLayout.getRoot());
    }

    public CalendarLayout getCalendarLayout() {
        return CalendarLayout;
    }
}
