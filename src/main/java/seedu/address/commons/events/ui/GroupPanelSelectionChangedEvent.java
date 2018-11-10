package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.group.Group;

/**
 * Represents a selection change in the GroupListPanel.
 */
public class GroupPanelSelectionChangedEvent extends BaseEvent {

    private final Group newSelection;

    /**
     * Receives group to be set.
     *
     * @param newSelection Group to be set.
     */
    public GroupPanelSelectionChangedEvent(Group newSelection) {
        this.newSelection = newSelection;
    }

    /**
     * Returns class's simple name.
     *
     * @return Class's simple name.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    /**
     * Returns {@code newSelection}.
     *
     * @return Group.
     */
    public Group getNewSelection() {
        return newSelection;
    }

}
