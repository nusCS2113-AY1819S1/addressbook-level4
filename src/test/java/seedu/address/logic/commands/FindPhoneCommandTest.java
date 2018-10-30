package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.ExpenseBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindPhoneCommand}.
 */
public class FindPhoneCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new ExpenseBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new ExpenseBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        PhoneContainsKeywordsPredicate firstPredicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("first"));
        PhoneContainsKeywordsPredicate secondPredicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("second"));

        FindPhoneCommand findFirstPhoneCommand = new FindPhoneCommand(firstPredicate);
        FindPhoneCommand findSecondPhoneCommand = new FindPhoneCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstPhoneCommand.equals(findFirstPhoneCommand));

        // same values -> returns true
        FindPhoneCommand findFirstPhoneCommandCopy = new FindPhoneCommand(firstPredicate);
        assertTrue(findFirstPhoneCommand.equals(findFirstPhoneCommandCopy));

        // different types -> returns false
        assertFalse(findFirstPhoneCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstPhoneCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstPhoneCommand.equals(findSecondPhoneCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PhoneContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindPhoneCommand command = new FindPhoneCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PhoneContainsKeywordsPredicate predicate = preparePredicate("95352563 9482224 9482427");
        FindPhoneCommand command = new FindPhoneCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code PhoneContainsKeywordsPredicate}.
     */
    private PhoneContainsKeywordsPredicate preparePredicate(String userInput) {
        return new PhoneContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
