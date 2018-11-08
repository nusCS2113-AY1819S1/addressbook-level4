package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventSingleDisplayPredicate;
import seedu.address.model.person.PersonAttendingEventPredicate;

/**
 * Lists all persons in the address book who are attending the specified event.
 */
public class ViewAttendeesCommand extends Command {

    public static final String COMMAND_WORD = "viewAttendees";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views all employees who are attending "
            + "the event indicated by the index number used in the displayed event list. "
            + "Also filters the event schedule and only shows the selected event\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Listed attendees of event: %1$s";

    private final Index indexEvent;
    private EventSingleDisplayPredicate eventPredicate;
    private PersonAttendingEventPredicate personPredicate;

    public ViewAttendeesCommand(Index indexEvent) {
        requireNonNull(indexEvent);

        this.indexEvent = indexEvent;

        // Predicates are defined only when the command is executed and the model is accessed.
        eventPredicate = null;
        personPredicate = null;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Event> lastShownList = model.getFilteredEventList();

        if (indexEvent.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToShow = lastShownList.get(indexEvent.getZeroBased());
        eventPredicate = new EventSingleDisplayPredicate(eventToShow);
        personPredicate = new PersonAttendingEventPredicate(eventToShow);

        model.updateFilteredEventList(eventPredicate);
        model.updateFilteredPersonList(personPredicate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, indexEvent.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof ViewAttendeesCommand)) {
            return false;
        }
        if (eventPredicate == null || personPredicate == null) {
            return indexEvent.equals(((ViewAttendeesCommand) other).indexEvent);
        }
        return (eventPredicate.equals(((ViewAttendeesCommand) other).eventPredicate)
                && personPredicate.equals(((ViewAttendeesCommand) other).personPredicate));
    }
}
