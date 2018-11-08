package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

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
 * Invites an employee to an event.
 */
public class InviteCommand extends Command {

    public static final String COMMAND_WORD = "invite";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Invites an employee to an event "
            + "by the index number used in the displayed employee list and displayed event list.\n"
            + "Parameters: PERSON_INDEX (must be a positive integer) "
            + PREFIX_TO + "EVENT_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_TO + "2";

    public static final String MESSAGE_INVITE_PERSON_SUCCESS = "Invited Employee: %1$s to %2$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This employee already exists in the event.";
    public static final String MESSAGE_CLASH_EVENT = "Unable to invite! %1$s clashes with %2$s's schedule.";

    private final Index indexEvent;
    private final Index indexPerson;

    public InviteCommand(Index indexPerson, Index indexEvent) {
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

        if (!event.isAttendeeEmpty() && event.hasAttendee(personEmail)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (model.hasClash(event, personEmail)) {
            throw new CommandException(String.format(MESSAGE_CLASH_EVENT, eventName, personName));
        }

        Event updatedEvent = event.createEventWithUpdatedAttendee(personEmail);

        model.updateEvent(event, updatedEvent);
        model.commitEventList();

        return new CommandResult(String.format(MESSAGE_INVITE_PERSON_SUCCESS, personName, eventName));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InviteCommand // instanceof handles nulls
                && indexEvent.equals(((InviteCommand) other).indexEvent))
                && indexPerson.equals(((InviteCommand) other).indexPerson); // state check
    }

}
