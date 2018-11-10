package seedu.recruit.logic.commands;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_REVERSE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;

import org.junit.Test;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for the SortJobOfferCommand.
 */
@SuppressWarnings("Duplicates")
public class SortJobOfferCommandTest {

    private Model model = new ModelManager(new CandidateBook(), new CompanyBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(new CandidateBook(), new CompanyBook(), new UserPrefs());
    private Model sortedModel = new ModelManager(new CandidateBook(), new CompanyBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();

    @Test
    public void execute_sortByCompanyName_success() {

        expectedModel.sortJobOffers(PREFIX_COMPANY_NAME);
        expectedModel.commitRecruitBook();
        SortJobOfferCommand sortJobOfferCommand = new SortJobOfferCommand(PREFIX_COMPANY_NAME);
        String expectedMessage = sortJobOfferCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCompanyJobList(), sortedModel.getFilteredCompanyJobList());
        assertCommandSuccess(sortJobOfferCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredCompanyJobList(), sortedModel.getFilteredCompanyJobList());
    }

    @Test
    public void execute_sortByAge_success() {

        expectedModel.sortJobOffers(PREFIX_AGE);
        expectedModel.commitRecruitBook();
        SortJobOfferCommand sortJobOfferCommand = new SortJobOfferCommand(PREFIX_AGE);
        String expectedMessage = sortJobOfferCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCompanyJobList(), sortedModel.getFilteredCompanyJobList());
        assertCommandSuccess(sortJobOfferCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredCompanyJobList(), sortedModel.getFilteredCompanyJobList());
    }

    @Test
    public void execute_sortByEmail_success() {

        expectedModel.sortJobOffers(PREFIX_EMAIL);
        expectedModel.commitRecruitBook();
        SortJobOfferCommand sortJobOfferCommand = new SortJobOfferCommand(PREFIX_EMAIL);
        String expectedMessage = sortJobOfferCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCompanyJobList(), sortedModel.getFilteredCompanyJobList());
        assertCommandSuccess(sortJobOfferCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredCompanyJobList(), sortedModel.getFilteredCompanyJobList());
    }

    @Test
    public void execute_sortByJob_success() {

        expectedModel.sortJobOffers(PREFIX_JOB);
        expectedModel.commitRecruitBook();
        SortJobOfferCommand sortJobOfferCommand = new SortJobOfferCommand(PREFIX_JOB);
        String expectedMessage = sortJobOfferCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCompanyJobList(), sortedModel.getFilteredCompanyJobList());
        assertCommandSuccess(sortJobOfferCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredCompanyJobList(), sortedModel.getFilteredCompanyJobList());
    }

    @Test
    public void execute_sortByEducation_success() {

        expectedModel.sortJobOffers(PREFIX_EDUCATION);
        expectedModel.commitRecruitBook();
        SortJobOfferCommand sortJobOfferCommand = new SortJobOfferCommand(PREFIX_EDUCATION);
        String expectedMessage = sortJobOfferCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCompanyJobList(), sortedModel.getFilteredCompanyJobList());
        assertCommandSuccess(sortJobOfferCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredCompanyJobList(), sortedModel.getFilteredCompanyJobList());
    }

    @Test
    public void execute_sortBySalary_success() {

        expectedModel.sortJobOffers(PREFIX_SALARY);
        expectedModel.commitRecruitBook();
        SortJobOfferCommand sortJobOfferCommand = new SortJobOfferCommand(PREFIX_SALARY);
        String expectedMessage = sortJobOfferCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCompanyJobList(), sortedModel.getFilteredCompanyJobList());
        assertCommandSuccess(sortJobOfferCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredCompanyJobList(), sortedModel.getFilteredCompanyJobList());
    }

    @Test
    public void execute_sortInReverse_success() {

        expectedModel.sortJobOffers(PREFIX_REVERSE);
        expectedModel.commitRecruitBook();
        SortJobOfferCommand sortJobOfferCommand = new SortJobOfferCommand(PREFIX_REVERSE);
        String expectedMessage = sortJobOfferCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCompanyJobList(), sortedModel.getFilteredCompanyJobList());
        assertCommandSuccess(sortJobOfferCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredCompanyJobList(), sortedModel.getFilteredCompanyJobList());
    }

}
