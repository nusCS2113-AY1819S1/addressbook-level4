package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that menu bar is clicked.
 */
public class NewMenuBarCmdClickedEvent extends BaseEvent {
    public final String menuCommand;

    public NewMenuBarCmdClickedEvent(String message) {
        this.menuCommand = message;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
