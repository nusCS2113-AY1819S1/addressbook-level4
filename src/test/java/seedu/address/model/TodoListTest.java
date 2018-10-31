package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DATE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_PRIORITY_ASSIGNMENT;
import static seedu.address.testutil.TypicalTasks.ESSAY;
import static seedu.address.testutil.TypicalTasks.getTypicalTodoList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.testutil.TaskBuilder;

public class TodoListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TodoList todoList = new TodoList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), todoList.getTaskList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        todoList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyTodoList_replacesData() {
        TodoList newData = getTypicalTodoList();
        todoList.resetData(newData);
        assertEquals(newData, todoList);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsDuplicateTaskException() {
        // Two tasks with the same identity fields
        Task editedEssay = new TaskBuilder(ESSAY).withDate(VALID_TASK_DATE_ASSIGNMENT)
                .withPriority(VALID_TASK_PRIORITY_ASSIGNMENT).build();
        List<Task> newTasks = Arrays.asList(ESSAY, editedEssay);
        TodoListStub newData = new TodoListStub(newTasks);

        thrown.expect(DuplicateTaskException.class);
        todoList.resetData(newData);
    }

    @Test
    public void hasTask_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        todoList.hasTask(null);
    }

    @Test
    public void hasTask_taskNotInTodoList_returnsFalse() {
        assertFalse(todoList.hasTask(ESSAY));
    }

    @Test
    public void hasTask_taskInTodoList_returnsTrue() {
        todoList.addTask(ESSAY);
        assertTrue(todoList.hasTask(ESSAY));
    }

    @Test
    public void hasTask_taskWithSameIdentityFieldsInTodoList_returnsTrue() {
        todoList.addTask(ESSAY);
        Task editedEssay = new TaskBuilder(ESSAY).withDate(VALID_TASK_DATE_ASSIGNMENT)
                .withPriority(VALID_TASK_PRIORITY_ASSIGNMENT).build();
        assertTrue(todoList.hasTask(editedEssay));
    }

    @Test
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        todoList.getTaskList().remove(0);
    }

    /**
     * A stub ReadOnlyTodoList whose tasks list can violate interface constraints.
     */
    private static class TodoListStub implements ReadOnlyTodoList {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        TodoListStub(Collection<Task> tasks) {
            this.tasks.setAll(tasks);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }
    }
}
