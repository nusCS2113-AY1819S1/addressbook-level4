package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TodoList;
import seedu.address.model.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task ASSIGNMENT = new TaskBuilder().withName("Assignment 2").withModule("CG2027")
            .withDate("16-10").withPriority("2").build();
    public static final Task LAB = new TaskBuilder().withName("Lab 1").withModule("CG2028")
            .withDate("30-08").withPriority("3").build();
    public static final Task PROJECT = new TaskBuilder().withName("Crowdfunding Website").withModule("CS2102")
            .withDate("11-11").withPriority("1").build();
    public static final Task QUIZ = new TaskBuilder().withName("Quiz 9").withModule("CS1231")
            .withDate("14-10").withPriority("1").build();
    public static final Task TUTORIAL = new TaskBuilder().withName("Tutorial 5").withModule("CS2040")
            .withDate("26-09").withPriority("3").build();
    public static final Task WEBCAST = new TaskBuilder().withName("Lecture 3").withModule("CS2113")
            .withDate("12-10").withPriority("2").build();

    // Manually added
    public static final Task ESSAY = new TaskBuilder().withName("Essay 1").withModule("CG1111")
            .withDate("12-12").withPriority("3").build();
    public static final Task REFLECTION = new TaskBuilder().withName("CA1 Reflection").withModule("CS2101")
            .withDate("10-10").withPriority("3").build();
    public static final Task REVISION = new TaskBuilder().withName("C Revision").withModule("CS1010")
            .withDate("14-11").withPriority("1").build();
    public static final Task SURVEY = new TaskBuilder().withName("Lecture Survey").withModule("CS2113")
            .withDate("17-10").withPriority("2").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code TodoList} with all the typical tasks.
     */
    public static TodoList getTypicalTodoList() {
        TodoList td = new TodoList();
        for (Task task : getTypicalTasks()) {
            td.addTask(task);
        }
        return td;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(ASSIGNMENT, LAB, PROJECT, QUIZ, TUTORIAL, WEBCAST));
    }
}
