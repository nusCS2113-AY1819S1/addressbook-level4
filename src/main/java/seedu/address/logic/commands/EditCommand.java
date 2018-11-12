package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Bonus;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.DateOfBirthContainsKeywordsPredicate;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.EmployeeIdContainsKeywordsPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.Position;
import seedu.address.model.person.Salary;
import seedu.address.model.person.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_DEPARTMENT + "DEPARTMENT] "
            + "[" + PREFIX_POSITION + "POSITION] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com "
            + PREFIX_DEPARTMENT + "Finance "
            + PREFIX_POSITION + "Manager "
            + PREFIX_ADDRESS + "21 Lower Kent Ridge Rd, Singapore 119077 "
            + PREFIX_TAG + "Fishing";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com "
            + PREFIX_DEPARTMENT + "Finance "
            + PREFIX_POSITION + "Manager "
            + PREFIX_ADDRESS + "21 Lower Kent Ridge Rd, Singapore 119077 "
            + PREFIX_TAG + "Fishing";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_DUPLICATE_EMAIL = "This email already exists in the address book";
    public static final String MESSAGE_DUPLICATE_PHONE = "This phone already exists in the address book";

    private static boolean isEmailDuplicated = false;
    private static boolean isPhoneDuplicated = false;
    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * Creates an EditCommand to edit the specified employee at the specified {@code index}
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    public static void setIsEmailDuplicated(boolean verifyEmailDuplication) {
        isEmailDuplicated = verifyEmailDuplication;
    }

    public static void setIsPhoneDuplicated(boolean verifyPhoneDuplication) {
        isPhoneDuplicated = verifyPhoneDuplication;
    }

    /**
     * Execution of the command will depend on whether there are duplicated Email, Phone or Name &
     * DateOfBirth. If any of the duplicated check is true, an exception will be thrown, otherwise,
     * the command will be executed accordingly.
     * @param model The actual model
     * @param history The actual history
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
        EmployeeIdContainsKeywordsPredicate predicate =
                employeeIdPredicateCreation(model, personToEdit);

        // Checks for duplicated email
        if (model.hasPerson(editedPerson, predicate) && isEmailDuplicated && !isPhoneDuplicated) {
            EmailContainsKeywordsPredicate emailPredicate =
                    new EmailContainsKeywordsPredicate(editedPerson.getEmail().value);
            model.updateFilteredPersonList(emailPredicate);
            throw new CommandException(MESSAGE_DUPLICATE_EMAIL);
        // Checks for duplicated phone
        } else if (model.hasPerson(editedPerson, predicate) && !isEmailDuplicated && isPhoneDuplicated) {
            PhoneContainsKeywordsPredicate phonePredicate =
                    new PhoneContainsKeywordsPredicate(editedPerson.getPhone().value);
            model.updateFilteredPersonList(phonePredicate);
            throw new CommandException(MESSAGE_DUPLICATE_PHONE);
        // Checks for duplicated name & date of birth
        } else if (model.hasPerson(editedPerson, predicate) && !isEmailDuplicated && !isPhoneDuplicated) {
            NameContainsKeywordsPredicate namePredicate =
                    new NameContainsKeywordsPredicate(Collections.singletonList(editedPerson.getName().fullName));
            DateOfBirthContainsKeywordsPredicate datePredicate =
                    new DateOfBirthContainsKeywordsPredicate(editedPerson.getDateOfBirth().value);
            model.updateFilteredPersonList(namePredicate.and(datePredicate));
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code EmployeeIdContainsKeywordsPredicate} that contains all the employee IDs
     * except for the employee ID of the {@code person} that is being passed as the param.
     */
    private static EmployeeIdContainsKeywordsPredicate employeeIdPredicateCreation(Model model, Person person) {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        List<Person> getFullList = model.getFilteredPersonList();
        List<String> allEmployeeIds = new ArrayList<>();

        for (Person employeeId : getFullList) {
            if (person.getEmployeeId().value != employeeId.getEmployeeId().value) {
                allEmployeeIds.add(employeeId.getEmployeeId().value);
            }
        }

        return new EmployeeIdContainsKeywordsPredicate(allEmployeeIds);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        EmployeeId updatedEmployeeId = personToEdit.getEmployeeId();
        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        DateOfBirth updatedDateOfBirth = personToEdit.getDateOfBirth();
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Department updatedDepartment = editPersonDescriptor.getDepartment().orElse(personToEdit.getDepartment());
        Position updatedPosition = editPersonDescriptor.getPosition().orElse(personToEdit.getPosition());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Salary updatedSalary = personToEdit.getSalary();
        Bonus updatedBonus = personToEdit.getBonus();
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedEmployeeId, updatedName, updatedDateOfBirth, updatedPhone, updatedEmail,
                updatedDepartment, updatedPosition, updatedAddress, updatedSalary, updatedBonus, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Department department;
        private Position position;
        private Address address;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setDepartment(toCopy.department);
            setPosition(toCopy.position);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, department, position, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setDepartment(Department department) {
            this.department = department;
        }

        public Optional<Department> getDepartment() {
            return Optional.ofNullable(department);
        }

        public void setPosition(Position position) {
            this.position = position;
        }

        public Optional<Position> getPosition() {
            return Optional.ofNullable(position);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getDepartment().equals(e.getDepartment())
                    && getPosition().equals(e.getPosition())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
