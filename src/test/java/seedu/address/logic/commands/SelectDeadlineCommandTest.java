package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalDeadlines.INVALID_32ND_JAN_2018;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_APR_2018;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_JAN_2018;

import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Milestone;
import seedu.address.model.task.Task;

public class SelectDeadlineCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullDeadline_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SelectDeadlineCommand(null);
    }

    @Test
    public void execute_deadlineAcceptedByModel_selectSuccessful() throws Exception {
        ModelStubAcceptingDeadlineSelected modelStub = new ModelStubAcceptingDeadlineSelected();
        Deadline validDeadline = VALID_1ST_JAN_2018;

        CommandResult commandResult = new SelectDeadlineCommand(validDeadline).execute(modelStub, commandHistory);

        assertEquals(String.format(SelectDeadlineCommand.MESSAGE_SUCCESS, validDeadline), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_invalidDeadline_throwCommandException() throws Exception {
        Deadline invalidDeadline = INVALID_32ND_JAN_2018;
        SelectDeadlineCommand selectCommand = new SelectDeadlineCommand(invalidDeadline);
        ModelStub modelStub = new ModelStubWithDeadline(invalidDeadline);

        thrown.expect(CommandException.class);
        thrown.expectMessage(SelectDeadlineCommand.MESSAGE_INVALID_DEADLINE);
        selectCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Deadline jan1st2018 = VALID_1ST_JAN_2018;
        Deadline apr1st2018 = VALID_1ST_APR_2018;
        SelectDeadlineCommand selectJan1st2018Command = new SelectDeadlineCommand(jan1st2018);
        SelectDeadlineCommand selectApr1st2018Command = new SelectDeadlineCommand(apr1st2018);
        SelectDeadlineCommand selectJan1st2018CommandCopy = new SelectDeadlineCommand(jan1st2018);

        // Same object -> returns true
        assertTrue(selectJan1st2018Command.equals(selectJan1st2018Command));

        // Same deadline -> returns true
        assertTrue(selectJan1st2018Command.equals(selectJan1st2018CommandCopy));

        // Null -> returns false
        assertFalse(selectJan1st2018Command.equals(null));

        // Different types -> returns false
        assertFalse(selectJan1st2018Command.equals(1));

        // Different deadline -> returns false
        assertFalse(selectJan1st2018Command.equals(selectApr1st2018Command));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void selectDeadline(Deadline deadline) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Deadline getDeadline() {
            return new Deadline(VALID_1ST_JAN_2018.toString());
        }

        @Override
        public boolean validDeadline(Deadline deadline) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyTaskBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTaskBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deferTaskDeadline(Task task, Deadline deadline) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTask(Task target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void completeTask(Task target, int hours) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateTask(Task target, Task editedTask) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMilestone(Milestone milestone) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoTaskBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoTaskBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoTaskBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoTaskBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitTaskBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void trackProductivity() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A default model stub that contains a single task.
     */

    private class ModelStubWithDeadline extends ModelStub {
        private final Deadline deadline;

        ModelStubWithDeadline(Deadline deadline) {
            requireNonNull(deadline);
            this.deadline = deadline;
        }

        @Override
        public boolean validDeadline(Deadline deadline) {
            requireNonNull(deadline);
            return Deadline.isValidDeadline(this.deadline.toString());
        }
    }

    /**
     * A model stub that always accepts the deadline being selected.
     */
    private class ModelStubAcceptingDeadlineSelected extends ModelStub {

        @Override
        public boolean validDeadline(Deadline deadlineSelected) {
            requireNonNull(deadlineSelected);
            return Deadline.isValidDeadline(deadlineSelected.toString());
        }

        @Override
        public void selectDeadline(Deadline deadline) {
            requireNonNull(deadline);
        }

        @Override
        public void commitTaskBook() {
            // called by {@code SelectDeadlineCommand#execute()}
        }

        @Override
        public ReadOnlyTaskBook getAddressBook() {
            return new AddressBook();
        }
    }
}
