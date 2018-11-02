package seedu.address.logic.commands.eventCommand;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.Events.Event;
import seedu.address.model.Events.EventName;
import seedu.address.model.Events.Venue;
import seedu.address.model.Events.Description;
import seedu.address.model.Events.EventDate;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Deletes a ledger identified using it's displayed index from the address book.
 */
public class DeleteEventCommand extends Command {

    public static final String COMMAND_WORD = "deleteEvent";

    public static final String COMMAND_ALIAS = "de/";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the event name used in the displayed event list.\n"
            + COMMAND_WORD
            + "parameter:"
            + PREFIX_NAME + " EVENT_NAME\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "Basketball training";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Delete Event: %1$s";

    private final EventName targetName;

    public DeleteEventCommand(EventName targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();
        Event eventToDelete = null;
        for (Event i : lastShownList) {
            if (i.getEventName().equals(targetName)){
                model.deleteEvent(i);
                eventToDelete = i;
                break;
            }
        }
        if (eventToDelete == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_NAME);
        }
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEventCommand // instanceof handles nulls
                && targetName.equals(((DeleteEventCommand) other).targetName)); // state check
    }
}
