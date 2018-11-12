package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.LeaveList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyLeaveList;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.Person;
import seedu.address.model.prioritylevel.PriorityLevelEnum;
import seedu.address.session.SessionManager;
import seedu.address.testutil.LeaveBuilder;
import systemtests.SessionHelper;

//@@author Hafizuddin-NUS
public class AddLeaveCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static Model dummyModel;
    private static final Leave REQUEST1EDIT = new LeaveBuilder().withNric("S1234567A")
            .withDate("01/02/2020").withApproval("PENDING").withPriorityLevel(0).build();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        SessionHelper.forceLoginWithPriorityLevelOf(PriorityLevelEnum.ADMINISTRATOR.getPriorityLevelCode());
        dummyModel = new ModelManager();
        SessionManager.getInstance(dummyModel);
    }

    @Test
    public void constructor_nullLeave_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddLeaveCommand(null);
    }

    @Test
    public void execute_leaveAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingLeaveAdded modelStub = new ModelStubAcceptingLeaveAdded();
        Leave validLeave = new LeaveBuilder().build();
        AddLeaveCommand addLeaveCommand = new AddLeaveCommand(validLeave);
        SessionHelper.forceLoginWithPriorityLevelOf(validLeave.getEmployeeId().toString(), 1);
        CommandResult commandResult = addLeaveCommand.execute(modelStub, commandHistory);
        assertEquals(String.format(AddLeaveCommand.MESSAGE_SUCCESS, validLeave), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validLeave), modelStub.leavesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateLeave_throwsCommandException() throws Exception {
        Leave validLeave = new LeaveBuilder().build();
        SessionHelper.forceLoginWithPriorityLevelOf(validLeave.getEmployeeId().toString(), 1);
        ModelStub modelStub = new ModelStubWithLeave(validLeave);
        AddLeaveCommand addLeaveCommand = new AddLeaveCommand(validLeave);
        addLeaveCommand.execute(dummyModel, commandHistory);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddLeaveCommand.MESSAGE_DUPLICATE_LEAVE);
        addLeaveCommand.execute(modelStub, commandHistory);
    }
    @Test
    public void equals() {
        Leave leave1 = new LeaveBuilder().withNric("S9514222A").withDate("12/03/2020").withPriorityLevel(3).build();
        Leave leave2 = new LeaveBuilder().withNric("S9513222E").withDate("12/04/2020").withPriorityLevel(3).build();
        AddLeaveCommand addLeaveCommand1 = new AddLeaveCommand(leave1);
        AddLeaveCommand addLeaveCommand2 = new AddLeaveCommand(leave2);

        // same object -> returns true
        assertTrue(addLeaveCommand1.equals(addLeaveCommand1));

        // different types -> returns false
        assertFalse(addLeaveCommand1.equals(1));

        // null -> returns false
        assertFalse(addLeaveCommand1.equals(null));

        // different leave -> returns false
        assertFalse(addLeaveCommand1.equals(addLeaveCommand2));
    }

    @Test
    public void execute_notLoggedIn_throwsCommandException() {
        Assertions.assertThrows(CommandException.class, () -> {
            SessionHelper.logoutOfSession();
            Leave leave = REQUEST1EDIT;
            AddLeaveCommand sd = new AddLeaveCommand(leave);
            sd.execute(dummyModel, commandHistory);
        }, SessionManager.NOT_LOGGED_IN);
    }

    @After
    public void tearDown() {
        SessionHelper.logoutOfSession();
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
        public void addLeave(Leave leave) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData2(ReadOnlyLeaveList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyLeaveList getLeaveList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasLeave(Leave leave) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteLeave(Leave target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortEmployee(String field, String order) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateLeave(Leave target, Leave editedLeave) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Leave> getFilteredLeaveList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredLeaveList(Predicate<Leave> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitLeaveList() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single leave.
     */
    private class ModelStubWithLeave extends ModelStub {
        private final Leave leave;

        ModelStubWithLeave(Leave leave) {
            requireNonNull(leave);
            this.leave = leave;
        }

        @Override
        public boolean hasLeave(Leave leave) {
            requireNonNull(leave);
            return this.leave.isSameRequest(leave);
        }
    }

    /**
     * A Model stub that always accept the leave being added.
     */
    private class ModelStubAcceptingLeaveAdded extends ModelStub {
        final ArrayList<Leave> leavesAdded = new ArrayList<>();

        @Override
        public boolean hasLeave(Leave leave) {
            requireNonNull(leave);
            return leavesAdded.stream().anyMatch(leave::isSameRequest);
        }

        @Override
        public void addLeave(Leave leave) {
            requireNonNull(leave);
            leavesAdded.add(leave);
        }

        @Override
        public void commitLeaveList() {
            // called by {@code AddLeaveCommand#execute()}
        }

        @Override
        public ReadOnlyLeaveList getLeaveList() {
            return new LeaveList();
        }
    }



}
