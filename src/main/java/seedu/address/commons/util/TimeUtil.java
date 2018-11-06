//@@author XiaoYunhan
package seedu.address.commons.util;

import java.util.Date;

/**
 * time-related methods for DeadlineNotification.
 */
public class TimeUtil {

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
    public int getDayInterval(Date firstDay, Date secondDay) {
        long interval = firstDay.getTime() - secondDay.getTime();
        return (int) interval/1440000;
    }

}
