package seedu.address.model.item;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Item's lost_status in the stock list.
 */
//@@author HeHaowei
public class Lost_status {
    public static final int LOST_STATUS_LOST = 0;
    public static final int LOST_STATUS_FOUND = 1;
    private final List<Integer> lost_status = new ArrayList<>(2);

    public Lost_status() { this(0,0); }
    public Lost_status(int lost, int found) {
        lost_status.add(lost);
        lost_status.add(found);
    }
    public Integer getLost_statusLost() { return lost_status.get(LOST_STATUS_LOST); };
    public Integer getLost_statusFound() {
        return lost_status.get(LOST_STATUS_FOUND);
    }

    public void setLost_statusLost(Integer lost) {
        lost_status.set(LOST_STATUS_LOST, lost);
    }
    public void setLost_statusFound(Integer found) {
        lost_status.set(LOST_STATUS_FOUND, found);
    }
    public void setDefaultValues(int quantity) {
        lost_status.set(LOST_STATUS_FOUND, quantity);
        lost_status.set(LOST_STATUS_LOST, 0);
    }
}

