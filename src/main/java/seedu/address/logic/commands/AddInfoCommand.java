package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_MARITAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_OCCUPATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_WEIGHT;
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
import seedu.address.model.appt.Appt;
import seedu.address.model.medhistory.MedHistory;
import seedu.address.model.medicalreport.MedicalReport;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.addinfo.BloodType;
import seedu.address.model.person.addinfo.DateOfBirth;
import seedu.address.model.person.addinfo.Gender;
import seedu.address.model.person.addinfo.Height;
import seedu.address.model.person.addinfo.MaritalStatus;
import seedu.address.model.person.addinfo.Nric;
import seedu.address.model.person.addinfo.Occupation;
import seedu.address.model.person.addinfo.Weight;
import seedu.address.model.tag.Tag;

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
            + "[" + PREFIX_ADD_INFO_NRIC + "NRIC] "
            + "[" + PREFIX_ADD_INFO_DOB + "DATE OF BIRTH IN DD-MM-YYYY] "
            + "[" + PREFIX_ADD_INFO_HEIGHT + "HEIGHT IN CM] "
            + "[" + PREFIX_ADD_INFO_WEIGHT + "WEIGHT IN KG] "
            + "[" + PREFIX_ADD_INFO_GENDER + "GENDER] "
            + "[" + PREFIX_ADD_INFO_BLOODTYPE + "BLOOD TYPE] "
            + "[" + PREFIX_ADD_INFO_OCCUPATION + "OCCUPATION] "
            + "[" + PREFIX_ADD_INFO_MARITAL + "MARITAL STATUS]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ADD_INFO_DOB + "01-01-1970 "
            + PREFIX_ADD_INFO_HEIGHT + "182";

    public static final String MESSAGE_NOT_EDITED = "At least one field to add additional information must be provided";
    public static final String MESSAGE_ADD_INFO_SUCCESS = "Added additional info to Person: %1$s";

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
        Height updatedHeight = addInfoPersonDescriptor.getHeight().orElse(personToEdit.getHeight());
        Weight updatedWeight = addInfoPersonDescriptor.getWeight().orElse(personToEdit.getWeight());
        Gender updatedGender = addInfoPersonDescriptor.getGender().orElse(personToEdit.getGender());
        BloodType updatedBloodType = addInfoPersonDescriptor.getBloodType().orElse(personToEdit.getBloodType());
        Occupation updatedOccupation = addInfoPersonDescriptor.getOccupation().orElse(personToEdit.getOccupation());
        MaritalStatus updatedMaritalStatus = addInfoPersonDescriptor.getMaritalStatus()
                .orElse(personToEdit.getMaritalStatus());


        return new Person(name, phone, email, address, medicalReports, medHistory, appts, updatedNric,
                updatedDateOfBirth, updatedHeight, updatedWeight, updatedGender, updatedBloodType, updatedOccupation,
                updatedMaritalStatus, tags);
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
        private Height height;
        private Weight weight;
        private Gender gender;
        private BloodType bloodType;
        private Occupation occupation;
        private MaritalStatus maritalStatus;

        public AddInfoPersonDescriptor() {}

        public AddInfoPersonDescriptor(AddInfoPersonDescriptor toCopy) {
            setNric(toCopy.nric);
            setDateOfBirth(toCopy.dateOfBirth);
            setHeight(toCopy.height);
            setWeight(toCopy.weight);
            setGender(toCopy.gender);
            setBloodType(toCopy.bloodType);
            setOccupation(toCopy.occupation);
            setMaritalStatus(toCopy.maritalStatus);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(nric, dateOfBirth, height, weight, gender, bloodType, occupation,
                    maritalStatus);
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

        public void setHeight(Height height) {
            this.height = height;
        }

        public Optional<Height> getHeight() {
            return Optional.ofNullable(height);
        }

        public void setWeight(Weight weight) {
            this.weight = weight;
        }

        public Optional<Weight> getWeight() {
            return Optional.ofNullable(weight);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setBloodType(BloodType bloodType) {
            this.bloodType = bloodType;
        }

        public Optional<BloodType> getBloodType() {
            return Optional.ofNullable(bloodType);
        }

        public void setOccupation(Occupation occupation) {
            this.occupation = occupation;
        }

        public Optional<Occupation> getOccupation() {
            return Optional.ofNullable(occupation);
        }

        public void setMaritalStatus(MaritalStatus maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public Optional<MaritalStatus> getMaritalStatus() {
            return Optional.ofNullable(maritalStatus);
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
                    && getDateOfBirth().equals(e.getDateOfBirth())
                    && getHeight().equals(e.getHeight())
                    && getWeight().equals(e.getWeight())
                    && getGender().equals(e.getGender())
                    && getBloodType().equals(e.getBloodType())
                    && getOccupation().equals(e.getOccupation())
                    && getMaritalStatus().equals(e.getMaritalStatus());
        }
    }
}
