package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.testutil.TypicalAddGroups.getAddGroup1;
import static seedu.address.testutil.TypicalAddGroups.getAddGroup2;
import static seedu.address.testutil.TypicalAddGroups.getAddGroup3;
import static seedu.address.testutil.TypicalGroups.getTypicalGroupsWithPersons;
import static seedu.address.testutil.TypicalPersons.ALICE;

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

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddGroupCommand.
 */
public class AddGroupCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullAddGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddGroupCommand(null);
    }

    @Test
        public void execute_nullModel_throwsException() throws Exception {
        AddGroupCommand addGroupCommand = new AddGroupCommand(getAddGroup1());
        thrown.expect(NullPointerException.class);
        addGroupCommand.execute(null, commandHistory);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() throws Exception {
        AddGroupCommand addGroupCommand = new AddGroupCommand(getAddGroup1());
        AddGroupCommandTest.ModelStub modelStub = new AddGroupCommandTest
                .ModelStubWithPersonInGroup(getTypicalGroupsWithPersons());

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        addGroupCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_invalidGroupIndex_throwsCommandException() throws Exception {
        AddGroupCommand addGroupCommand = new AddGroupCommand(getAddGroup2());
        AddGroupCommandTest.ModelStub modelStub = new AddGroupCommandTest
                .ModelStubWithPersonInGroup(getTypicalGroupsWithPersons());

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        addGroupCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_hasPersonInGroup_throwsCommandException() throws Exception {
        AddGroupCommand addGroupCommand = new AddGroupCommand(getAddGroup3());
        AddGroupCommandTest.ModelStub modelStub = new AddGroupCommandTest
                .ModelStubWithPersonInGroup(getTypicalGroupsWithPersons());

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddGroupCommand.MESSAGE_DUPLICATE_PERSONS);
        addGroupCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        AddGroupCommand addGroupCommand1 = new AddGroupCommand(getAddGroup1());
        AddGroupCommand addGroupCommand2 = new AddGroupCommand(getAddGroup2());

        // same object -> returns true
        assertTrue(addGroupCommand1.equals(addGroupCommand1));

        // same values -> returns true
        AddGroupCommand addGroup1CommandCopy = new AddGroupCommand(getAddGroup1());
        assertTrue(addGroupCommand1.equals(addGroup1CommandCopy));

        // different types -> returns false
        assertFalse(addGroupCommand1.equals(1));

        // null -> returns false
        assertFalse(addGroupCommand1.equals(null));

        // different param -> returns false
        assertFalse(addGroupCommand1.equals(addGroupCommand2));
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
    }

    /**
     * A Model stub that contains a single group with one person in it.
     */
    private class ModelStubWithPersonInGroup extends AddGroupCommandTest.ModelStub {
        private final Group group;

        ModelStubWithPersonInGroup(Group group) {
            requireNonNull(group);
            this.group = group;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            ObservableList<Person> person = FXCollections.observableArrayList();
            person.add(ALICE);
            return person;
        }

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            ObservableList<Group> group = FXCollections.observableArrayList();
            group.add(getTypicalGroupsWithPersons());
            return group;
        }

        @Override
        public boolean hasPersonInGroup(AddGroup toAdd) {
            requireNonNull(toAdd);
            for (Person p : group.getPersons()) {
                for (Person p2 : toAdd.getPersonSet()) {
                    if (p.equals(p2)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

}
