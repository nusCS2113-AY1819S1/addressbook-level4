package seedu.recruit.logic.commands;

import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.testutil.TypicalCompanies.getTypicalCompanyBook;
import static seedu.recruit.testutil.TypicalCompanies.resetTypicalCompaniesJobList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.company.CompanyName;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.testutil.CompanyBuilder;
import seedu.recruit.testutil.JobOfferBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddJobDetailsCommand}.
 */

public class AddJobDetailsCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(new CandidateBook(), getTypicalCompanyBook(), new UserPrefs());
    }

    @After
    public void cleanUp() {
        resetTypicalCompaniesJobList();
    }

    @Test
    public void execute_newJobOfferWithExistingCompany_success() {
        JobOffer jobOffer = new JobOfferBuilder()
                .withCompanyName("Dummy").build();
        model.addCompany(new CompanyBuilder().withCompanyName("Dummy").build());

        Model expectedModel = new ModelManager(new CandidateBook(), getTypicalCompanyBook(), new UserPrefs());
        expectedModel.addCompany(new CompanyBuilder().withCompanyName("Dummy").build());
        expectedModel.addJobOffer(new CompanyName("Dummy"), jobOffer);
        expectedModel.commitCompanyBook();

        assertCommandSuccess(new AddJobDetailsCommand(jobOffer), model, commandHistory,
                String.format(AddJobDetailsCommand.MESSAGE_SUCCESS, jobOffer), expectedModel);
    }

    @Test
    public void execute_duplicateJobOffer_throwsCommandException() {
        JobOffer jobOffer = new JobOfferBuilder().withCompanyName(model.getCompanyFromIndex(0)
                .getCompanyName().toString()).build();
        model.addJobOffer(model.getCompanyFromIndex(0).getCompanyName(), jobOffer);

        assertCommandFailure(new AddJobDetailsCommand(jobOffer), model, commandHistory,
                AddJobDetailsCommand.MESSAGE_DUPLICATE_JOB_OFFER);
    }

    @Test
    public void execute_jobOfferInvalidCompany_throwsCompanyNotFoundException() {
        //Empty CompanyBook
        model = new ModelManager(new CandidateBook(), new CompanyBook(), new UserPrefs());

        JobOffer jobOffer = new JobOfferBuilder().build();

        assertCommandFailure(new AddJobDetailsCommand(jobOffer), model, commandHistory,
                AddJobDetailsCommand.MESSAGE_COMPANY_NOT_FOUND);
    }
}
