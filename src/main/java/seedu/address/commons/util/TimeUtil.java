//@@author XiaoYunhan
package seedu.address.commons.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.GregorianCalendar;

/**
 * time-related methods for Deadline Notification.
 */
public class TimeUtil {

    /**
     * Generator of type of Date
     */
    private static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Return the current time in the type of Date
     */
    public Date getCurrentDate() {
        return new Date();
    }

    /**
     * Calculate the day interval between two Date variables
     * Return the day intervals (day) in the type of integer
     */
    public int getDayInterval(Date earlyDay, Date lateDay) {
        long interval = lateDay.getTime() - earlyDay.getTime();
        return (int) (interval / (1000*60*60*24));
    }

    /**
     * Convert the Date-type input to String-type
     *  in the form of "dd/MM/yyyy"
     */
    public String dateToStringConverter(Date inputDate) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(inputDate);
    }

    /**
     * Convert the input String-type to Date-type
     *  in the form of "dd/MM/yyyy"
     */
    public Date stringToDateConverter(String inputDate) {
        //int year = Integer.parseInt(inputDate.substring(6, 10));
        //int month = Integer.parseInt(inputDate.substring(3, 5));
        //int day = Integer.parseInt(inputDate.substring(0, 2));
        //return new GregorianCalendar(year, month, day).getTime();
        return parseDate(inputDate);
    }

}
