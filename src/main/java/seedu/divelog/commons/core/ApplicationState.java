package seedu.divelog.commons.core;

import seedu.divelog.commons.enums.Units;
import seedu.divelog.commons.events.UnitsChangedEvent;

/**
 * Stores the application state. Can be useful for implementing modes.
 * Currently only stores units.
 */
public class ApplicationState {
    private static ApplicationState applicationState = new ApplicationState();
    private Units commonUnits;

    private ApplicationState() {
        this.commonUnits = Units.METERS;
    }


    public static ApplicationState getInstance() {
        return applicationState;
    }

    /**
     * Sets the units of the application
     * @param unit
     */
    public void setUnits(Units unit) {
        if (unit != this.commonUnits) {
            EventsCenter ev = EventsCenter.getInstance();
            ev.post(new UnitsChangedEvent(unit));
            commonUnits = unit;
        }
    }

    /**
     * Gets the units of the application
     * @return
     */
    public Units getUnit() {
        return this.commonUnits;
    }
}
