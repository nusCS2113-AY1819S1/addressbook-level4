package seedu.address.commons.util;

import static junit.framework.TestCase.assertTrue;
import static seedu.address.testutil.TypicalTasks.ASSIGNMENT;
import static seedu.address.testutil.TypicalTasks.TUTORIAL;

import org.junit.Test;

import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class SortComparatorTest {

    @Test
    public void compareNameEqualsTest() {
        Task task = new TaskBuilder(ASSIGNMENT).withName("Tutorial 5").build();
        int Result = SortComparator.compareName().compare(task, TUTORIAL);
        assertTrue (Result == 0);
    }

    @Test
    public void compareNameLessTest() {
        Task task = new TaskBuilder(ASSIGNMENT).withName("Tutorial 5").build();
        int Result = SortComparator.compareName().compare(ASSIGNMENT, task);
        assertTrue (Result < 0);
    }

    @Test
    public void compareNameCaseEqualsTest() {
        Task task = new TaskBuilder(ASSIGNMENT).withName("tutorial 5").build();
        int Result = SortComparator.compareName().compare(task, TUTORIAL);
        assertTrue(Result == 0);
    }

    @Test
    public void compareModuleEqualsTest() {
        Task task = new TaskBuilder(ASSIGNMENT).withModule("CG2040").build();
        int Result = SortComparator.compareModule().compare(task, TUTORIAL);
        assertTrue(Result == 0);
    }

    @Test
    public void comparePriorityEqualsTest() {
        Task task = new TaskBuilder(ASSIGNMENT).withPriority("3").build();
        int Result = SortComparator.comparePriority().compare(task, TUTORIAL);
        assertTrue(Result == 0);
    }

    @Test
    public void comparePriorityLessTest() {
        Task task = new TaskBuilder(ASSIGNMENT).withPriority("1").build();
        int Result = SortComparator.comparePriority().compare(task, TUTORIAL);
        assertTrue(Result < 0);
    }

    @Test
    public void compareModuleLessTest() {
        Task task = new TaskBuilder(ASSIGNMENT).withModule("CG2027").build();
        int Result = SortComparator.compareModule().compare(TUTORIAL, task);
        assertTrue(Result < 0);
    }

}
