package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.grade.Test;
import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEST_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * A command to delete certain test from student.
 */
public class DeleteTestMarksCommand extends Command {
    /**
     * A command to delete certain test from student.
     */
    public static final String COMMAND_WORD = "delete_test";
    public static final String COMMAND_WORD_2 = "dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": delete certain test from all student taking the test"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD_2 + " " + PREFIX_TEST_NAME + "cs2113quiz1 ";


    public static final String MESSAGE_NOT_FOUND_TEST = "%1$s is not Found please add first.";
    public static final String MESSAGE_SUCCESSFUL_DELETE_TEST = "%1$s have been successfully delete from all students";
    public static final String MESSAGE_NO_STUDENT = "No student in list to delete tests from";
    private final String testName;

    public DeleteTestMarksCommand(String testName) {
        requireNonNull(testName);
        this.testName = testName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        List<Person> personListName = model.getFilteredPersonList();

        if (personListName.isEmpty()) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(MESSAGE_NO_STUDENT);
        } else {
            boolean anyUpdate = false;
            for (Person person: personListName) {
                boolean updateNeeded = false;
                for (Test test: person.getTests()) {
                    if (test.getTestName().testName.toUpperCase().equals(testName.toUpperCase())){
                        updateNeeded = true;
                    }
                }
                if (updateNeeded)
                    anyUpdate = editPersonTests(person,model);
            }

            if (anyUpdate)
                return new CommandResult(
                        String.format(MESSAGE_SUCCESSFUL_DELETE_TEST, testName));
        }

        throw new CommandException(
                String.format(MESSAGE_NOT_FOUND_TEST, testName));
    }
    /**
     * editPersonTests
     */
    private boolean editPersonTests(Person person, Model model) throws CommandException {
        Person personToEdit = person;
       EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();

        Set<Test> testList = new HashSet<>();
        testList.addAll(personToEdit.getTests());

        for (Test t : personToEdit.getTests()) {
            if (t.getTestName().testName.toUpperCase().equals(testName.toUpperCase())) {
                testList.remove(t);
            }
        }

        editPersonDescriptor.setTests(testList);
        Person editedPerson = EditCommand.createEditedPerson(personToEdit, editPersonDescriptor);
        model.updatePerson(personToEdit, editedPerson);
        model.commitAddressBook();
        return true;
    }

//    /**
//     * createEditedPerson
//     */
//    public static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
//        assert personToEdit != null;
//
//        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
//        Gender updateGender = editPersonDescriptor.getGender().orElse(personToEdit.getGender());
//        Nationality updateNationality = editPersonDescriptor.getNationality().orElse(personToEdit.getNationality());
//        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
//        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
//        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
//        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
//        Set<Test> updatedTests = editPersonDescriptor.getTests().orElse(personToEdit.getTests());
//        return new Person(updatedName, updateGender, updateNationality, updatedPhone,
//                updatedEmail, updatedAddress, updatedTags, updatedTests);
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        return other == this // short circuit if same object
//                || (other instanceof DeleteTestMarksCommand // instanceof handles nulls
//                && testName.equals(((DeleteTestMarksCommand) other).testName)); // state check
//    }
//
//    /**
//     * Stores the details to edit the person with. Each non-empty field value will replace the
//     * corresponding field value of the person.
//     */
//    public static class EditPersonDescriptor {
//        private Name name;
//        private Gender gender;
//        private Nationality nationality;
//        private Phone phone;
//        private Email email;
//        private Address address;
//        private Set<Tag> tags;
//        private Set<Test> tests;
//
//        public EditPersonDescriptor() {
//        }
//
//        /**
//         * Copy constructor.
//         * A defensive copy of {@code tags} is used internally.
//         */
//        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
//            setName(toCopy.name);
//            setGender(toCopy.gender);
//            setNationality(toCopy.nationality);
//            setPhone(toCopy.phone);
//            setEmail(toCopy.email);
//            setAddress(toCopy.address);
//            setTags(toCopy.tags);
//            setTests(toCopy.tests);
//        }
//
//        /**
//         * Returns true if at least one field is edited.
//         */
//        public boolean isAnyFieldEdited() {
//
//            return CollectionUtil.isAnyNonNull(name, phone, email,
//                    address, tags, tests);
//
//        }
//
//        public void setName(Name name) {
//            this.name = name;
//        }
//
//        public Optional<Name> getName() {
//            return Optional.ofNullable(name);
//        }
//
//        public void setGender(Gender gender) {
//            this.gender = gender;
//        }
//
//        public Optional<Gender> getGender() {
//            return Optional.ofNullable(gender);
//        }
//
//        public void setNationality(Nationality nationality) {
//            this.nationality = nationality;
//        }
//
//        public Optional<Nationality> getNationality() {
//            return Optional.ofNullable(nationality);
//        }
//
//        public void setPhone(Phone phone) {
//            this.phone = phone;
//        }
//
//        public Optional<Phone> getPhone() {
//            return Optional.ofNullable(phone);
//        }
//
//        public void setEmail(Email email) {
//            this.email = email;
//        }
//
//        public Optional<Email> getEmail() {
//            return Optional.ofNullable(email);
//        }
//
//        public void setAddress(Address address) {
//            this.address = address;
//        }
//
//        public Optional<Address> getAddress() {
//            return Optional.ofNullable(address);
//        }
//
//
//        /**
//         * Sets {@code tags} to this object's {@code tags}.
//         * A defensive copy of {@code tags} is used internally.
//         */
//        public void setTags(Set<Tag> tags) {
//            this.tags = (tags != null) ? new HashSet<>(tags) : null;
//        }
//
//        /**
//         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
//         * if modification is attempted.
//         * Returns {@code Optional#empty()} if {@code tags} is null.
//         */
//        public Optional<Set<Tag>> getTags() {
//            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
//        }
//
//        /**
//         * Sets {@code tags} to this object's {@code tags}.
//         * A defensive copy of {@code tags} is used internally.
//         */
//        public void setTests(Set<Test> tests) {
//            this.tests = (tests != null) ? new HashSet<>(tests) : null;
//        }
//
//        /**
//         * Returns an unmodifiable Test set, which throws {@code UnsupportedOperationException}
//         * if modification is attempted.
//         * Returns {@code Optional#empty()} if {@code tags} is null.
//         */
//        public Optional<Set<Test>> getTests() {
//            return (tests != null) ? Optional.of(Collections.unmodifiableSet(tests)) : Optional.empty();
//        }
//
//        @Override
//        public boolean equals(Object other) {
//            // short circuit if same object
//            if (other == this) {
//                return true;
//            }
//
//            // instanceof handles nulls
//            if (!(other instanceof EditPersonDescriptor)) {
//                return false;
//            }
//
//            // state check
//            EditPersonDescriptor e = (EditPersonDescriptor) other;
//
//            return getName().equals(e.getName())
//                    && getPhone().equals(e.getPhone())
//                    && getGender().equals(e.getGender())
//                    && getNationality().equals(e.getNationality())
//                    && getEmail().equals(e.getEmail())
//                    && getAddress().equals(e.getAddress())
//                    && getTags().equals(e.getTags())
//                    && getTests().equals(e.getTests());
//        }
    }