//@@author XiaoYunhan
package seedu.address.logic;

import seedu.address.commons.util.TimeUtil;

import java.util.Date;

public class DeadlineNotification {

    private TimeUtil timeUtil = new TimeUtil();

    public boolean notifyOrNot (Date currentTaskDate) {
        int interval = timeUtil.getDayInterval(currentTaskDate, timeUtil.getCurrentDate());
        return interval <= 7 && interval >=0;
    }
}
