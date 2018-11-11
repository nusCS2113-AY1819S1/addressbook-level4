package seedu.address.logic.commands.eventCommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Events.Event;
import seedu.address.model.Model;

/**
 * Deletes a ledger identified using it's displayed index from the address book.
 */
public class DeleteEventCommand extends Command {

    public static final String COMMAND_WORD = "deleteEvent";

    public static final String COMMAND_ALIAS = "dE";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the event name used in the displayed event list.\n"
            + "parameter:"
            + "[INDEX]\n"
            + "Example: " + COMMAND_WORD + " " + "1";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Delete Event: %1$s";

    private final Index targetIndex;

    public DeleteEventCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }
        Event eventToDelete= lastShownList.get(targetIndex.getZeroBased());
        model.deleteEvent(eventToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEventCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteEventCommand) other).targetIndex)); // state check
    }
}
