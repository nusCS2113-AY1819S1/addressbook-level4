package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.TimeTable;
import seedu.address.model.person.exceptions.TimeSlotOverlapException;
import seedu.address.security.SecurityAuthenticationException;

/**
 * Clears the user's {@code TimeTable}.
 */
public class ClearCommand extends Command {
    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_WORD_ALIAS = "c";
    public static final String MESSAGE_SUCCESS = "Your timetable has been cleared!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws SecurityAuthenticationException {
        requireNonNull(model);

        Person personToEdit = model.getUser();
        Person editedPerson = createEditedPerson(personToEdit);

        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateTimeTable(editedPerson.getTimeTable());
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Creates and returns a {@code Person} with the an empty {@code TimeTable}
     */
    private static Person createEditedPerson(Person personToEdit) throws TimeSlotOverlapException {
        requireNonNull(personToEdit);

        TimeTable timeTable = new TimeTable();

        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), timeTable, personToEdit.getFriends());
    }
}
