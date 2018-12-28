package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_START;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appt.Appt;
import seedu.address.model.appt.ApptComparator;
import seedu.address.model.appt.ApptDateTime;
import seedu.address.model.person.Person;

//@@author brandonccm1996
/**
 * Deletes an appointment from a person's schedule.
 */
public class DeleteApptCommand extends Command {
    public static final String COMMAND_WORD = "deleteappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the appt identified by the index number used in the displayed person list and "
            + "the start time provided.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_APPT_START + "START\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_APPT_START + "16-09-2018 15:00";

    public static final String MESSAGE_DELETE_APPT_SUCCESS = "Removed appt from Person: %1$s";
    public static final String MESSAGE_APPT_NOT_FOUND = "The appt you are trying to delete cannot be found for the "
            + "patient.";

    private final Index index;
    private final ApptDateTime apptStart;

    public DeleteApptCommand(Index index, ApptDateTime apptStart) {
        requireAllNonNull(index, apptStart);
        this.index = index;
        this.apptStart = apptStart;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        boolean apptFound = false;

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Set<Appt> oldAppts = personToEdit.getAppts();
        Set<Appt> newAppts = new TreeSet<>(new ApptComparator());
        for (Appt oldAppt : oldAppts) {
            if (!oldAppt.getStart().equals(apptStart)) {
                newAppts.add(oldAppt);
            } else {
                apptFound = true;
            }
        }

        if (!apptFound) {
            throw new CommandException(MESSAGE_APPT_NOT_FOUND);
        }

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getMedicalReports(), personToEdit.getMedHistory(), newAppts,
                personToEdit.getNric(), personToEdit.getDateOfBirth(), personToEdit.getHeight(),
                personToEdit.getWeight(), personToEdit.getGender(), personToEdit.getBloodType(),
                personToEdit.getOccupation(), personToEdit.getMaritalStatus(), personToEdit.getTags());

        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message when the appt is deleted from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        return String.format(MESSAGE_DELETE_APPT_SUCCESS, personToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteApptCommand)) {
            return false;
        }

        // state check
        DeleteApptCommand e = (DeleteApptCommand) other;
        return index.equals(e.index) && apptStart.equals(e.apptStart);
    }
}
