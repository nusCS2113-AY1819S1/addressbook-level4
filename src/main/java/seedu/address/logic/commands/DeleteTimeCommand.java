package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.TimeSlot;
import seedu.address.model.person.TimeTable;
import seedu.address.model.person.exceptions.TimeSlotDoesNotExistException;
import seedu.address.model.person.exceptions.TimeSlotOverlapException;

/**
 * Deletes a {@code TimeSlot} from the {@code TimeTable} of person at {@code Index index}
 */
public class DeleteTimeCommand extends Command {
    public static final String COMMAND_WORD = "deletetime";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a timeslot from your timetable.\n"
            + "Parameters: DAYOFWEEK START - END\n"
            + "Example: " + COMMAND_WORD + " Monday 8-10";

    public static final String MESSAGE_DELETE_TIMESLOT_SUCCESS = "Deleted timeslot: %1$s";
    public static final String MESSAGE_TIMESLOT_DOES_NOT_EXIST = "The timeslot to be deleted does not exist!";

    private final TimeSlot toDelete;
    private final Index index;

    /**
     * Creates an {@code DeleteTimeCommand} to delete the specified timeSlot
     */
    public DeleteTimeCommand(Index index, TimeSlot timeSlot) {
        requireAllNonNull(index, timeSlot);

        this.index = index;
        this.toDelete = timeSlot;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson;

        try {
            editedPerson = createEditedPerson(personToEdit, toDelete);
        } catch (TimeSlotDoesNotExistException e) {
            throw new CommandException(DeleteTimeCommand.MESSAGE_TIMESLOT_DOES_NOT_EXIST);
        }

        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateTimeTable(editedPerson.getTimeTable());
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_TIMESLOT_SUCCESS, toDelete));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * and the {@code TimeSlot toDelete} deleted.
     */
    private static Person createEditedPerson(Person personToEdit, TimeSlot toDelete) throws TimeSlotOverlapException {
        requireAllNonNull(personToEdit, toDelete);

        TimeTable timeTable = new TimeTable(personToEdit.getTimeTable());

        try {
            timeTable.removeTimeSlot(toDelete);
        } catch (TimeSlotDoesNotExistException e) {
            throw e;
        }

        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), timeTable, personToEdit.getFriends());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTimeCommand)) {
            return false;
        }

        // state check
        DeleteTimeCommand e = (DeleteTimeCommand) other;
        return index.equals(e.index)
                && toDelete.equals(e.toDelete);
    }
}
