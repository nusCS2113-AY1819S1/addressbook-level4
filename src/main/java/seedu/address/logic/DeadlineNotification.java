//@@author XiaoYunhan
package seedu.address.logic;

import java.util.Date;

import seedu.address.commons.util.TimeUtil;

/**
 * Deadline notification related methods
 */
public class DeadlineNotification {

    private TimeUtil timeUtil = new TimeUtil();

    /**
     * get the date of current task in type of Date
     * return true if requiring notification
     */
    public boolean notifyOrNot (Date currentTaskDate) {
        int interval = timeUtil.getDayInterval(currentTaskDate, timeUtil.getCurrentDate());
        return interval <= 7 && interval >= 0;
    }
}
