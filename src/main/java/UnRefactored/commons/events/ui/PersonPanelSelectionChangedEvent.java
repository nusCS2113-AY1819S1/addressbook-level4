package UnRefactored.commons.events.ui;

import UnRefactored.commons.events.BaseEvent;
import seedu.address.model.task.Task;

/**
 * Represents a selection change in the Person List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final Task newSelection;

    public PersonPanelSelectionChangedEvent(Task newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Task getNewSelection() {
        return newSelection;
    }
}
