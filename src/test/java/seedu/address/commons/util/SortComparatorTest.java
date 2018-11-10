package seedu.address.commons.util;

import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static seedu.address.testutil.TypicalTasks.ASSIGNMENT;
import static seedu.address.testutil.TypicalTasks.TUTORIAL;

public class SortComparatorTest {

    @Test
    public void compareName_equalsTest() {
        Task task = new TaskBuilder(ASSIGNMENT).withName("Tutorial 5").build();
        int Result = SortComparator.compareName().compare(task,TUTORIAL);
        assertTrue (Result == 0);
    }

    @Test
    public void compareName_lessTest() {
        Task task = new TaskBuilder(ASSIGNMENT).withName("Tutorial 5").build();
        int Result = SortComparator.compareName().compare(task, ASSIGNMENT);
        assertTrue (Result <= -1);
    }

    @Test
    public void compareName_caseEqualsTest() {
        Task task = new TaskBuilder(ASSIGNMENT).withName("tutorial 5").build();
        int Result = SortComparator.compareName().compare(task, TUTORIAL);
        assertTrue(Result == 0);
    }

    @Test
    public void compareModule_equalsTest() {
        Task task = new TaskBuilder(ASSIGNMENT).withModule("CG2027").build();
        int Result = SortComparator.compareModule().compare(task, ASSIGNMENT);
        assertTrue(Result == 0);
    }

}
