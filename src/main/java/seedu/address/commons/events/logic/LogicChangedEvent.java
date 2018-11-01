package seedu.address.commons.events.logic;

import seedu.address.commons.events.BaseEvent;
import seedu.address.logic.Logic;

/**
 * Change in logic for logic related component
 */
public class LogicChangedEvent extends BaseEvent {

    public final Logic logic;

    public LogicChangedEvent(Logic logic) {
        this.logic = logic;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
