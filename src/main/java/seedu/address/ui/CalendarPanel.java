package seedu.address.ui;

import java.time.YearMonth;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.ui.calendar.MonthView;

//@@author linnnruoo
/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    private MonthView monthView;
    private YearMonth currentYearMonth;

    @FXML
    private StackPane calendar;

    public CalendarPanel() {
        super(FXML);

        monthView = new MonthView();
        createMainView();
    }

    /**
     * Creates the main view of the calendar, which by default, is the current month view.*
     */
    private void createMainView() {
        monthView.getMonthView(currentYearMonth);
        calendar.getChildren().add(monthView.getRoot());
    }

    public MonthView getMonthView() {
        return monthView;
    }
}
