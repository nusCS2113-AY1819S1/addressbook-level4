package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TaskBuilder.DEFAULT_DEADLINE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.logging.Logger;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final Logger logger = LogsCenter.getLogger(AddTaskCommandTest.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddTaskCommand(null);
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult commandResult = new AddTaskCommand(validTask).execute(modelStub, commandHistory);

        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() throws Exception {
        Task validTask = new TaskBuilder().build();
        AddTaskCommand addCommand = new AddTaskCommand(validTask);
        ModelStub modelStub = new ModelStubWithTask(validTask);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddTaskCommand.MESSAGE_DUPLICATE_TASK);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_taskWithDifferentDeadline_addSuccessful() throws Exception {
        Task typicalTask = new TaskBuilder().build();
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        modelStub.addTask(typicalTask);

        Task secondTask = new TaskBuilder().withDeadline("5/5/2018").build();
        AddTaskCommand addCommand = new AddTaskCommand(secondTask);
        modelStub.selectDeadline(new Deadline("5/5/2018"));
        CommandResult commandResult = new AddTaskCommand(secondTask).execute(modelStub, commandHistory);

        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, secondTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(typicalTask, secondTask), modelStub.tasksAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_taskWithDifferentTitle_addSuccessful() throws Exception {
        Task typicalTask = new TaskBuilder().build();
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        modelStub.addTask(typicalTask);

        Task secondTask = new TaskBuilder().withTitle("Different Title").build();
        AddTaskCommand addCommand = new AddTaskCommand(secondTask);
        CommandResult commandResult = new AddTaskCommand(secondTask).execute(modelStub, commandHistory);

        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, secondTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(typicalTask, secondTask), modelStub.tasksAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_taskWithDifferentModuleCode_addSuccessful() throws Exception {
        Task typicalTask = new TaskBuilder().build();
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        modelStub.addTask(typicalTask);

        Task secondTask = new TaskBuilder().withModuleCode("CG2271").build();
        AddTaskCommand addCommand = new AddTaskCommand(secondTask);
        CommandResult commandResult = new AddTaskCommand(secondTask).execute(modelStub, commandHistory);

        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, secondTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(typicalTask, secondTask), modelStub.tasksAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_taskWithDifferentDescription_throwsCommandException() throws Exception {
        Task typicalTask = new TaskBuilder().build();
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        modelStub.addTask(typicalTask);

        Task secondTask = new TaskBuilder().withDescription("Different description").build();
        AddTaskCommand addCommand = new AddTaskCommand(secondTask);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddTaskCommand.MESSAGE_DUPLICATE_TASK);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_taskWithDifferentPriorityLevel_throwsCommandException() throws Exception {
        Task typicalTask = new TaskBuilder().build();
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        modelStub.addTask(typicalTask);

        Task secondTask = new TaskBuilder().withPriority("low").build();
        AddTaskCommand addCommand = new AddTaskCommand(secondTask);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddTaskCommand.MESSAGE_DUPLICATE_TASK);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_taskWithDifferentHours_throwsCommandException() throws Exception {
        Task typicalTask = new TaskBuilder().build();
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        modelStub.addTask(typicalTask);

        Task secondTask = new TaskBuilder().withExpectedNumOfHours(4).build();
        AddTaskCommand addCommand = new AddTaskCommand(secondTask);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddTaskCommand.MESSAGE_DUPLICATE_TASK);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_taskWithZeroHourCompletion_throwsCommandException() throws Exception {
        Task validTask = new TaskBuilder().withExpectedNumOfHours(0).build();
        AddTaskCommand addCommand = new AddTaskCommand(validTask);
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_ZERO_HOURS_COMPLETION);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Task jan1st = new TaskBuilder().withDeadline("1/1/2018").build();
        Task nov1st = new TaskBuilder().withDeadline("1/11/2018").build();
        AddTaskCommand addJan1stCommand = new AddTaskCommand(jan1st);
        AddTaskCommand addNov1stCommand = new AddTaskCommand(nov1st);

        // same object -> returns true
        assertTrue(addJan1stCommand.equals(addJan1stCommand));

        // same values -> returns true
        AddTaskCommand addJan1stCommandCopy = new AddTaskCommand(jan1st);
        assertTrue(addJan1stCommand.equals(addJan1stCommandCopy));

        // different types -> returns false
        assertFalse(addJan1stCommand.equals(1));

        // null -> returns false
        assertFalse(addJan1stCommand.equals(null));

        // different task -> returns false
        assertFalse(addJan1stCommand.equals(addNov1stCommand));
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
            return new Deadline(DEFAULT_DEADLINE);
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
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single task.
     */
    private class ModelStubWithTask extends ModelStub {
        private final Task task;

        ModelStubWithTask(Task task) {
            requireNonNull(task);
            this.task = task;
        }

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return this.task.isSameTask(task);
        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();
        private Deadline deadline = new Deadline("1/1/2018");

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return tasksAdded.stream().anyMatch(task::isSameTask);
        }

        @Override
        public void selectDeadline(Deadline deadline) {
            this.deadline = deadline;
        }

        @Override
        public Deadline getDeadline() {
            return this.deadline;
        }

        @Override
        public void addTask(Task task) {
            requireNonNull(task);
            tasksAdded.add(task);
        }

        @Override
        public void commitTaskBook() {
            // called by {@code AddTaskCommand#execute()}
        }

        @Override
        public ReadOnlyTaskBook getAddressBook() {
            return new AddressBook();
        }
    }
}
