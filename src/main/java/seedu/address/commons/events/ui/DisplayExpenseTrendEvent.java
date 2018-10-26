package seedu.address.commons.events.ui;

import static java.util.Objects.requireNonNull;

import java.util.TreeMap;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the expense trend page.
 */
public class DisplayExpenseTrendEvent extends BaseEvent {

    private final TreeMap<String, Double> expenseTrendData;

    public DisplayExpenseTrendEvent(TreeMap<String, Double> expenseTrendData) {
        this.expenseTrendData = expenseTrendData;
    }

    public TreeMap<String, Double> getexEenseTrendData() {
        requireNonNull(this.expenseTrendData);
        return this.expenseTrendData;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
