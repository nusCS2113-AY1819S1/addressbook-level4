package seedu.recruit.logic.commands;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_REVERSE;
import static seedu.recruit.testutil.TypicalCompanies.getReversedCompanyBook;
import static seedu.recruit.testutil.TypicalCompanies.getTypicalCompanyBook;

import org.junit.Test;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for the SortCompanyCommand.
 */
@SuppressWarnings("Duplicates")
public class SortCompanyCommandTest {

    private Model model = new ModelManager(new CandidateBook(), getReversedCompanyBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(new CandidateBook(), getReversedCompanyBook(), new UserPrefs());
    private Model sortedModel = new ModelManager(new CandidateBook(), getTypicalCompanyBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();

    @Test
    public void execute_sortByCompanyName_success() {

        expectedModel.sortCompanies(PREFIX_COMPANY_NAME);
        expectedModel.commitRecruitBook();
        SortCompanyCommand sortCompanyCommand = new SortCompanyCommand(PREFIX_COMPANY_NAME);
        String expectedMessage = sortCompanyCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCompanyList(), sortedModel.getFilteredCompanyList());
        assertCommandSuccess(sortCompanyCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredCompanyList(), sortedModel.getFilteredCompanyList());
    }

    @Test
    public void execute_sortByEmail_success() {

        expectedModel.sortCompanies(PREFIX_EMAIL);
        expectedModel.commitRecruitBook();
        SortCompanyCommand sortCompanyCommand = new SortCompanyCommand(PREFIX_EMAIL);
        String expectedMessage = sortCompanyCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCompanyList(), sortedModel.getFilteredCompanyList());
        assertCommandSuccess(sortCompanyCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredCompanyList(), sortedModel.getFilteredCompanyList());
    }

    @Test
    public void execute_sortInReverse_success() {

        expectedModel.sortCompanies(PREFIX_REVERSE);
        expectedModel.commitRecruitBook();
        SortCompanyCommand sortCompanyCommand = new SortCompanyCommand(PREFIX_REVERSE);
        String expectedMessage = sortCompanyCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCompanyList(), sortedModel.getFilteredCompanyList());
        assertCommandSuccess(sortCompanyCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredCompanyList(), sortedModel.getFilteredCompanyList());
    }

}
