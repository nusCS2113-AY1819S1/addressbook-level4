package seedu.recruit.commons.events.logic;

import seedu.recruit.commons.events.BaseEvent;
import seedu.recruit.logic.LogicState;

/** Indicatesa request to change the logic state of LogicManager*/

public class ChangeLogicStateEvent extends BaseEvent {
    public final LogicState newState;

    public ChangeLogicStateEvent(String state) {
        newState = new LogicState(state);
    }

    @Override
    public String toString() {
        return "changed logic state to " + newState.toString();
    }
}
