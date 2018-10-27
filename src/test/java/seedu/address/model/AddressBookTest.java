package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LEVEL_HIGH;
import static seedu.address.testutil.TypicalTasks.CS2113_TASK_1;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

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

//@@author chelseyong
public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getTaskList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalTaskBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsDuplicateTaskException() {
        // Two tasks with the same title and deadline fields
        Task editedTask1 = new TaskBuilder(CS2113_TASK_1).withDescription(VALID_DESCRIPTION_2)
                .withPriority(VALID_PRIORITY_LEVEL_HIGH).build();
        List<Task> newTasks = Arrays.asList(CS2113_TASK_1, editedTask1);
        TaskBookStub newData = new TaskBookStub(newTasks);

        thrown.expect(DuplicateTaskException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasTask_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasTask(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasTask(CS2113_TASK_1));
    }

    @Test
    public void hasTask_taskInTaskBook_returnsTrue() {
        addressBook.addTask(CS2113_TASK_1);
        assertTrue(addressBook.hasTask(CS2113_TASK_1));
    }

    @Test
    public void hasTask_taskWithSameTitleAndSameDeadlineInTaskBook_returnsTrue() {
        addressBook.addTask(CS2113_TASK_1);
        Task editedTask1 = new TaskBuilder(CS2113_TASK_1).withDescription(VALID_DESCRIPTION_2)
                .withPriority(VALID_PRIORITY_LEVEL_HIGH).build();
        assertTrue(addressBook.hasTask(editedTask1));
    }

    @Test
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTaskList().remove(0);
    }

    /**
     * A stub ReadOnlyTaskBook whose tasks list can violate interface constraints.
     */
    private static class TaskBookStub implements ReadOnlyTaskBook {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        TaskBookStub(Collection<Task> tasks) {
            this.tasks.setAll(tasks);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }
    }

}
