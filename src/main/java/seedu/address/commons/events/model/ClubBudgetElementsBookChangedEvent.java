package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyClubBudgetElementsBook;

/** Indicates the ClubBudgetElementsBook in the model has changed*/
public class ClubBudgetElementsBookChangedEvent extends BaseEvent {

    public final ReadOnlyClubBudgetElementsBook data;

    public ClubBudgetElementsBookChangedEvent(ReadOnlyClubBudgetElementsBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of club budget elements " + data.getClubsList().size();
    }
}
