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
import seedu.address.model.person.exceptions.TimeSlotOverlapException;

/**
 * Adds a {@code TimeSlot} to the {@code TimeTable} of person at {@code Index index}
 */
public class AddTimeCommand extends Command {
    public static final String COMMAND_WORD = "addtime";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a timeslot to your timetable.\n"
            + "Parameters: DAYOFWEEK START - END\n"
            + "Example: " + COMMAND_WORD + " Monday 8-10";

    public static final String MESSAGE_ADD_TIMESLOT_SUCCESS = "Added timeslot: %1$s";
    public static final String MESSAGE_OVERLAP_TIMESLOT = "The timeslot added overlaps with an existing timeslot!";

    private final TimeSlot toAdd;
    private final Index index;

    /**
     * Creates an {@code AddTimeCommand} to add the specified {@code TimeSlot}
     */
    public AddTimeCommand(Index index, TimeSlot timeSlot) {
        requireAllNonNull(index, timeSlot);

        this.index = index;
        this.toAdd = timeSlot;
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
            editedPerson = createEditedPerson(personToEdit, toAdd);
        } catch (TimeSlotOverlapException e) {
            throw new CommandException(AddTimeCommand.MESSAGE_OVERLAP_TIMESLOT);
        }

        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateTimeTable(editedPerson.getTimeTable());
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_ADD_TIMESLOT_SUCCESS, toAdd));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * and the {@code TimeSlot toAdd} added.
     */
    private static Person createEditedPerson(Person personToEdit, TimeSlot toAdd) throws TimeSlotOverlapException {
        requireAllNonNull(personToEdit, toAdd);

        TimeTable timeTable = new TimeTable(personToEdit.getTimeTable());

        try {
            timeTable.addTimeSlot(toAdd);
        } catch (TimeSlotOverlapException e) {
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
        if (!(other instanceof AddTimeCommand)) {
            return false;
        }

        // state check
        AddTimeCommand e = (AddTimeCommand) other;
        return index.equals(e.index)
                && toAdd.equals(e.toAdd);
    }
}
