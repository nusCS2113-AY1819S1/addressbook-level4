package seedu.address.ui;

import java.util.logging.Logger;
import java.util.Calendar;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.CalendarInfo;

/**
 * Panel containing the list of persons.
 */
public class CalendarPanel extends UiPart<Region> {
    private static final String FXML = "CalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);

    private Calendar calendar;
    private int firstDay;

    CalendarInfo calendarInfo = new CalendarInfo();

    @FXML
    private GridPane calendarView = new GridPane();

    @FXML
    private Label date1;

    public CalendarPanel() {
        super(FXML);
        calendar = calendarInfo.getCalendar();
        firstDay = calendarInfo.firstDay;
        setGridPane(calendar);
        registerAsAnEventHandler(this);
    }

    private void setGridPane (Calendar calendar) {
        int numOfDays;
        int week;
        int day = firstDay;
        if (calendar.get(Calendar.MONTH) == Calendar.JANUARY
                || calendar.get(Calendar.MONTH) == Calendar.MARCH
                || calendar.get(Calendar.MONTH) == Calendar.MAY
                || calendar.get(Calendar.MONTH) == Calendar.JULY
                || calendar.get(Calendar.MONTH) == Calendar.AUGUST
                || calendar.get(Calendar.MONTH) == Calendar.OCTOBER
                || calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
            numOfDays = 31;
        } else if (calendar.get(Calendar.MONTH) == Calendar.FEBRUARY){
            numOfDays = 29;
        } else {
            numOfDays = 30;
        }
        //for (int i = 0; i < numOfDays; i++) {
            week = calendar.get(Calendar.WEEK_OF_MONTH);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            date1.setText(Integer.toString(1));
            calendarView.add(date1, (day%7), week);
            day++;
        //}
    }
    /*
    private void setEventHandlerForSelectionChangeEvent() {
        calendarView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue));
                    }
                });
    }
    */

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    /*
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }
    */

}
