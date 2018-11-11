package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskContainsKeywordsPredicate;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    //@@author chelseyong
    public static final String VALID_DEFERDAYS = "2";
    public static final int VALID_DEFERDAYS_INT = 2;
    public static final String VALID_DEADLINE_1ST_JAN = "1/1/2018";
    public static final String VALID_DEADLINE_31ST_MARCH = "31/3/2018";
    public static final String VALID_DEADLINE_12TH_MAY = "12/5/2018";
    public static final String VALID_MODULE_CODE_CS2113 = "CS2113";
    public static final String VALID_MODULE_CODE_CG2271 = "CG2271";
    public static final String VALID_TAG = "module";
    public static final String VALID_MODULE_CODE_CS2101 = "CS2101";
    public static final String VALID_TITLE_1 = "Complete CS2113 Homework";
    public static final String VALID_TITLE_2 = "Start coding test units";
    public static final String VALID_TITLE_3 = "Prepare OP2";
    public static final String VALID_DESCRIPTION_1 = "Refer to notes";
    public static final String VALID_DESCRIPTION_2 = "Do this before integration tests";
    public static final String VALID_DESCRIPTION_3 = "OP2 has high weightage";
    public static final String VALID_PRIORITY_LEVEL_LOW = "low";
    public static final String VALID_PRIORITY_LEVEL_HIGH = "high";
    public static final String VALID_PRIORITY_LEVEL_MEDIUM = "medium";
    public static final String VALID_1_HOUR = "1";
    public static final String VALID_2_HOURS = "2";
    public static final String TAG_NON_ALPHA = "+";
    public static final Index VALID_INDEX = Index.fromOneBased(1);
    public static final String VALID_INDEX_1 = "1";
    public static final String VALID_INDEX_2 = "2";
    public static final String VALID_MILESTONE_DESCRIPTION_1 = "Dummy milestone";
    public static final String VALID_RANK_1 = "1";
    public static final String VALID_SORTING_METHOD_PRIORITY = "priority";

    public static final String DEADLINE_DESC_1ST_JAN = " " + PREFIX_DEADLINE + VALID_DEADLINE_1ST_JAN;
    public static final String DEADLINE_DESC_31ST_MARCH = " " + PREFIX_DEADLINE + VALID_DEADLINE_31ST_MARCH;
    public static final String DEADLINE_DESC_12TH_MAY = " " + PREFIX_DEADLINE + VALID_DEADLINE_12TH_MAY;
    public static final String DEFERDEADLINE_TWODAY = " " + PREFIX_DAY + VALID_DEFERDAYS;
    public static final String MODULE_CODE_CS2113_DESC = " " + PREFIX_MODULE_CODE + VALID_MODULE_CODE_CS2113;
    public static final String MODULE_CODE_CG2271_DESC = " " + PREFIX_MODULE_CODE + VALID_MODULE_CODE_CG2271;
    public static final String TAG_MODULE = " " + PREFIX_TAG + VALID_TAG;
    public static final String VALID_INDEX_STRING = " " + PREFIX_INDEX + "1";
    public static final String TITLE_DESC_1 = " " + PREFIX_TITLE + VALID_TITLE_1;
    public static final String TITLE_DESC_2 = " " + PREFIX_TITLE + VALID_TITLE_2;
    public static final String TITLE_DESC_3 = " " + PREFIX_TITLE + VALID_TITLE_3;
    public static final String DESCRIPTION_DESC_1 = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_1;
    public static final String DESCRIPTION_DESC_2 = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_2;
    public static final String DESCRIPTION_DESC_3 = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_3;
    public static final String PRIORITY_LEVEL_DESC_LOW = " " + PREFIX_PRIORITY + VALID_PRIORITY_LEVEL_LOW;
    public static final String PRIORITY_LEVEL_DESC_HIGH = " " + PREFIX_PRIORITY + VALID_PRIORITY_LEVEL_HIGH;
    public static final String PRIORITY_LEVEL_DESC_MEDIUM = " " + PREFIX_PRIORITY + VALID_PRIORITY_LEVEL_MEDIUM;
    public static final String HOURS_DESC_1 = " " + PREFIX_HOURS + VALID_1_HOUR;
    public static final String HOURS_DESC_2 = " " + PREFIX_HOURS + VALID_2_HOURS;
    public static final String INDEX_DESC_1 = " " + PREFIX_INDEX + VALID_INDEX_1;
    public static final String INDEX_DESC_2 = " " + PREFIX_INDEX + VALID_INDEX_2;
    public static final String MILESTONE_DESCRIPTION_DESC_1 = " " + PREFIX_MILESTONE + VALID_MILESTONE_DESCRIPTION_1;
    public static final String RANK_DESC_1 = " " + PREFIX_RANK + VALID_RANK_1;
    public static final String SORT_PRIORITY = " " + PREFIX_SORT + VALID_SORTING_METHOD_PRIORITY;

    public static final String INVALID_DEFERDAYS_EXCEEDED = " " + PREFIX_DAY + "32";
    public static final String INVALID_DEFERDAYS_NEGATIVE = " " + PREFIX_DAY + "-1";
    public static final String INVALID_DEADLINE_DESC = " " + PREFIX_DEADLINE + "31/2"; // No 31st February in calendar
    public static final String INVALID_TAG_NON_ALPHA = " " + PREFIX_TAG + TAG_NON_ALPHA; // TAG NEEDS TO BE ALPHANUMERIC
    public static final String INVALID_INDEX_STRING = " " + PREFIX_INDEX + "0";
    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + " "; // Title is empty
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + " "; // Description is empty
    public static final String INVALID_PRIORITY_LEVEL_DESC = " " + PREFIX_PRIORITY + "mid"; // not a priority level
    public static final String INVALID_SORTING_METHOD_HOURS = " " + PREFIX_SORT + "hours";

    public static final String INVALID_MODULE_CODE_DESC = " " + PREFIX_MODULE_CODE + "2113CS";
    public static final String EMPTY_MODULE_CODE_DESC = " " + PREFIX_MODULE_CODE + " ";

    public static final int OVERFLOW_INT = Integer.MAX_VALUE;
    public static final String INVALID_HOURS_DESC = " " + PREFIX_HOURS + "one"; // not an integer
    public static final String ZERO_HOURS_DESC = " " + PREFIX_HOURS + "0";
    public static final String NEGATIVE_HOURS_DESC = " " + PREFIX_HOURS + "-1";
    public static final String INVALID_HOURS_OVERFLOW = " " + PREFIX_HOURS
            + Long.toString((long) OVERFLOW_INT + 1);

    public static final String INVALID_INDEX_OVERFLOW = " " + PREFIX_INDEX
            + Long.toString((long) OVERFLOW_INT + 1);
    public static final String INVALID_INDEX_DESC_ZERO = " " + PREFIX_INDEX + "0";
    public static final String INVALID_INDEX_DESC_NEGATIVE = " " + PREFIX_INDEX + "-1";
    public static final String INVALID_PREAMBLE_INDEX_DESC = " " + PREFIX_INDEX + "1a";
    public static final String INVALID_EMPTY_INDEX_DESC = " " + PREFIX_INDEX + "";
    public static final String INVALID_INDEX_DESC = " " + PREFIX_INDEX + "a" + VALID_INDEX_1;

    public static final String INVALID_MILESTONE_DESCRIPTION_DESC = " " + PREFIX_MILESTONE
            + "Dummy milestone with forty one characters";
    public static final String INVALID_RANK_DESC_ZERO = " " + PREFIX_RANK + "0";
    public static final String INVALID_RANK_DESC_NEGATIVE = " " + PREFIX_RANK + "-1";
    public static final String EMPTY_INDEX_DESC = " " + PREFIX_INDEX + " ";
    public static final String EMPTY_MILESTONE_DESCRIPTION_DESC = " " + PREFIX_MILESTONE + " ";
    public static final String EMPTY_RANK_DESC = " " + PREFIX_RANK + " ";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    //@@author
    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered task list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Task> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTaskList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredTaskList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }
    /**
     *Updates {@code model}'s filtered list to show only the task at the given {@code targetIndex} in the
     *{@code model}'s address book.
     */
    public static void showTaskAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTaskList().size());

        Task task = model.getFilteredTaskList().get(targetIndex.getZeroBased());
        final String[] splitName = task.getTitle().split("\\s+");
        model.updateFilteredTaskList(new TaskContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredTaskList().size());
    }

    /**
     * Deletes the first task in {@code model}'s filtered list from {@code model}'s task book.
     */
    public static void deleteFirstTask(Model model) {
        Task firstTask = model.getFilteredTaskList().get(0);
        model.deleteTask(firstTask);
        model.commitTaskBook();
    }
}
