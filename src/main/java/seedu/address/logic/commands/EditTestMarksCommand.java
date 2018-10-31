package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddTestMarksCommand.MESSAGE_PERSONNAME_NOT_FOUND;
import static seedu.address.logic.commands.AddTestMarksCommand.MESSAGE_PERSON_DUPLICATE_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEST_MARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEST_NAME;
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
import seedu.address.model.grade.Grade;
import seedu.address.model.grade.Marks;
import seedu.address.model.grade.Test;
import seedu.address.model.grade.TestName;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Nationality;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
/**
 * A command to edit person test marks
 */
public class EditTestMarksCommand extends Command {
    /**
     * A command to edit person test marks
     */
    public static final String COMMAND_WORD = "edit_test";
    public static final String COMMAND_WORD_2 = "edt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": edit test to persons whose names"
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD_2 + " alice " + PREFIX_TEST_NAME + "cs2113quiz1 " + PREFIX_TEST_MARK + "67";


    public static final String MESSAGE_NOT_FOUND_TEST = "Test Name is not Found please add first.";
    private final NameContainsKeywordsPredicate predicate;
    private final String testName;
    private final String testMarks;
    private final String testGrade;
    private final List<String> nameList;

    public EditTestMarksCommand(NameContainsKeywordsPredicate predicate, String testName,
                                String testMarks, String testGrade, List<String> nameList) {
        this.predicate = predicate;
        this.testName = testName;
        this.testMarks = testMarks;
        this.testGrade = testGrade;
        this.nameList = nameList;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);


        List<Person> personListName = model.getFilteredPersonList();
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        String grade = "Undefined";
        if (testGrade != null) {
            grade = testGrade;
        }
        if (personListName.isEmpty()) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(MESSAGE_PERSONNAME_NOT_FOUND);
        } else {
            if (personListName.size() > 1) {
                String fullName = "";
                for (String name :nameList) {
                    fullName += name + " ";
                }
                fullName = fullName.substring(0, fullName.length() - 1).toUpperCase();
                boolean checked = false;
                boolean duplicate = false;
                for (Person person: personListName) {
                    if (fullName.equals(person.getName().fullName.toUpperCase())) {
                        return editPersonMarks(person, model);
                    } else if (!fullName.equals(person.getName().fullName.toUpperCase())
                            && person.getName().fullName.toUpperCase().contains(fullName)) {
                        duplicate = true;
                    } else {
                        checked = true;
                    }
                }
                if (!checked && duplicate) {
                    model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                    throw new CommandException(MESSAGE_PERSON_DUPLICATE_FOUND);
                } else if (checked && !duplicate) {
                    model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                    throw new CommandException(MESSAGE_PERSONNAME_NOT_FOUND);
                }
            } else {
                return editPersonMarks(personListName.get(0), model);
            }
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_NOT_FOUND, model.getFilteredPersonList().size()));
    }
    /**
     * createEditedPerson
     */
    private CommandResult editPersonMarks(Person person, Model model) throws CommandException {
        Person personToEdit = person;
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        Test test = null;
        Grade assignGrade = new Grade("Undefined");
        if (this.testGrade != null) {
            assignGrade = new Grade(this.testGrade);
        }
        try {
            test = new Test(new TestName(testName), new Marks(testMarks), assignGrade);
        } catch (IllegalArgumentException e) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(e.getMessage());
        }
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
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
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
    /**
     * createEditedPerson
     */
    public static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Gender updateGender = editPersonDescriptor.getGender().orElse(personToEdit.getGender());
        Nationality updateNationality = editPersonDescriptor.getNationality().orElse(personToEdit.getNationality());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        Set<Test> updatedTests = editPersonDescriptor.getTests().orElse(personToEdit.getTests());
        return new Person(updatedName, updateGender, updateNationality, updatedPhone,
                updatedEmail, updatedAddress, updatedTags, updatedTests);
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditTestMarksCommand // instanceof handles nulls
                && predicate.equals(((EditTestMarksCommand) other).predicate)); // state check
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Gender gender;
        private Nationality nationality;
        private Phone phone;
        private Email email;
        private Address address;
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
            setTags(toCopy.tags);
            setTests(toCopy.tests);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {

            return CollectionUtil.isAnyNonNull(name, phone, email,
                    address, tags, tests);

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
                    && getTags().equals(e.getTags())
                    && getTests().equals(e.getTests());
        }
    }
}

