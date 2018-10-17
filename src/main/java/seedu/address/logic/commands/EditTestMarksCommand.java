package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;



import seedu.address.model.Model;
import seedu.address.model.grade.Marks;
import seedu.address.model.grade.Test;
import seedu.address.model.grade.TestName;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Nationality;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class EditTestMarksCommand extends Command {

    public static final String COMMAND_WORD = "edit_test";
    public static final String COMMAND_WORD_2 = "et";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add test to persons whose names"
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice cs2113quiz1 66";


    public static final String MESSAGE_NOT_FOUND_TEST = "Test Name is not Found please add first.";
    private final NameContainsKeywordsPredicate predicate;
    private final String testName;
    private final String testMarks;
    private final EditPersonDescriptor editPersonDescriptor = null;

    public EditTestMarksCommand(NameContainsKeywordsPredicate predicate, String testName, String testMarks) {
        this.predicate = predicate;
        this.testName = testName;
        this.testMarks = testMarks;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);


        List<Person> PersonListName = model.getFilteredPersonList();
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        Person personToEdit = PersonListName.get(0);

        Test test = new Test(new TestName(testName), new Marks(testMarks));

        Set<Test> testList = new HashSet<>();
        testList.addAll(personToEdit.getTests());

        boolean checkExists = false;
        for (Test t : personToEdit.getTests()) {
            if (t.getTestName().testName.equals(test.getTestName().testName)) {
                checkExists = true;
                testList.remove(t);
                testList.add(test);
            }
        }

        if (!checkExists) {
            throw new CommandException(MESSAGE_NOT_FOUND_TEST);
        }

        editPersonDescriptor.setTests(testList);
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);


        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();


        return new CommandResult(
                String.format(Messages.MESSAGE_UPDATED_TEST_LIST, model.getFilteredPersonList().size()));
    }

    public static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Gender updateGender = editPersonDescriptor.getGender().orElse(personToEdit.getGender());
        Nationality updateNationality = editPersonDescriptor.getNationality().orElse(personToEdit.getNationality());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Grade updatedGrade = editPersonDescriptor.getGrade().orElse(personToEdit.getGrade());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        Set<Test> updatedTests = editPersonDescriptor.getTests().orElse(personToEdit.getTests());
        return new Person(updatedName, updateGender, updateNationality, updatedPhone,
                updatedEmail, updatedAddress, updatedGrade, updatedTags, updatedTests);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditTestMarksCommand // instanceof handles nulls
                && predicate.equals(((EditTestMarksCommand) other).predicate)); // state check
    }


    public static class EditPersonDescriptor {
        private Name name;
        private Gender gender;
        private Nationality nationality;
        private Phone phone;
        private Email email;
        private Address address;
        private Grade grade;
        private Set<Tag> tags;
        private Set<Test> tests;

        public EditPersonDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setGender(toCopy.gender);
            setNationality(toCopy.nationality);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setGrade(toCopy.grade);
            setTags(toCopy.tags);
            setTests(toCopy.tests);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {

            return CollectionUtil.isAnyNonNull(name, phone, email,
                    address, tags, grade, tests);

        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setNationality(Nationality nationality) {
            this.nationality = nationality;
        }

        public Optional<Nationality> getNationality() {
            return Optional.ofNullable(nationality);
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

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setGrade(Grade grade) {
            this.grade = grade;
        }

        public Optional<Grade> getGrade() {
            return Optional.ofNullable(grade);
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

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTests(Set<Test> tests) {
            this.tests = (tests != null) ? new HashSet<>(tests) : null;
        }

        /**
         * Returns an unmodifiable Test set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Test>> getTests() {
            return (tests != null) ? Optional.of(Collections.unmodifiableSet(tests)) : Optional.empty();
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
                    && getGender().equals(e.getGender())
                    && getNationality().equals(e.getNationality())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getGrade().equals(e.getGrade())
                    && getTags().equals(e.getTags())
                    && getTests().equals(e.getTests());
        }
    }
}

