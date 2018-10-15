package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.time.LocalDate;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Attendees;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.StartTime;
import seedu.address.model.person.Person;

//@@author jieliangang
/**
 * Invites an existing person to an existing event.
 */
public class InviteCommand extends Command {

    public static final String COMMAND_WORD = "invite";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Invites an existing person to an existing event "
            + "by the index number used in the displayed person list and displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TO + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_TO + "2";

    public static final String MESSAGE_INVITE_PERSON_SUCCESS = "Invited Person: %1$s to %2$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the event.";

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
        Attendees attendeesList = event.getAttendees();

        if (!attendeesList.isSetEmpty() && attendeesList.hasName(personName)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        Event updatedEvent = updateList(event, personName);

        model.updateEvent(event, updatedEvent);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_INVITE_PERSON_SUCCESS, personName, event.getEventName()));
    }

    /**
     * Add new name to an event attendees list.
     *
     * @param event The event to be updated.
     * @param personName The person's name to be added to the attendees list.
     * @return An updated event with the person's name in the attendees list.
     */
    private static Event updateList(Event event, String personName) {
        assert event != null;
        Attendees updatedAttendee = event.getAttendees().addName(personName);
        EventName eventName = event.getEventName();
        Description description = event.getDescription();
        Location location = event.getLocation();
        StartTime startTime = event.getStartTime();
        EndTime endTime = event.getEndTime();
        LocalDate date = event.getDate();

        return new Event(eventName, description, date, startTime, endTime, location, updatedAttendee);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InviteCommand // instanceof handles nulls
                && indexEvent.equals(((InviteCommand) other).indexEvent))
                && indexPerson.equals(((InviteCommand) other).indexPerson); // state check
    }

}
