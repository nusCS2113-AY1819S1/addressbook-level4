package seedu.address;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_12TH_MAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_1ST_JAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_31ST_MARCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LEVEL_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LEVEL_LOW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LEVEL_MEDIUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.task.Task;


/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    // Manually added
    public static final Task CS2113_homework = new TaskBuilder().withDeadline("1/1")
            .withTitle("Complete code refactoring").withDescription("Refer to notes!")
            .withPriority("low").build();

    public static final Task CS2101_homework = new TaskBuilder().withDeadline("9/10")
        .withTitle("Plan a OP2 meeting").withDescription("OP2 is 40% of the grade")
        .withPriority("high").build();

    public static final Task CS2102_homework = new TaskBuilder().withDeadline("11/11")
        .withTitle("Set up the backend framework").withDescription("Using Flask")
            .withDescription("medium").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Task CS2113_TASK_1 = new TaskBuilder().withDeadline(VALID_DEADLINE_31ST_MARCH)
        .withTitle(VALID_TITLE_1).withDescription(VALID_DESCRIPTION_1)
        .withPriority(VALID_PRIORITY_LEVEL_LOW).build();
    public static final Task CS2113_TASK_2 = new TaskBuilder().withDeadline(VALID_DEADLINE_1ST_JAN)
        .withTitle(VALID_TITLE_2).withDescription(VALID_DESCRIPTION_2)
        .withPriority(VALID_PRIORITY_LEVEL_HIGH).build();
    public static final Task CS2113_TASK_3 = new TaskBuilder().withDeadline(VALID_DEADLINE_12TH_MAY)
        .withTitle(VALID_TITLE_3).withDescription(VALID_DESCRIPTION_3)
        .withPriority(VALID_PRIORITY_LEVEL_MEDIUM).build();

    // public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalTaskBook() {
        AddressBook ab = new AddressBook();
        for (Task task : getTypicalTasks()) {
            ab.addTask(task);
        }
        return ab;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(CS2101_homework, CS2102_homework, CS2113_homework));
    }
}
