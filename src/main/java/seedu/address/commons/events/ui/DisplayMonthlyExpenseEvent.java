package seedu.address.commons.events.ui;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to display the monthly expense page.
 */
public class DisplayMonthlyExpenseEvent extends BaseEvent {

    private final HashMap<String, String> monthlyData;

    public DisplayMonthlyExpenseEvent(HashMap<String, String> monthlyData) {
        this.monthlyData = monthlyData;
    }

    public HashMap<String, String> getMonthlyData() {
        requireNonNull(this.monthlyData);
        return this.monthlyData;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
