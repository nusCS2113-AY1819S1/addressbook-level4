package seedu.address.commons.events.ui;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to display the monthly expense page.
 */
public class DisplayMonthlyExpenseEvent extends BaseEvent {

    private final HashMap<String, String> monthlyData;
    private final String selectedMonth;

    public DisplayMonthlyExpenseEvent(HashMap<String, String> monthlyData, String selectedMonth) {
        this.monthlyData = monthlyData;
        this.selectedMonth = selectedMonth;
    }

    public HashMap<String, String> getMonthlyData() {
        requireNonNull(this.monthlyData);
        return this.monthlyData;
    }

    public String getSelectedMonth() {
        return this.selectedMonth;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
