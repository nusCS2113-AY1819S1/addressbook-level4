package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEST_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEST_AMY_MARKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEST_GRADE_UNDEFINED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEST_MARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEST_MARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEST_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEST_NAME_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.group.AddGroup;
import seedu.address.model.group.Group;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class EditTestMarksCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    //TODO
//    @Test
//    public void constructor_nullTest_throwsNullPointerException() {
//        thrown.expect(NullPointerException.class);
//        new EditTestMarksCommand(null,null,null,null,null);
//    }

    @Test
    public void execute_testAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTestAdded modelStub = new ModelStubAcceptingTestAdded();
        modelStub.validPerson = new PersonBuilder().withTests(VALID_TEST_AMY).build();
        Person validPerson = new PersonBuilder().build();
        String[] nameKeywords = validPerson.getName().fullName.split("\\s+");
        List<String> nameKeywordsList =
                new ArrayList<>(Arrays.asList(nameKeywords));
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate = new NameContainsKeywordsPredicate(nameKeywordsList);

        CommandResult commandResult = new EditTestMarksCommand(nameContainsKeywordsPredicate,VALID_TEST_NAME_AMY,VALID_TEST_AMY_MARKS,VALID_TEST_GRADE_UNDEFINED,nameKeywordsList).execute(modelStub, commandHistory);

        assertEquals(Messages.MESSAGE_UPDATED_TEST_LIST, commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_notFoundTest_throwsCommandException() throws Exception {

        ModelStubAcceptingTestAdded modelStub = new ModelStubAcceptingTestAdded();
        Person validPerson = new PersonBuilder().build();
        String[] nameKeywords = validPerson.getName().fullName.split("\\s+");
        List<String> nameKeywordsList =
                new ArrayList<>(Arrays.asList(nameKeywords));
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate = new NameContainsKeywordsPredicate(nameKeywordsList);
        EditTestMarksCommand editTestMarksCommand = new EditTestMarksCommand(nameContainsKeywordsPredicate,VALID_TEST_NAME_AMY,VALID_TEST_AMY_MARKS,VALID_TEST_GRADE_UNDEFINED,nameKeywordsList);
        thrown.expect(CommandException.class);
        thrown.expectMessage(EditTestMarksCommand.MESSAGE_NOT_FOUND_TEST);
        editTestMarksCommand.execute(modelStub, commandHistory);

    }

    @Test
    public void execute_noPerson_throwsCommandException() throws Exception {

        ModelStubAcceptingTestAdded modelStub = new ModelStubAcceptingTestAdded();
        Person validPerson = new PersonBuilder().withName("Jenny").build();
        String[] nameKeywords = validPerson.getName().fullName.split("\\s+");
        List<String> nameKeywordsList =
                new ArrayList<>(Arrays.asList(nameKeywords));
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate = new NameContainsKeywordsPredicate(nameKeywordsList);
        EditTestMarksCommand editTestMarksCommand = new EditTestMarksCommand(nameContainsKeywordsPredicate,VALID_TEST_NAME_AMY,VALID_TEST_AMY_MARKS,VALID_TEST_GRADE_UNDEFINED,nameKeywordsList);
        thrown.expect(CommandException.class);
        thrown.expectMessage(AddTestMarksCommand.MESSAGE_PERSONNAME_NOT_FOUND);
        editTestMarksCommand.execute(modelStub, commandHistory);

    }

//    @Test
//    public void execute_duplicatePerson_throwsCommandException() throws Exception {
//
//        ModelStubAcceptingTestAdded modelStub = new ModelStubAcceptingTestAdded();
//
//        Person validPerson = new PersonBuilder().withName("Jeff Alice").build();
//        String[] nameKeywords = validPerson.getName().fullName.split("\\s+");
//        List<String> nameKeywordsList =
//                new ArrayList<>(Arrays.asList(nameKeywords));
//        NameContainsKeywordsPredicate nameContainsKeywordsPredicate = new NameContainsKeywordsPredicate(nameKeywordsList);
//        EditTestMarksCommand editTestMarksCommand = new EditTestMarksCommand(nameContainsKeywordsPredicate,VALID_TEST_NAME_AMY,VALID_TEST_AMY_MARKS,VALID_TEST_GRADE_UNDEFINED,nameKeywordsList);
//        thrown.expect(CommandException.class);
//        thrown.expectMessage(AddTestMarksCommand.MESSAGE_PERSON_DUPLICATE_FOUND);
//        editTestMarksCommand.execute(modelStub, commandHistory);
//
//    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();

        String[] nameKeywordsAlice = alice.getName().fullName.split("\\s+");
        List<String> nameKeywordsListAlice =
                new ArrayList<>(Arrays.asList(nameKeywordsAlice));
        NameContainsKeywordsPredicate nameContainsKeywordsPredicateAlice = new NameContainsKeywordsPredicate(nameKeywordsListAlice);

        String[] nameKeywordsBob = bob.getName().fullName.split("\\s+");
        List<String> nameKeywordsListBob =
                new ArrayList<>(Arrays.asList(nameKeywordsBob));
        NameContainsKeywordsPredicate nameContainsKeywordsPredicateBob = new NameContainsKeywordsPredicate(nameKeywordsListBob);

        EditTestMarksCommand editTestMarksCommandAlice = new EditTestMarksCommand(nameContainsKeywordsPredicateAlice,VALID_TEST_NAME_AMY,VALID_TEST_MARK_AMY,VALID_TEST_GRADE_UNDEFINED,nameKeywordsListAlice);
        EditTestMarksCommand editTestMarksCommandBob = new EditTestMarksCommand(nameContainsKeywordsPredicateBob,VALID_TEST_NAME_BOB,VALID_TEST_MARK_BOB,VALID_TEST_GRADE_UNDEFINED,nameKeywordsListBob);

        // same object -> returns true
        assertTrue(editTestMarksCommandAlice.equals(editTestMarksCommandAlice));

        // same values -> returns true
        EditTestMarksCommand editTestMarksCommandAliceCopy = new EditTestMarksCommand(nameContainsKeywordsPredicateAlice,VALID_TEST_NAME_AMY,VALID_TEST_MARK_AMY,VALID_TEST_GRADE_UNDEFINED,nameKeywordsListAlice);
        assertTrue(editTestMarksCommandAlice.equals(editTestMarksCommandAliceCopy));

        // different types -> returns false
        assertFalse(editTestMarksCommandAlice.equals(1));

        // null -> returns false
        assertFalse(editTestMarksCommandAlice.equals(null));

        // different person -> returns false
        assertFalse(editTestMarksCommandAlice.equals(editTestMarksCommandBob));
    }


    /**
     * A Model stub that always accept the Test being added.
     */
    private class ModelStubAcceptingTestAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();
        Person validPerson = new PersonBuilder().build();
        Person validPerson2 = new PersonBuilder().withName("Jeff").build();
        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {

        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            requireNonNull(target);
            requireNonNull(editedPerson);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            personsAdded.add(validPerson);
            personsAdded.add(validPerson2);
            return FXCollections.observableArrayList(personsAdded);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredGroupList(Predicate<Group> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void createGroup(Group createGroup) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasGroup(Group checkGroup) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addGroup(AddGroup addGroup) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPersonInGroup(AddGroup addGroup) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteGroup(Group target) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
