//@@author SHININGGGG
package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.CalendarInfo;

/**
 * Panel containing the list of persons.
 */
public class CalendarPanel extends UiPart<Region> {
    private static final String FXML = "CalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);

    private CalendarInfo calendarInfo = new CalendarInfo();

    private Calendar calendar;
    private int firstDay;

    @FXML
    private Text currentDate;

    @FXML
    private GridPane calendarView;

    public CalendarPanel() {
        super(FXML);
        calendar = calendarInfo.getCalendar();
        firstDay = calendarInfo.firstDay - 1;
        setCurrentDate();
        //String stringFirstDay = Integer.toString(firstDay);
        //setCalendar(calendar);
        //calendar = Calendar.getInstance();
        setCalendar(calendar);
        registerAsAnEventHandler(this);
    }

    private void setCurrentDate () {

        //Text currentDate = new Text();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        currentDate.setText("\n                                           " + dtf.format(now));
        currentDate.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        currentDate.setFill(Color.ORANGE);
        //currentDate.setLayoutY(20);
        //currentDate.setLayoutX(0);
        //currentDate.setLayoutY(0);
    }

    private void setCalendar (Calendar calendar) {
        System.out.println("The first day of the whole month is " + firstDay);
        calendarView.setMinSize(200, 200);
        calendarView.setVgap(30);
        calendarView.setHgap(20);
        calendarView.setAlignment(Pos.CENTER);


        //calendarView.add(currentDate, 3, 0);

        for (int j = 0; j < 7; j++) {
            if (j == 0) {
                Text text = new Text("Sun");
                text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
                text.setFill(Color.WHEAT);
                calendarView.add(text, 0, 0);
            } else if (j == 1) {
                Text text = new Text("Mon");
                text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
                text.setFill(Color.WHEAT);
                calendarView.add(text, 1, 0);
            } else if (j == 2) {
                Text text = new Text("Tue");
                text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
                text.setFill(Color.WHEAT);
                calendarView.add(text, 2, 0);
            } else if (j == 3) {
                Text text = new Text("Wed");
                text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
                text.setFill(Color.WHEAT);
                calendarView.add(text, 3, 0);
            } else if (j == 4) {
                Text text = new Text("Thur");
                text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
                text.setFill(Color.WHEAT);
                calendarView.add(text, 4, 0);
            } else if (j == 5) {
                Text text = new Text("Fri");
                text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
                text.setFill(Color.WHEAT);
                calendarView.add(text, 5, 0);
            } else {
                Text text = new Text("Sat");
                text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
                text.setFill(Color.WHEAT);
                calendarView.add(text, 6, 0);
            }
        }

        int numOfDays;
        int week = 1;
        int day = firstDay;
        if (calendar.get(Calendar.MONTH) == Calendar.JANUARY
            || calendar.get(Calendar.MONTH) == Calendar.MARCH
            || calendar.get(Calendar.MONTH) == Calendar.MAY
            || calendar.get(Calendar.MONTH) == Calendar.JULY
            || calendar.get(Calendar.MONTH) == Calendar.AUGUST
            || calendar.get(Calendar.MONTH) == Calendar.OCTOBER
            || calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
            numOfDays = 31;
        } else if (calendar.get(Calendar.MONTH) == Calendar.FEBRUARY) {
            numOfDays = 29;
        } else {
            numOfDays = 30;
        }

        for (int i = 1; i <= numOfDays; i++) {
            Text text = new Text(Integer.toString(i));
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            text.setFill(Color.WHEAT);
            calendarView.add(text, day, week);
            day = (day + 1) % 7;
            if (day == 0) {
                week++;
            }
        }

    }
}
