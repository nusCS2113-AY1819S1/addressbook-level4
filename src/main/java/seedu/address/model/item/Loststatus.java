package seedu.address.model.item;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Item's loststatus in the stock list.
 */
//@@author HeHaowei
public class Loststatus {
    public static final int LOSTSTATUS_LOST = 0;
    public static final int LOSTSTATUS_FOUND = 1;
    private final List<Integer> loststatus = new ArrayList<>(2);

    public Loststatus() {
        this(0, 0); }
    public Loststatus(int lost, int found) {
        loststatus.add(lost);
        loststatus.add(found);
    }
    public Integer getLoststatusLost() {
        return loststatus.get(LOSTSTATUS_LOST); }
    public Integer getLoststatusFound() {
        return loststatus.get(LOSTSTATUS_FOUND);
    }

    public void setLoststatusLost(Integer lost) {
        loststatus.set(LOSTSTATUS_LOST, lost);
    }
    public void setLoststatusFound(Integer found) {
        loststatus.set(LOSTSTATUS_FOUND, found);
    }
    public void setDefaultValues(int quantity) {
        loststatus.set(LOSTSTATUS_FOUND, quantity);
        loststatus.set(LOSTSTATUS_LOST, 0);

    }

}

