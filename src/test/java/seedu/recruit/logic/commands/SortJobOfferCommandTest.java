package seedu.recruit.logic.commands;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;

import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;

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
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for the SortJobOfferCommand.
 */
@SuppressWarnings("Duplicates")
public class SortJobOfferCommandTest {

    private Model model = new ModelManager(new CandidateBook(), getTypicalCompanyBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(new CandidateBook(),getTypicalCompanyBook(), new UserPrefs());
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

}
