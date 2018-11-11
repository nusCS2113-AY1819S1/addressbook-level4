package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.TypicalDeadlines.INVALID_32ND_JAN_2018;
import static seedu.address.testutil.TypicalDeadlines.INVALID_32ND_JAN_WITHOUT_YEAR;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_APR_2018;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_APR_WITHOUT_YEAR;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_JAN_2018;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_JAN_WITHOUT_YEAR;
import static seedu.address.testutil.TypicalDeadlines.VALID_YEAR_2018;

import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Task;

//@@author emobeany
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
    public void execute_deadlineWithYearAcceptedByModel_selectSuccessful() throws Exception {
        ModelStubAcceptingDeadlineSelected modelStub = new ModelStubAcceptingDeadlineSelected();
        Deadline validDeadline = VALID_1ST_JAN_2018;

        CommandResult commandResult = new SelectDeadlineCommand(validDeadline).execute(modelStub, commandHistory);

        assertEquals(String.format(SelectDeadlineCommand.MESSAGE_SUCCESS, validDeadline), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_deadlineWithoutYearAcceptedByModel_selectSuccessful() throws Exception {
        ModelStubAcceptingDeadlineSelected modelStub = new ModelStubAcceptingDeadlineSelected();
        Deadline validDeadline = VALID_1ST_JAN_WITHOUT_YEAR;

        CommandResult commandResult = new SelectDeadlineCommand(validDeadline).execute(modelStub, commandHistory);

        assertEquals(String.format(SelectDeadlineCommand.MESSAGE_SUCCESS, validDeadline), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_invalidDeadlineWithYear_throwCommandException() throws Exception {
        Deadline invalidDeadline = INVALID_32ND_JAN_2018;
        SelectDeadlineCommand selectCommand = new SelectDeadlineCommand(invalidDeadline);
        ModelStub modelStub = new ModelStubWithDeadline(invalidDeadline);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_DEADLINE);
        selectCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_invalidDeadlineWithoutYear_throwsCommandException() throws Exception {
        Deadline invalidDeadline = INVALID_32ND_JAN_WITHOUT_YEAR;
        SelectDeadlineCommand selectCommand = new SelectDeadlineCommand(invalidDeadline);
        ModelStub modelStub = new ModelStubWithDeadline(invalidDeadline);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_DEADLINE);
        selectCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        SelectDeadlineCommand selectJan1st2018Command = new SelectDeadlineCommand(VALID_1ST_JAN_2018);
        SelectDeadlineCommand selectApr1st2018Command = new SelectDeadlineCommand(VALID_1ST_APR_2018);
        SelectDeadlineCommand selectJan1st2018CommandCopy = new SelectDeadlineCommand(VALID_1ST_JAN_2018);
        SelectDeadlineCommand selectJan1stCommand = new SelectDeadlineCommand(VALID_1ST_JAN_WITHOUT_YEAR);
        SelectDeadlineCommand selectApr1stCommand = new SelectDeadlineCommand(VALID_1ST_APR_WITHOUT_YEAR);

        // Same object -> returns true
        assertEquals(selectJan1st2018Command, selectJan1st2018Command);

        // Same deadline -> returns true
        assertEquals(selectJan1st2018Command, selectJan1st2018CommandCopy);

        // Same deadline since default year is 2018 -> returns true
        assertEquals(selectJan1st2018Command, selectJan1stCommand);

        // Null -> returns false
        assertNotEquals(selectJan1st2018Command, (null));
        assertNotEquals(selectJan1stCommand, (null));

        // Different types -> returns false
        assertNotEquals(selectJan1st2018Command, 1);
        assertNotEquals(selectJan1stCommand, 1);

        // Different deadline -> returns false
        assertNotEquals(selectJan1st2018Command, selectApr1st2018Command);
        assertNotEquals(selectJan1stCommand, selectApr1st2018Command);
        assertNotEquals(selectJan1stCommand, selectApr1stCommand);

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

        //author ChanChunCheong
        @Override
        public void addTag(Task task, Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeTag(Task task, Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void selectTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortTask(String method) {
            throw new AssertionError("This method should not be called.");
        }
        //author


        @Override
        public Deadline getDeadline() {
            return new Deadline(VALID_1ST_JAN_2018.toString());
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
        public boolean isTheExactSameTaskAs(Task task) {
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

        @Override
        public String getYear() {
            return VALID_YEAR_2018;
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
    }

    /**
     * A model stub that always accepts the deadline being selected.
     */
    private class ModelStubAcceptingDeadlineSelected extends ModelStub {

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
