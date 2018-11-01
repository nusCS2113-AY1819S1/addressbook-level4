package seedu.address.model;

import java.util.Calendar;
/**
 * Calendar information initializer
 */
public class CalendarInfo {

    public final Calendar calendar = Calendar.getInstance();
    public final int firstDay;

    public CalendarInfo() {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        firstDay = calendar.get(Calendar.DAY_OF_WEEK);
    }

    public Calendar getCalendar() {
        return calendar;
    }

}
