package seedu.planner.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MONEYFLOW;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.Model;
import seedu.planner.model.record.NameContainsKeywordsPredicate;
import seedu.planner.model.record.Record;
import seedu.planner.testutil.EditRecordDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Payment from Amy";
    public static final String VALID_NAME_BOB = "Payment from Bob";
    public static final String VALID_DATE_AMY = "11-10-2004";
    public static final String VALID_DATE_BOB = "11-02-2004";
    public static final String VALID_MONEYFLOW_INCOME_AMY = "+10.90";
    public static final String VALID_MONEYFLOW_EXPENSE_BOB = "-11.50";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";


    public static final String INVALID_NAME = "James&";
    public static final String INVALID_DATE = "911a";
    public static final String INVALID_MONEYFLOW = "11.";
    public static final String INVALID_TAG = "hubby*";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String DATE_DESC_AMY = " " + PREFIX_DATE + VALID_DATE_AMY;
    public static final String DATE_DESC_BOB = " " + PREFIX_DATE + VALID_DATE_BOB;
    public static final String MONEYFLOW_INCOME_DESC_AMY = " " + PREFIX_MONEYFLOW + VALID_MONEYFLOW_INCOME_AMY;
    public static final String MONEYFLOW_EXPENSE_DESC_BOB = " " + PREFIX_MONEYFLOW + VALID_MONEYFLOW_EXPENSE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + INVALID_NAME; // '&' not allowed in names
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + INVALID_DATE; // alphabets not allowed in date
    public static final String INVALID_MONEYFLOW_DESC = " "
            + PREFIX_MONEYFLOW + INVALID_MONEYFLOW; // missing digit after decimal point
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + INVALID_TAG; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditRecordDescriptor DESC_AMY;
    public static final EditCommand.EditRecordDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditRecordDescriptorBuilder().withName(VALID_NAME_AMY)
                .withDate(VALID_DATE_AMY).withMoneyFlow(VALID_MONEYFLOW_INCOME_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditRecordDescriptorBuilder().withName(VALID_NAME_BOB)
                .withDate(VALID_DATE_BOB).withMoneyFlow(VALID_MONEYFLOW_EXPENSE_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

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
     * - the planner book and the filtered record list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        FinancialPlanner expectedFinancialPlanner = new FinancialPlanner(actualModel.getFinancialPlanner());
        List<Record> expectedFilteredList = new ArrayList<>(actualModel.getFilteredRecordList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedFinancialPlanner, actualModel.getFinancialPlanner());
            assertEquals(expectedFilteredList, actualModel.getFilteredRecordList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the record at the given {@code targetIndex} in the
     * {@code model}'s planner book.
     */
    // TODO: Look at this code again
    public static void showRecordAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredRecordList().size());

        Record record = model.getFilteredRecordList().get(targetIndex.getZeroBased());
        final String[] splitName = record.getName().fullName.split("\\s+");
        model.updateFilteredRecordList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredRecordList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the record
     * at the given {@code targetIndexInt} in the {@code model}'s planner book.
     * @param model
     * @param targetIndexInt
     */
    public static void showRecordAtIndex(Model model, int targetIndexInt) {
        assertTrue((targetIndexInt < model.getFilteredRecordList().size()));

        Record record = model.getFilteredRecordList().get(targetIndexInt);
        final String[] splitName = record.getName().fullName.split("\\s+");
        model.updateFilteredRecordList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredRecordList().size());
    }

    /**
     * Deletes the first record in {@code model}'s filtered list from {@code model}'s planner book.
     */
    public static void deleteFirstRecord(Model model) {
        Record firstRecord = model.getFilteredRecordList().get(0);
        model.deleteRecord(firstRecord);
        model.commitFinancialPlanner();
    }

}
