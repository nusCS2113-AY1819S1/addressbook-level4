package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEST_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEST_NAME_AMY;
import static seedu.address.logic.commands.DeleteTestMarksCommand.MESSAGE_SUCCESSFUL_DELETE_TEST;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.group.AddGroup;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DeleteTestMarksCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullTest_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeleteTestMarksCommand(null);
    }

    @Test
    public void execute_testAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTestDeleted modelStub = new ModelStubAcceptingTestDeleted();
        modelStub.validPerson = new PersonBuilder().withTests(VALID_TEST_AMY).build();

        CommandResult commandResult = new
                DeleteTestMarksCommand(VALID_TEST_NAME_AMY).execute(modelStub, commandHistory);

        assertEquals(String.format(MESSAGE_SUCCESSFUL_DELETE_TEST, VALID_TEST_NAME_AMY), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_notFoundTest_throwsCommandException() throws Exception {

        ModelStubAcceptingTestDeleted modelStub = new ModelStubAcceptingTestDeleted();
        DeleteTestMarksCommand deleteTestMarksCommand = new DeleteTestMarksCommand("TEST24");
        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(DeleteTestMarksCommand.MESSAGE_NOT_FOUND_TEST, "TEST24"));
        deleteTestMarksCommand.execute(modelStub, commandHistory);

    }

    @Test
    public void equals() {

        DeleteTestMarksCommand deleteTestMarksCommandTest1 = new DeleteTestMarksCommand("Test1");
        DeleteTestMarksCommand deleteTestMarksCommandTest2 = new DeleteTestMarksCommand("Test2");

        // same object -> returns true
        assertTrue(deleteTestMarksCommandTest1.equals(deleteTestMarksCommandTest1));

        // same values -> returns true
        DeleteTestMarksCommand deleteTestMarksCommandTest1Copy = new DeleteTestMarksCommand("Test1");
        assertTrue(deleteTestMarksCommandTest1.equals(deleteTestMarksCommandTest1Copy));

        // different types -> returns false
        assertFalse(deleteTestMarksCommandTest1.equals(1));

        // null -> returns false
        assertFalse(deleteTestMarksCommandTest1.equals(null));

        // different person -> returns false
        assertFalse(deleteTestMarksCommandTest1.equals(deleteTestMarksCommandTest2));
    }


    /**
     * A Model stub that always accept the Test being added.
     */
    private class ModelStubAcceptingTestDeleted extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();
        private Person validPerson = new PersonBuilder().build();
        private Person validPerson2 = new PersonBuilder().withName("Jeff").build();
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

        @Override
        public void deleteGroupPerson(Group group, Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getScriptFolderLocation() {
            throw new AssertionError("This method should not be called.");
        }
    }
}
