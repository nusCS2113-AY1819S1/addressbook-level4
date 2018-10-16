//@@author emobeany
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
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
import seedu.address.model.task.Task;
import seedu.address.testutil.DeadlineBuilder;

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
        ModelStubAcceptingDeadlineSelected modelStub = new SelectDeadlineCommandTest().ModelStubAcceptingDeadlineSelected();
        Deadline validDeadline = new DeadlineBuilder().build();

        CommandResult commandResult = new SelectDeadlineCommand(validDeadline).execute(modelStub, commandHistory);

        assertEquals(String.format(SelectDeadlineCommand.MESSAGE_SUCCESS, validDeadline), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_invalidDeadline_throwsCommandException() throws Exception {
        Deadline validDeadline = new DeadlineBuilder().build();
        SelectDeadlineCommand selectCommand = new SelectDeadlineCommand(validDeadline);
        SelectDeadlineCommandTest.ModelStub modelStub = new SelectDeadlineCommandTest.ModelStubWithDeadline(validDeadline);

        thrown.expect(CommandException.class);
        thrown.expectMessage(SelectDeadlineCommand.MESSAGE_INVALID_DEADLINE);
        selectCommand.execute(modelStub, commandHistory);
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
        public void deleteTask(Task target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void selectDeadline(Deadline deadline) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean invalidDeadline(Deadline deadline) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateTask(Task target, Task editedTask) {
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
    }

    /**
     * A model stub that contains a single deadline
     */
    private class ModelStubWithDeadline extends SelectDeadlineCommandTest.ModelStub {
        private final Deadline deadline;

        ModelStubWithDeadline(Deadline deadline) {
            requireNonNull(deadline);
            this.deadline = deadline;
        }

        @Override
        public boolean invalidDeadline(Deadline deadline) {
            requireNonNull(deadline);
            return this.deadline.isValidDeadline(deadline);
        }
    }

    /**
     * A Model stub that always accept the deadline being selected.
     */
    private class ModelStubAcceptingDeadlineSelected extends SelectDeadlineCommandTest.ModelStub {
        final ArrayList<Deadline> deadlineSelected = new ArrayList<>();

        @Override
        public boolean invalidDeadline(Deadline deadline) {
            requireNonNull(deadline);
            return deadlineSelected.stream().anyMatch(deadline::isValidDeadline);
        }

        @Override
        public void selectDeadline(Deadline deadline) {
            requireNonNull(deadline);
            deadlineSelected.add(deadline);
        }

        @Override
        public void commitTaskBook() {
            // called by {@code selectDeadlineCommand#execute()}
        }

        @Override
        public ReadOnlyTaskBook getAddressBook() {
            return new AddressBook();
        }
    }
}
