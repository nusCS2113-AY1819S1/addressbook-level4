package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;

//@@author Woonhian
/**
 * Contains integration tests (interaction with the Model) for {@code FilterDepartmentCommand}.
 */
public class FilterDepartmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        DepartmentContainsKeywordsPredicate firstPredicate =
                new DepartmentContainsKeywordsPredicate(Collections.singletonList("first"));
        DepartmentContainsKeywordsPredicate secondPredicate =
                new DepartmentContainsKeywordsPredicate(Collections.singletonList("second"));

        FilterDepartmentCommand filterDepartmentFirstCommand = new FilterDepartmentCommand(firstPredicate);
        FilterDepartmentCommand filterDepartmentSecondCommand = new FilterDepartmentCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterDepartmentFirstCommand.equals(filterDepartmentFirstCommand));

        // same values -> returns true
        FilterDepartmentCommand filterDepartmentFirstCommandCopy = new FilterDepartmentCommand(firstPredicate);
        assertTrue(filterDepartmentFirstCommand.equals(filterDepartmentFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterDepartmentFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterDepartmentFirstCommand.equals(null));

        // different department -> returns false
        assertFalse(filterDepartmentFirstCommand.equals(filterDepartmentSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noDepartmentFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        DepartmentContainsKeywordsPredicate predicate = preparePredicate(" ");
        FilterDepartmentCommand command = new FilterDepartmentCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multipleDepartmentsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        DepartmentContainsKeywordsPredicate predicate = preparePredicate("Junior Senior");
        FilterDepartmentCommand command = new FilterDepartmentCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private DepartmentContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DepartmentContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
