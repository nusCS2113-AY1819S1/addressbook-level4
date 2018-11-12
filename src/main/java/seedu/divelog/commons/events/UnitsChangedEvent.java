package seedu.divelog.commons.events;

import seedu.divelog.commons.enums.Units;

/**
 * Fires when the units are changed
 */
public class UnitsChangedEvent extends BaseEvent {
    private final Units unit;

    public UnitsChangedEvent(Units unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "UnitsChangedEvent fired. Units set to: " + unit;
    }
}
