//@@author LowGinWee
package seedu.address.model.schedule;

import java.util.Calendar;
import java.util.Date;

public class Activity {
    private final Date date;
    private final String activity;
    public static final String DATE_VALIDATION_REGEX = "";
    public static final String MESSAGE_DATE_CONSTRAINTS = "Date should be in \"DD/MM/YYYY\".";

    public Activity(Date date, String activity){
            this.date = date;
            this.activity = activity;
    }

    public Date getDate() {
        return date;
    }

    public String getActivity() {
        return activity;
    }

    public static Date toDate(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static boolean isValidDate(String test){
        return test.matches(DATE_VALIDATION_REGEX);
    }
}
