package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_31ST_MARCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LEVEL_HIGH;
import static unrefactored.testutil.TypicalTasks.CS2113_TASK_1;
import static unrefactored.testutil.TypicalTasks.CS2113_TASK_2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;
import unrefactored.testutil.TaskBuilder;

public class UniqueTaskListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueTaskList uniqueTaskList = new UniqueTaskList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.contains(null);
    }

    @Test
    public void contains_taskNotInList_returnsFalse() {
        assertFalse(uniqueTaskList.contains(CS2113_TASK_1));
    }

    @Test
    public void contains_taskInList_returnsTrue() {
        uniqueTaskList.add(CS2113_TASK_1);
        assertTrue(uniqueTaskList.contains(CS2113_TASK_1));
    }

    @Test
    public void contains_taskWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTaskList.add(CS2113_TASK_1);
        Task editedTask1 = new TaskBuilder(CS2113_TASK_1).withDeadline(DEADLINE_DESC_31ST_MARCH)
                .withTitle(VALID_DESCRIPTION_2).withPriority(VALID_PRIORITY_LEVEL_HIGH)
                .build();
        assertTrue(uniqueTaskList.contains(editedTask1));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueTaskList.add(CS2113_TASK_1);
        thrown.expect(DuplicateTaskException.class);
        uniqueTaskList.add(CS2113_TASK_1);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.setTask(null, CS2113_TASK_1);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.setTask(CS2113_TASK_1, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsTaskNotFoundException() {
        thrown.expect(TaskNotFoundException.class);
        uniqueTaskList.setTask(CS2113_TASK_1, CS2113_TASK_1);
    }

    /*@Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueTaskList.add(CS2113_TASK_1);
        uniqueTaskList.setTask(CS2113_TASK_1, CS2113_TASK_1);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(CS2113_TASK_1);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }*/

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsTaskNotFoundException() {
        thrown.expect(TaskNotFoundException.class);
        uniqueTaskList.remove(CS2113_TASK_1);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueTaskList.add(CS2113_TASK_1);
        uniqueTaskList.remove(CS2113_TASK_1);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTasks_nullUniqueTaskList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.setTasks((UniqueTaskList) null);
    }

    @Test
    public void setTasks_uniquePersonList_replacesOwnListWithProvidedUniqueTaskList() {
        uniqueTaskList.add(CS2113_TASK_1);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(CS2113_TASK_2);
        uniqueTaskList.setTasks(expectedUniqueTaskList);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTasks_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTaskList.setTasks((List<Task>) null);
    }

    @Test
    public void setTasks_list_replacesOwnListWithProvidedList() {
        uniqueTaskList.add(CS2113_TASK_1);
        List<Task> taskList = Collections.singletonList(CS2113_TASK_2);
        uniqueTaskList.setTasks(taskList);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(CS2113_TASK_2);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTasks_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Task> listWithDuplicateTasks = Arrays.asList(CS2113_TASK_1, CS2113_TASK_1);
        thrown.expect(DuplicateTaskException.class);
        uniqueTaskList.setTasks(listWithDuplicateTasks);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueTaskList.asUnmodifiableObservableList().remove(0);
    }
}
