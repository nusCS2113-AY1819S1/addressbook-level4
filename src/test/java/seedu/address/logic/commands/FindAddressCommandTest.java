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
import seedu.address.model.EventBook;
import seedu.address.model.ExpenseBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.AddressContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindAddressCommand}.
 */
public class FindAddressCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new ExpenseBook(),
            new EventBook(), new TaskBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new ExpenseBook(), new EventBook(),
            new TaskBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        AddressContainsKeywordsPredicate firstPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("first"));
        AddressContainsKeywordsPredicate secondPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("second"));

        FindAddressCommand findFirstAddressCommand = new FindAddressCommand(firstPredicate);
        FindAddressCommand findSecondAddressCommand = new FindAddressCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstAddressCommand.equals(findFirstAddressCommand));

        // same values -> returns true
        FindAddressCommand findFirstAddressCommandCopy = new FindAddressCommand(firstPredicate);
        assertTrue(findFirstAddressCommand.equals(findFirstAddressCommandCopy));

        // different types -> returns false
        assertFalse(findFirstAddressCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstAddressCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstAddressCommand.equals(findSecondAddressCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        AddressContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindAddressCommand command = new FindAddressCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        AddressContainsKeywordsPredicate predicate = preparePredicate("wall michegan tokyo");
        FindAddressCommand command = new FindAddressCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code AddressContainsKeywordsPredicate}.
     */
    private AddressContainsKeywordsPredicate preparePredicate(String userInput) {
        return new AddressContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
