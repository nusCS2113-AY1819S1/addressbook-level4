package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medhistory.MedHistory;
import seedu.address.model.medicalreport.MedicalReport;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.timetable.Appt;

/**
 * Edits optional details of an existing patient in the address book.
 */

public class AddInfoCommand extends Command {
    public static final String COMMAND_WORD = "addinfo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the additional information of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing information will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_NOT_EDITED = "At least one field to add additional information must be provided";
    public static final String MESSAGE_ADD_INFO_SUCCESS = "Added additional info to Person: %1$s";
    public static final String MESSAGE_DELETE_INFO_SUCCESS = "Removed additional info to person: %1$s";

    private final Index index;
    private final AddInfoPersonDescriptor addInfoPersonDescriptor;


    /**
     *
     * @param index of the person in the filter person list to edit the additional info
     * @param addInfoPersonDescriptor details to add to the person
     */
    public AddInfoCommand(Index index, AddInfoPersonDescriptor addInfoPersonDescriptor) {
        requireAllNonNull(index, addInfoPersonDescriptor);

        this.index = index;
        this.addInfoPersonDescriptor = new AddInfoPersonDescriptor(addInfoPersonDescriptor);
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireAllNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, addInfoPersonDescriptor);

        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_ADD_INFO_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, AddInfoPersonDescriptor addInfoPersonDescriptor) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Set<Appt> appts = personToEdit.getAppts();
        Set<MedicalReport> medicalReports = personToEdit.getMedicalReports();
        Set<MedHistory> medHistory = personToEdit.getMedHistory();
        Set<Tag> tags = personToEdit.getTags();

        Nric updatedNric = addInfoPersonDescriptor.getNric().orElse(personToEdit.getNric());
        DateOfBirth updatedDateOfBirth = addInfoPersonDescriptor.getDateOfBirth().orElse(personToEdit.getDateOfBirth());

        return new Person(name, phone, email, address, medicalReports, medHistory, appts,
                updatedNric, updatedDateOfBirth, tags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddInfoCommand)) {
            return false;
        }

        // state check
        AddInfoCommand e = (AddInfoCommand) other;
        return index.equals(e.index)
                && addInfoPersonDescriptor.equals(e.addInfoPersonDescriptor);
    }

    /**
     * Stores the details to add additional info to the person. Each non-empty field value will replace the
     * corresponding field value of the person
     */
    public static class AddInfoPersonDescriptor {
        private Nric nric;
        private DateOfBirth dateOfBirth;

        public AddInfoPersonDescriptor() {}

        public AddInfoPersonDescriptor(AddInfoPersonDescriptor toCopy) {
            setNric(toCopy.nric);
            setDateOfBirth(toCopy.dateOfBirth);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(nric, dateOfBirth);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }

        public void setDateOfBirth(DateOfBirth dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public Optional<DateOfBirth> getDateOfBirth() {
            return Optional.ofNullable(dateOfBirth);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof AddInfoPersonDescriptor)) {
                return false;
            }

            AddInfoPersonDescriptor e = (AddInfoPersonDescriptor) other;

            return getNric().equals(e.getNric())
                    && getDateOfBirth().equals(e.getDateOfBirth());
        }
    }
}
