package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecruitments.getTypicalRecruitmentList;
import static seedu.address.testutil.expenses.TypicalExpenses.getTypicalExpensesList;
import static seedu.address.testutil.schedule.TypicalSchedules.getTypicalScheduleList;

import java.util.Arrays;

import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

import seedu.address.model.ModelManager;

import seedu.address.model.UserPrefs;

import seedu.address.model.expenses.EmployeeIdExpensesContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.schedule.EmployeeIdScheduleContainsKeywordsPredicate;

/**

 * Contains integration tests (interaction with the Model) for {@code FindCommand}.

 */

public class FindCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(),
            getTypicalScheduleList(), getTypicalRecruitmentList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(),
            getTypicalScheduleList(), getTypicalRecruitmentList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();



    @Test

    public void equals() {
        String firstKeyword = "first";
        String secondKeyword = "second";

        FindCommand findFirstCommand = new FindCommand(firstKeyword);
        FindCommand findSecondCommand = new FindCommand(secondKeyword);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstKeyword);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("blank"));
        EmployeeIdScheduleContainsKeywordsPredicate schedulePredicate =
                new EmployeeIdScheduleContainsKeywordsPredicate(Collections.singletonList("blank"));
        EmployeeIdExpensesContainsKeywordsPredicate expensesPredicate =
                new EmployeeIdExpensesContainsKeywordsPredicate(Collections.singletonList("blank"));
        FindCommand command = new FindCommand("hello");
        command.setIsInputEmployeeId(false);
        command.setIsInputName(true);
        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.updateFilteredScheduleList(schedulePredicate);
        expectedModel.updateFilteredExpensesList(expensesPredicate);
        try {
            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneKeyword_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("Elle Meyer"));
        EmployeeIdScheduleContainsKeywordsPredicate schedulePredicate =
                new EmployeeIdScheduleContainsKeywordsPredicate(Collections.singletonList(ELLE.getEmployeeId().value));
        EmployeeIdExpensesContainsKeywordsPredicate expensesPredicate =
                new EmployeeIdExpensesContainsKeywordsPredicate(Collections.singletonList(ELLE.getEmployeeId().value));
        FindCommand command = new FindCommand("Elle");
        command.setIsInputEmployeeId(false);
        command.setIsInputName(true);
        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.updateFilteredScheduleList(schedulePredicate);
        expectedModel.updateFilteredExpensesList(expensesPredicate);
        try {
            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        assertEquals(Arrays.asList(ELLE), model.getFilteredPersonList());
    }
}
