package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.security.SecurityAuthenticationException;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected EventsCenter eventsCenter;

    /**
     * Uses default {@link EventsCenter}
     */
    public Command() {
        this(EventsCenter.getInstance());
    }

    public Command(EventsCenter eventsCenter) {
        this.eventsCenter = eventsCenter;
        eventsCenter.registerHandler(this);
    }

    protected void raise(BaseEvent event) {
        eventsCenter.post(event);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model, CommandHistory history) throws CommandException,
            SecurityAuthenticationException;

}
