package seedu.planner.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.logic.commands.FindTagCommand.MESSAGE_SUCCESS;
import static seedu.planner.logic.commands.FindTagCommand.convertKeywordsToMessage;
import static seedu.planner.testutil.TypicalRecords.CAIFAN;
import static seedu.planner.testutil.TypicalRecords.INDO;
import static seedu.planner.testutil.TypicalRecords.ZT;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.record.TagsContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindTagCommand}.
 */
public class FindTagCommandTest {
    private Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        String inputFirstString = "first";
        String inputSecondString = "second";
        TagsContainsKeywordsPredicate firstPredicate =
                new TagsContainsKeywordsPredicate(Collections.singletonList(inputFirstString));
        TagsContainsKeywordsPredicate secondPredicate =
                new TagsContainsKeywordsPredicate(Collections.singletonList(inputSecondString));
        String[] inputFirstStringArray = inputFirstString.split("\\s+");
        String[] inputSecondStringArray = inputSecondString.split("\\s+");

        FindTagCommand findFirstCommand = new FindTagCommand(firstPredicate, inputFirstStringArray);
        FindTagCommand findSecondCommand = new FindTagCommand(secondPredicate, inputSecondStringArray);

        // same object -> returns true
        Assert.assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindTagCommand findFirstCommandCopy = new FindTagCommand(firstPredicate, inputFirstStringArray);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        Assert.assertNotEquals(1, findFirstCommand);

        // null -> returns false
        Assert.assertNotEquals(null, findFirstCommand);

        // different record -> returns false
        Assert.assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void execute_singleKeywords_noRecordFound() {
        String inputKeywords = "salary";
        TagsContainsKeywordsPredicate predicate = preparePredicate(inputKeywords);
        String[] keywords = inputKeywords.split("\\s+");
        FindTagCommand command = new FindTagCommand(predicate, keywords);
        expectedModel.updateFilteredRecordList(predicate);
        String expectedMessage = String.format(MESSAGE_SUCCESS,
                expectedModel.getFilteredRecordList().size(), convertKeywordsToMessage(keywords));
        assertCommandSuccess(command, model, commandHistory, expectedMessage , expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRecordList());
    }

    @Test
    public void execute_multipleKeywords_multipleRecordsFound() {
        String inputKeywords = "friends owesmoney";
        TagsContainsKeywordsPredicate predicate = preparePredicate(inputKeywords);
        String[] keywords = inputKeywords.split("\\s+");
        FindTagCommand command = new FindTagCommand(predicate, keywords);
        expectedModel.updateFilteredRecordList(predicate);
        String expectedMessage = String.format(MESSAGE_SUCCESS,
                expectedModel.getFilteredRecordList().size(), convertKeywordsToMessage(keywords));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(INDO, CAIFAN, ZT), model.getFilteredRecordList());
    }

    private TagsContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TagsContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
