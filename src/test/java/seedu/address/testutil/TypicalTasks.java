package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_12TH_MAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_1ST_JAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_31ST_MARCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2113;
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

//@@author chelseyong
/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {
    // Manually added
    public static final Task CS2113_HOMEWORK = new TaskBuilder().withDeadline("1/1/2018").withModuleCode("CS2113")
            .withTitle("Complete code refactoring").withDescription("Refer to notes!")
            .withPriority("low").withTags("software", "engineering").build();

    public static final Task CS2101_HOMEWORK = new TaskBuilder().withDeadline("9/10/2018").withModuleCode("CS2101")
        .withTitle("Plan a OP2 meeting").withDescription("OP2 is 40% of the grade")
        .withPriority("high").withTags("english").build();

    public static final Task CS2102_HOMEWORK = new TaskBuilder().withDeadline("11/11/2018").withModuleCode("CS2102")
        .withTitle("Set up the backend framework").withDescription("Using Flask")
            .withPriority("medium").withTags("database").build();

    public static final Task CG2271_HOMEWORK = new TaskBuilder().withDeadline("5/6/2018").withModuleCode("CG2271")
        .withTitle("Implement message passing").withDescription("Symmetric & indirect naming scheme")
            .withDescription("low").withTags("real").build();

    public static final Task CG1112_HOMEWORK = new TaskBuilder().withDeadline("2/5/2018").withModuleCode("CG1112")
        .withTitle("Write buffer class").withDescription("refer to api")
            .withDescription("high").withTags("vincent").build();

    public static final Task CS2114_HOMEWORK = new TaskBuilder().withDeadline("1/1/2018").withModuleCode("CS2114")
            .withTitle("Start on code").withDescription("Find out more about syntax of new language")
            .withPriority("high").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Task CS2113_TASK_1 = new TaskBuilder().withDeadline(VALID_DEADLINE_31ST_MARCH)
        .withModuleCode(VALID_MODULE_CODE_CS2113).withTitle(VALID_TITLE_1).withDescription(VALID_DESCRIPTION_1)
        .withPriority(VALID_PRIORITY_LEVEL_LOW).build();

    public static final Task CS2113_TASK_1_WITHOUT_MODULE_CODE = new TaskBuilder()
            .withDeadline(VALID_DEADLINE_31ST_MARCH).withTitle(VALID_TITLE_1)
            .withDescription(VALID_DESCRIPTION_1).withPriority(VALID_PRIORITY_LEVEL_LOW).build();

    public static final Task CS2113_TASK_2 = new TaskBuilder().withDeadline(VALID_DEADLINE_1ST_JAN)
        .withModuleCode(VALID_MODULE_CODE_CS2113).withTitle(VALID_TITLE_2).withDescription(VALID_DESCRIPTION_2)
        .withPriority(VALID_PRIORITY_LEVEL_HIGH).build();
    public static final Task CS2113_TASK_3 = new TaskBuilder().withDeadline(VALID_DEADLINE_12TH_MAY)
        .withModuleCode(VALID_MODULE_CODE_CS2113).withTitle(VALID_TITLE_3).withDescription(VALID_DESCRIPTION_3)
        .withPriority(VALID_PRIORITY_LEVEL_MEDIUM).build();

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

    public static AddressBook getTypicalTaskBook_task1And4DiffDeadlines() {
        AddressBook ab = new AddressBook();
        for (Task task : getTypicalTasks_task1And4DiffDeadlines()) {
            ab.addTask(task);
        }
        return ab;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(CS2101_HOMEWORK, CS2102_HOMEWORK, CS2113_HOMEWORK, CS2114_HOMEWORK));
    }

    public static List<Task> getTypicalTasks_task1And4DiffDeadlines() {
        Task task = new TaskBuilder(CS2101_HOMEWORK).withDeadline("10/10/2018").build();
        return new ArrayList<>(Arrays.asList(CS2101_HOMEWORK, CS2102_HOMEWORK, CS2113_HOMEWORK, task));
    }
}
