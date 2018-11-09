package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.EditEventDescriptor;
import static seedu.address.logic.commands.EditCommand.createEditedEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendee.Attendee;
import seedu.address.model.event.Event;
import seedu.address.model.user.Username;

/**
 * Removes an attendee from an event using the event's displayed index from the event manager
 * and the attendee's username.
 */
public class RemoveAttendeeCommand extends Command {

    public static final String COMMAND_WORD = "removeAttendee";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes an attendee from an event identified by the index number used in the displayed event list "
            + "and the attendee's username.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "USERNAME (must be a valid username registered for the event)\n"
            + "Example: " + COMMAND_WORD + " 1" + " Peter Parker";

    public static final String MESSAGE_REMOVE_ATTENDEE_SUCCESS = "Removed %1$s from event: %2$s";
    public static final String MESSAGE_INVALID_ATTENDEE = "Attendee is not registered for event.";

    private final Index targetIndex;
    private final Username targetAttendee;

    public RemoveAttendeeCommand(Index targetIndex, Username targetAttendee) {
        this.targetIndex = targetIndex;
        this.targetAttendee = targetAttendee;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGIN);
        }

        if (!model.getAdminStatus()) {
            throw new CommandException(MESSAGE_ADMIN);
        }

        List<Event> filteredEventList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= filteredEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventWithAttendee = filteredEventList.get(targetIndex.getZeroBased());
        Set<Attendee> attendeeSet = new HashSet<>(eventWithAttendee.getAttendance());

        if (!attendeeSet.remove(new Attendee(targetAttendee.toString()))) {
            throw new CommandException(MESSAGE_INVALID_ATTENDEE);
        }

        EditEventDescriptor removeAttendeeEventDescriptor = new EditEventDescriptor();
        removeAttendeeEventDescriptor.setAttendees(attendeeSet);
        Event eventAttendeeRemoved = createEditedEvent(eventWithAttendee, removeAttendeeEventDescriptor);

        model.updateEvent(eventWithAttendee, eventAttendeeRemoved);
        model.commitEventManager();

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_REMOVE_ATTENDEE_SUCCESS,
                targetAttendee.toString(), targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveAttendeeCommand // instanceof handles nulls
                && targetIndex.equals(((RemoveAttendeeCommand) other).targetIndex)
                && targetAttendee.equals(((RemoveAttendeeCommand) other).targetAttendee)); // state check
    }
}
