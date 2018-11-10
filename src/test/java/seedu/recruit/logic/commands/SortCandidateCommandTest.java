package seedu.recruit.logic.commands;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_REVERSE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.recruit.testutil.TypicalPersons.getReversedAddressBook;
import static seedu.recruit.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for the SortCandidateCommand.
 */
@SuppressWarnings("Duplicates")
public class SortCandidateCommandTest {

    private Model model = new ModelManager(getReversedAddressBook(), new CompanyBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getReversedAddressBook(), new CompanyBook(), new UserPrefs());
    private Model sortedModel = new ModelManager(getTypicalAddressBook(), new CompanyBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();

    @Test
    public void execute_sortByName_success() {

        expectedModel.sortCandidates(PREFIX_NAME);
        expectedModel.commitRecruitBook();
        SortCandidateCommand sortCandidateCommand = new SortCandidateCommand(PREFIX_NAME);
        String expectedMessage = sortCandidateCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCandidateList(), sortedModel.getFilteredCandidateList());
        assertCommandSuccess(sortCandidateCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredCandidateList(), sortedModel.getFilteredCandidateList());
    }

    @Test
    public void execute_sortByAge_success() {

        expectedModel.sortCandidates(PREFIX_AGE);
        expectedModel.commitRecruitBook();
        SortCandidateCommand sortCandidateCommand = new SortCandidateCommand(PREFIX_AGE);
        String expectedMessage = sortCandidateCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCandidateList(), sortedModel.getFilteredCandidateList());
        assertCommandSuccess(sortCandidateCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredCandidateList(), sortedModel.getFilteredCandidateList());
    }

    @Test
    public void execute_sortByEmail_success() {

        expectedModel.sortCandidates(PREFIX_EMAIL);
        expectedModel.commitRecruitBook();
        SortCandidateCommand sortCandidateCommand = new SortCandidateCommand(PREFIX_EMAIL);
        String expectedMessage = sortCandidateCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCandidateList(), sortedModel.getFilteredCandidateList());
        assertCommandSuccess(sortCandidateCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredCandidateList(), sortedModel.getFilteredCandidateList());
    }

    @Test
    public void execute_sortByJob_success() {

        expectedModel.sortCandidates(PREFIX_JOB);
        expectedModel.commitRecruitBook();
        SortCandidateCommand sortCandidateCommand = new SortCandidateCommand(PREFIX_JOB);
        String expectedMessage = sortCandidateCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCandidateList(), sortedModel.getFilteredCandidateList());
        assertCommandSuccess(sortCandidateCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredCandidateList(), sortedModel.getFilteredCandidateList());
    }

    @Test
    public void execute_sortByEducation_success() {

        expectedModel.sortCandidates(PREFIX_EDUCATION);
        expectedModel.commitRecruitBook();
        SortCandidateCommand sortCandidateCommand = new SortCandidateCommand(PREFIX_EDUCATION);
        String expectedMessage = sortCandidateCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCandidateList(), sortedModel.getFilteredCandidateList());
        assertCommandSuccess(sortCandidateCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredCandidateList(), sortedModel.getFilteredCandidateList());
    }

    @Test
    public void execute_sortBySalary_success() {

        expectedModel.sortCandidates(PREFIX_SALARY);
        expectedModel.commitRecruitBook();
        SortCandidateCommand sortCandidateCommand = new SortCandidateCommand(PREFIX_SALARY);
        String expectedMessage = sortCandidateCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCandidateList(), sortedModel.getFilteredCandidateList());
        assertCommandSuccess(sortCandidateCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredCandidateList(), sortedModel.getFilteredCandidateList());
    }

    @Test
    public void execute_sortInReverse_success() {

        expectedModel.sortCandidates(PREFIX_REVERSE);
        expectedModel.commitRecruitBook();
        SortCandidateCommand sortCandidateCommand = new SortCandidateCommand(PREFIX_REVERSE);
        String expectedMessage = sortCandidateCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCandidateList(), sortedModel.getFilteredCandidateList());
        assertCommandSuccess(sortCandidateCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredCandidateList(), sortedModel.getFilteredCandidateList());
    }

}
