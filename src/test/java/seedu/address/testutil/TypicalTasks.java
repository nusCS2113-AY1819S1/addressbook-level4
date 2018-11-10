package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_BODY_ASSIGN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BODY_QUIZ;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATETIME_ASSIGN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATETIME_QUIZ;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_ASSIGN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_QUIZ;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATETIME_ASSIGN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATETIME_QUIZ;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HARDCOPY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_IVLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_ASSIGN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_QUIZ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TaskBook;
import seedu.address.model.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task ASSIGNMENT1 = new TaskBuilder().withTaskName("Assignment1 Submission")
            .withBody("CG2027 Assign1").withStartDateTime("2/10_17:00")
            .withEndDateTime("9/10_14:00").withPriority("HIGH").withTags("IVLE").build();
    public static final Task ASSIGNMENT2 = new TaskBuilder().withTaskName("Assignment2 Submission")
            .withBody("CG2027 Assign2").withStartDateTime("9/10")
            .withEndDateTime("16/10_14:00").withPriority("HIGH").withTags("IVLE").build();
    public static final Task ASSIGNMENT3 = new TaskBuilder().withTaskName("Assignment3 Submission")
            .withBody("CG2027 Assign3").withStartDateTime("16/10")
            .withEndDateTime("23/10_14:00").withPriority("HIGH").withTags("IVLE").build();
    public static final Task PROJECT = new TaskBuilder().withTaskName("Practical Exam")
            .withBody("CS2113 Product Release").withStartDateTime("2/11")
            .withEndDateTime("2/11_16:00").withPriority("MED").withTags("LT15").build();
    public static final Task QUIZ = new TaskBuilder().withTaskName("Quiz submission")
            .withBody("MA1508E  Quiz10").withStartDateTime("22/10")
            .withEndDateTime("28/10_22:00").withPriority("LOW").withTags("IVLE").build();

    // Manually added
    public static final Task ASSIGNMENT4 = new TaskBuilder().withTaskName("Assignment4 Submission")
            .withBody("CG2027 Assign4").withStartDateTime("23/10_17:00")
            .withEndDateTime("30/10_14:00").withPriority("HIGH").build();
    public static final Task QUIZ11 = new TaskBuilder().withTaskName("Quiz submission")
            .withBody("MA1508E Quiz11").withStartDateTime("29/10")
            .withEndDateTime("4/11_22:00").withPriority("MED").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Task QUIZ9 = new TaskBuilder().withTaskName(VALID_TASK_NAME_QUIZ).withBody(VALID_BODY_QUIZ)
            .withStartDateTime(VALID_START_DATETIME_QUIZ).withEndDateTime(VALID_END_DATETIME_QUIZ)
            .withPriority(VALID_PRIORITY_QUIZ).withTags(VALID_TAG_IVLE).build();
    public static final Task ASSIGNMENT5 = new TaskBuilder().withTaskName(VALID_TASK_NAME_ASSIGN)
            .withBody(VALID_BODY_ASSIGN).withStartDateTime(VALID_START_DATETIME_ASSIGN)
            .withEndDateTime(VALID_END_DATETIME_ASSIGN).withPriority(VALID_PRIORITY_ASSIGN)
            .withTags(VALID_TAG_IVLE, VALID_TAG_HARDCOPY).build();

    public static final String KEYWORD_MATCHING_MEIER = "Assignment"; // A keyword that matches Assignment

    private TypicalTasks(){}; // prevents instantiation
    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static TaskBook getTypicalTaskBook() {
        TaskBook tb = new TaskBook();
        for (Task task : getTypicalTasks()) {
            tb.addTask(task);
        }
        return tb;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList());
    }
}
