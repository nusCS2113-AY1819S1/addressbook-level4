package seedu.recruit.logic.commands;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;

import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE_RANGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;

import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_REVERSE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.CASHIER_AUDI;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.CASHIER_BENTLEY;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.CASHIER_CHEVROLET;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.CASHIER_DODGE;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.MANAGER_AUDI;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.MANAGER_BENTLEY;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.MANAGER_CHEVROLET;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.MANAGER_DODGE;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.SALESPERSON_AUDI;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.SALESPERSON_BENTLEY;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.SALESPERSON_CHEVROLET;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.SALESPERSON_DODGE;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.getTypicalCompanyBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * Contains integration tests (interaction with the Model)
 * and unit tests for the SortJobOfferCommand.
 */
@SuppressWarnings("Duplicates")
public class SortJobOfferCommandTest {

    private Model model = new ModelManager(new CandidateBook(), getTypicalCompanyBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(new CandidateBook(), getTypicalCompanyBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();

    @Test
    public void execute_sortByCompanyName_success() {
        List<JobOffer> sortedList = new ArrayList<JobOffer>(Arrays.asList(
                CASHIER_AUDI, SALESPERSON_AUDI, MANAGER_AUDI, CASHIER_BENTLEY, SALESPERSON_BENTLEY, MANAGER_BENTLEY,
                CASHIER_CHEVROLET, SALESPERSON_CHEVROLET, MANAGER_CHEVROLET, CASHIER_DODGE, SALESPERSON_DODGE,
                MANAGER_DODGE));
        expectedModel.sortJobOffers(PREFIX_COMPANY_NAME);
        expectedModel.commitRecruitBook();
        SortJobOfferCommand sortJobOfferCommand = new SortJobOfferCommand(PREFIX_COMPANY_NAME);
        String expectedMessage = sortJobOfferCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCompanyJobList(), expectedModel.getFilteredCompanyJobList());
        assertEquals(expectedModel.getFilteredCompanyJobList(), sortedList);
        assertCommandSuccess(sortJobOfferCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByJob_success() {
        List<JobOffer> sortedList = new ArrayList<JobOffer>(Arrays.asList(
                CASHIER_AUDI, CASHIER_BENTLEY, CASHIER_CHEVROLET, CASHIER_DODGE, MANAGER_AUDI, MANAGER_BENTLEY,
                MANAGER_CHEVROLET, MANAGER_DODGE, SALESPERSON_AUDI, SALESPERSON_BENTLEY, SALESPERSON_CHEVROLET,
                SALESPERSON_DODGE));
        expectedModel.sortJobOffers(PREFIX_JOB);
        expectedModel.commitRecruitBook();
        SortJobOfferCommand sortJobOfferCommand = new SortJobOfferCommand(PREFIX_JOB);
        String expectedMessage = sortJobOfferCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCompanyJobList(), expectedModel.getFilteredCompanyJobList());
        assertEquals(expectedModel.getFilteredCompanyJobList(), sortedList);
        assertCommandSuccess(sortJobOfferCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByAgeRange_success() {
        List<JobOffer> sortedList = new ArrayList<JobOffer>(Arrays.asList(
                CASHIER_DODGE, CASHIER_CHEVROLET, CASHIER_BENTLEY, CASHIER_AUDI, SALESPERSON_DODGE,
                SALESPERSON_CHEVROLET, SALESPERSON_BENTLEY, SALESPERSON_AUDI, MANAGER_DODGE, MANAGER_CHEVROLET,
                MANAGER_BENTLEY, MANAGER_AUDI));
        expectedModel.sortJobOffers(PREFIX_AGE_RANGE);
        expectedModel.commitRecruitBook();
        SortJobOfferCommand sortJobOfferCommand = new SortJobOfferCommand(PREFIX_AGE_RANGE);
        String expectedMessage = sortJobOfferCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCompanyJobList(), expectedModel.getFilteredCompanyJobList());
        assertEquals(expectedModel.getFilteredCompanyJobList(), sortedList);
        assertCommandSuccess(sortJobOfferCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByEducation_success() {
        List<JobOffer> sortedList = new ArrayList<JobOffer>(Arrays.asList(
                MANAGER_AUDI, MANAGER_BENTLEY, MANAGER_CHEVROLET, MANAGER_DODGE, SALESPERSON_AUDI, SALESPERSON_BENTLEY,
                SALESPERSON_CHEVROLET, SALESPERSON_DODGE, CASHIER_AUDI, CASHIER_BENTLEY, CASHIER_CHEVROLET,
                CASHIER_DODGE));
        expectedModel.sortJobOffers(PREFIX_EDUCATION);
        expectedModel.commitRecruitBook();
        SortJobOfferCommand sortJobOfferCommand = new SortJobOfferCommand(PREFIX_EDUCATION);
        String expectedMessage = sortJobOfferCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCompanyJobList(), expectedModel.getFilteredCompanyJobList());
        assertEquals(expectedModel.getFilteredCompanyJobList(), sortedList);
        assertCommandSuccess(sortJobOfferCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortBySalary_success() {
        List<JobOffer> sortedList = new ArrayList<JobOffer>(Arrays.asList(
                CASHIER_DODGE, CASHIER_CHEVROLET, CASHIER_BENTLEY, CASHIER_AUDI, SALESPERSON_DODGE,
                SALESPERSON_CHEVROLET, SALESPERSON_BENTLEY, SALESPERSON_AUDI, MANAGER_DODGE, MANAGER_CHEVROLET,
                MANAGER_BENTLEY, MANAGER_AUDI
        ));
        expectedModel.sortJobOffers(PREFIX_SALARY);
        expectedModel.commitRecruitBook();
        SortJobOfferCommand sortJobOfferCommand = new SortJobOfferCommand(PREFIX_SALARY);
        String expectedMessage = sortJobOfferCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCompanyJobList(), expectedModel.getFilteredCompanyJobList());
        assertEquals(expectedModel.getFilteredCompanyJobList(), sortedList);
        assertCommandSuccess(sortJobOfferCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortInReverse_success() {
        List<JobOffer> sortedList = new ArrayList<JobOffer>(Arrays.asList(
                MANAGER_DODGE, MANAGER_CHEVROLET, MANAGER_BENTLEY, MANAGER_AUDI, SALESPERSON_DODGE,
                SALESPERSON_CHEVROLET, SALESPERSON_BENTLEY, SALESPERSON_AUDI, CASHIER_DODGE, CASHIER_CHEVROLET,
                CASHIER_BENTLEY, CASHIER_AUDI));
        expectedModel.sortJobOffers(PREFIX_REVERSE);
        expectedModel.commitRecruitBook();
        SortJobOfferCommand sortJobOfferCommand = new SortJobOfferCommand(PREFIX_REVERSE);
        String expectedMessage = sortJobOfferCommand.MESSAGE_SUCCESS;

        assertNotEquals(model.getFilteredCompanyJobList(), expectedModel.getFilteredCompanyJobList());
        assertEquals(expectedModel.getFilteredCompanyJobList(), sortedList);
        assertCommandSuccess(sortJobOfferCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
