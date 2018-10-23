//@@author LowGinWee
package seedu.address.model.schedule;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Activity {
    public static final String DATE_VALIDATION_REGEX = "\\d{2}/\\d{2}/\\d{4}";
    public static final String MESSAGE_DATE_CONSTRAINTS = "Date should be in \"DD/MM/YYYY\" and must be a valid date.";

    private final Date date;
    private final String activity;

    public Activity(Date date, String activity) {
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

    public static String getDateString (Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String dayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH).format(date);
        int month = cal.get(Calendar.MONTH);
        if (month == 0) {
            month = 12;
        }
        return new String(dayOfWeek + " " + cal.get(Calendar.DATE) + "/" + month
                + "/" + cal.get(Calendar.YEAR));
    }

    public static boolean isValidDate(String test) {

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);
        try {
            format.parse(test);
        } catch (ParseException e) {
            return false;
        }
        return test.matches(DATE_VALIDATION_REGEX);
    }
}
