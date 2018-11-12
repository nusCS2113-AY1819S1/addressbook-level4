package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;


//@@author jieliangang
/**
 * Removes an employee from an event.
 */
public class RemoveCommand extends Command {

    public static final String COMMAND_WORD = "remove";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes an employee from an event "
            + "by the index number used in the displayed employee list and displayed event list.\n"
            + "Parameters: PERSON_INDEX (must be a positive integer) "
            + PREFIX_FROM + "EVENT_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_FROM + "1";

    public static final String MESSAGE_REMOVE_PERSON_SUCCESS = "Removed Employee: %1$s from %2$s";
    public static final String MESSAGE_ABSENT_PERSON = "This employee is not originally in the event's attendee list.";
    public static final String MESSAGE_ATTENDEE_EMPTY = "The event selected has no invited persons.";

    private final Index indexEvent;
    private final Index indexPerson;

    public RemoveCommand(Index indexPerson, Index indexEvent) {
        this.indexPerson = indexPerson;
        this.indexEvent = indexEvent;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Event> lastShownEventList = model.getFilteredEventList();

        if (indexPerson.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (indexEvent.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Person person = lastShownPersonList.get(indexPerson.getZeroBased());
        Event event = lastShownEventList.get(indexEvent.getZeroBased());

        String personName = person.getName().toString();
        String personEmail = person.getEmail().toString();
        String eventName = event.getEventName().toString();

        if (event.isAttendeeEmpty()) {
            throw new CommandException(MESSAGE_ATTENDEE_EMPTY);
        } else if (!event.hasAttendee(personEmail)) {
            throw new CommandException(MESSAGE_ABSENT_PERSON);
        }

        Event updatedEvent = event.removePersonFromAttendee(personEmail);

        model.updateEvent(event, updatedEvent);
        model.commitEventList();

        return new CommandResult(String.format(MESSAGE_REMOVE_PERSON_SUCCESS, personName, eventName));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveCommand // instanceof handles nulls
                && indexEvent.equals(((RemoveCommand) other).indexEvent))
                && indexPerson.equals(((RemoveCommand) other).indexPerson); // state check
    }
}
