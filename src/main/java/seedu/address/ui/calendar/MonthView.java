package seedu.address.ui.calendar;

import java.time.LocalDate;
import java.time.YearMonth;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import seedu.address.ui.UiPart;

/**
 * Supports the display of the month view of the calendar.
 */
public class MonthView extends UiPart<Region> {

    private static final String FXML = "MonthView.fxml";

    private static final int SUNDAY = 7;
    private static final int MAX_COLUMN = 6;
    private static final int MAX_ROW = 4;

    private int dateCount;
    private YearMonth currentYearMonth;
    private YearMonth viewYearMonth;
    private String[] datesToBePrinted;
    @FXML
    private Text calendarTitle;

    @FXML
    private GridPane gridCalendar;

    public MonthView() {
        super(FXML);

        currentYearMonth = YearMonth.now();
        viewYearMonth = currentYearMonth;

    }

    /**
     * Displays the month view.
     *
     * @param yearMonth Year and month in the YearMonth format.
     */
    public void getMonthView(YearMonth yearMonth) {
        viewYearMonth = yearMonth;

        int year = yearMonth.getYear();

        setMonthCalendarTitle(year, yearMonth.getMonth().toString());
        setMonthCalendarDatesAndEntries(year, yearMonth.getMonthValue());
    }

    /**
     * Sets the title of the calendar according to a specific month and year.
     */
    public void setMonthCalendarTitle(int year, String month) {
        calendarTitle.setText(month + " " + year);
    }

    /**
     * Sets the dates and entries of a month-view calendar according to the specific month and year.
     *
     * @param year Year represented as a 4-digit integer.
     * @param month Month represented by numbers from 1 to 12.
     */
    private void setMonthCalendarDatesAndEntries(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        int lengthOfMonth = startDate.lengthOfMonth();
        int startDay = getMonthStartDay(startDate);


        datesToBePrinted = new String[36];
        storeMonthDatesToBePrinted(lengthOfMonth);

        setFiveWeeksMonthCalendar(startDay);

        // If month has more than 5 weeks
        if (dateCount != lengthOfMonth) {
            setSixWeeksMonthCalendar(lengthOfMonth);
        }
    }

    /**
     * Sets the dates of a five-weeks month-view calendar into the {@code gridCalendar}.
     *
     * @param startDay Integer value of the day of week of the start day  of the month. Values ranges from 1 - 7,
     *                 representing the different days of the week.
     */
    private void setFiveWeeksMonthCalendar(int startDay) {
        dateCount = 1;
        for (int row = 0; row <= MAX_ROW; row++) {
            if (row == 0) {
                for (int column = startDay; column <= MAX_COLUMN; column++) {
                    Text dateToPrint = new Text(datesToBePrinted[dateCount]);
                    addMonthDate(dateToPrint, column, row);
                    dateCount++;
                }
            } else {
                for (int column = 0; column <= MAX_COLUMN; column++) {
                    Text dateToPrint = new Text(datesToBePrinted[dateCount]);
                    addMonthDate(dateToPrint, column, row);
                    dateCount++;
                }
            }
        }
    }

    /**
     * Sets the dates of the sixth week in a six-weeks month-view calendar into the {@code gridCalendar}.
     *
     * @param lengthOfMonth Integer value of the number of days in a month.
     */
    private void setSixWeeksMonthCalendar(int lengthOfMonth) {
        int remainingDays = lengthOfMonth - dateCount;

        for (int column = 0; column <= remainingDays; column++) {
            Text dateToPrint = new Text(datesToBePrinted[dateCount]);
            addMonthDate(dateToPrint, column, 0);
            dateCount++;
        }
    }

    /**
     * Gets the day of week of the start date of a particular month and year.
     *
     * @param startDate A LocalDate variable that represents the date, viewed as year-month-day. The day will always
     *                  be set as 1.
     * @return Integer value of the day of week of the start day  of the month. Values ranges from 1 - 7,
     *         representing the different days of the week.
     */
    private int getMonthStartDay(LocalDate startDate) {
        int startDay = startDate.getDayOfWeek().getValue();

        // Sunday is the first column in the calendar
        if (startDay == SUNDAY) {
            startDay = 0;
        }

        return startDay;
    }

    /**
     * Adds a particular date to the correct {@code column} and {@code row} in the {@code gridCalendar}.
     *
     * @param dateToPrint The formatted date text to be printed on the {@code gridCalendar}.
     * @param column The column number in {@code gridCalendar}. Column number should range from 0 to 6.
     * @param row The row number in {@code gridCalendar}. Row number should range from 0 to 4.
     */
    private void addMonthDate(Text dateToPrint, int column, int row) {
        // To update the JavaFX component from a non-JavaFX thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gridCalendar.add(dateToPrint, column, row);
            }
        });

        gridCalendar.setHalignment(dateToPrint, HPos.LEFT);
        gridCalendar.setValignment(dateToPrint, VPos.TOP);
        dateToPrint.setId("date" + String.valueOf(dateCount));
        dateToPrint.setFill(Color.WHITE);
    }

    /**
     * Stores the formatted date String to be printed on the {@code gridCalendar}.
     *
     * @param lengthOfMonth Integer value of the number of days in a month.
     */
    private void storeMonthDatesToBePrinted(int lengthOfMonth) {
        for (int date = 1; date <= 35; date++) {
            if (date <= lengthOfMonth) {
                datesToBePrinted[date] = "  " + String.valueOf(date);
            }
        }
    }

    /**
     * Checks if the dates are printed in the same row and column.
     */
    public boolean dateIsEqual(Object other) {
        MonthView monthView = (MonthView) other;

        for (int date = 1; date <= viewYearMonth.lengthOfMonth(); date++) {
            Node expectedText = gridCalendar.lookup("#date" + String.valueOf(date));
            int expectedRow = gridCalendar.getRowIndex(expectedText);
            int expectedColumn = gridCalendar.getColumnIndex(expectedText);

            Node actualText = monthView.gridCalendar.lookup("#date" + String.valueOf(date));
            int actualRow = monthView.gridCalendar.getRowIndex(actualText);
            int actualColumn = monthView.gridCalendar.getColumnIndex(actualText);

            return (expectedRow == actualRow) && (expectedColumn == actualColumn);
        }

        return false;
    }
}
