package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.testutil.GroupBuilder;

public class CreateGroupCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CreateGroupCommand(null);
    }

    @Test
    public void execute_groupAcceptedByModel_addSuccessful() throws Exception {
        CreateGroupCommandTest.ModelStubAcceptingGroupCreated modelStub =
                new CreateGroupCommandTest.ModelStubAcceptingGroupCreated();
        Group validGroup = new GroupBuilder().build();

        CommandResult commandResult = new CreateGroupCommand(validGroup).execute(modelStub, commandHistory);

        assertEquals(String.format(CreateGroupCommand.MESSAGE_SUCCESS, validGroup), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validGroup), modelStub.groupsCreated);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateGroup_throwsCommandException() throws Exception {
        Group validGroup = new GroupBuilder().build();
        CreateGroupCommand createGroupCommand = new CreateGroupCommand(validGroup);
        CreateGroupCommandTest.ModelStub modelStub = new CreateGroupCommandTest.ModelStubWithGroup(validGroup);

        thrown.expect(CommandException.class);
        thrown.expectMessage(CreateGroupCommand.MESSAGE_DUPLICATE_GROUP);
        createGroupCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Group tut1 = new GroupBuilder().withGroupName("TUT[1]").build();
        Group tut2 = new GroupBuilder().withGroupName("TUT[2]").build();
        CreateGroupCommand createTut1Command = new CreateGroupCommand(tut1);
        CreateGroupCommand createTut2Command = new CreateGroupCommand(tut2);

        // same object -> returns true
        assertTrue(createTut1Command.equals(createTut1Command));

        // same values -> returns true
        CreateGroupCommand createTut1CommandCopy = new CreateGroupCommand(tut1);
        assertTrue(createTut1Command.equals(createTut1CommandCopy));

        // different types -> returns false
        assertFalse(createTut1Command.equals(1));

        // null -> returns false
        assertFalse(createTut1Command.equals(null));

        // different person -> returns false
        assertFalse(createTut1Command.equals(createTut2Command));
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
    }

    /**
     * A Model stub that contains a single group.
     */
    private class ModelStubWithGroup extends CreateGroupCommandTest.ModelStub {
        private final Group group;

        ModelStubWithGroup(Group group) {
            requireNonNull(group);
            this.group = group;
        }

        @Override
        public boolean hasGroup(Group group) {
            requireNonNull(group);
            return this.group.isSameGroup(group);
        }
    }

    /**
     * A Model stub that always accept the group being added.
     */
    private class ModelStubAcceptingGroupCreated extends CreateGroupCommandTest.ModelStub {
        final ArrayList<Group> groupsCreated = new ArrayList<>();

        @Override
        public boolean hasGroup(Group group) {
            requireNonNull(group);
            return groupsCreated.stream().anyMatch(group::isSameGroup);
        }

        @Override
        public void createGroup(Group group) {
            requireNonNull(group);
            groupsCreated.add(group);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code CreateGroupCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
