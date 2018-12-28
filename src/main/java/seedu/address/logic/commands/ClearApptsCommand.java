package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Set;
import java.util.TreeSet;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appt.Appt;
import seedu.address.model.appt.ApptComparator;
import seedu.address.model.appt.ApptDate;
import seedu.address.model.person.Person;

//@@author brandonccm1996
/**
 * clears all appointments that are before a specified date for all persons
 */
public class ClearApptsCommand extends Command {
    public static final String COMMAND_WORD = "clearappts";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all appts that end before or on the date provided for all persons in the health book.\n"
            + "Parameters: " + PREFIX_APPT_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_APPT_DATE + "16-09-2018";

    public static final String MESSAGE_CLEAR_OLD_APPTS_SUCCESS = "Any appointments ending before or on the specified "
            + "date have been deleted.";
    public static final String MESSAGE_NO_OLD_APPTS = "There are no appointments ending before or on the specified "
            + "date to be deleted.";

    private final ApptDate dateInput;

    public ClearApptsCommand(ApptDate dateInput) {
        requireNonNull(dateInput);
        this.dateInput = dateInput;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        ReadOnlyAddressBook addressBook = model.getAddressBook();
        boolean hasApptsToBeCleared = false;

        for (Person personToEdit : addressBook.getPersonList()) {
            Set<Appt> oldAppts = personToEdit.getAppts();

            for (Appt appt : oldAppts) {
                if (appt.getEnd().value.toLocalDate().compareTo(dateInput.value) <= 0) {
                    hasApptsToBeCleared = true;
                    break;
                }
            }

            if (hasApptsToBeCleared) {
                break;
            }
        }

        if (!hasApptsToBeCleared) {
            throw new CommandException(MESSAGE_NO_OLD_APPTS);
        } else {
            for (Person personToEdit : addressBook.getPersonList()) {
                Set<Appt> apptsCopy = new TreeSet<>(new ApptComparator());
                Set<Appt> oldAppts = personToEdit.getAppts();

                for (Appt appt : oldAppts) {
                    apptsCopy.add(appt);
                    if (appt.getEnd().value.toLocalDate().compareTo(dateInput.value) <= 0) {
                        apptsCopy.remove(appt);
                    }
                }

                Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(),
                        personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getMedicalReports(),
                        personToEdit.getMedHistory(), apptsCopy, personToEdit.getNric(), personToEdit.getDateOfBirth(),
                        personToEdit.getHeight(), personToEdit.getWeight(), personToEdit.getGender(),
                        personToEdit.getBloodType(), personToEdit.getOccupation(), personToEdit.getMaritalStatus(),
                        personToEdit.getTags());

                model.updatePerson(personToEdit, editedPerson);
            }

            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            model.commitAddressBook();
            return new CommandResult(MESSAGE_CLEAR_OLD_APPTS_SUCCESS);
        }
    }
}
