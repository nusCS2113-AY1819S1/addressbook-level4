package seedu.recruit.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.company.CompanyName;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.testutil.CompanyBuilder;
import seedu.recruit.testutil.JobOfferBuilder;



public class AddJobDetailsCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullJobOffer_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddJobDetailsCommand(null);
    }

    @Test
    public void execute_jobOfferInvalidCompany_throwsCompanyNotFoundException() throws Exception {
        ModelStubEmptyCompanyBook modelStub = new ModelStubEmptyCompanyBook();
        JobOffer jobOffer = new JobOfferBuilder().build();
        AddJobDetailsCommand addJobDetailsCommand = new AddJobDetailsCommand(jobOffer);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddJobDetailsCommand.MESSAGE_COMPANY_NOT_FOUND);
        addJobDetailsCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_jobOfferAcceptedByModel_addSuccessful () throws Exception {
        JobOffer jobOffer = new JobOfferBuilder().build();
        ModelStubWithCompany modelStub = new ModelStubWithCompany(new CompanyBuilder()
                .withCompanyName(jobOffer.getCompanyName().toString()).build(), new ArrayList<>());

        CommandResult commandResult = new AddJobDetailsCommand(jobOffer).execute(modelStub, commandHistory);

        Company expectedCompany = new CompanyBuilder().withCompanyName(jobOffer.getCompanyName().toString()).build();

        assertEquals(String.format(AddJobDetailsCommand.MESSAGE_SUCCESS, jobOffer), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(expectedCompany), modelStub.companyList);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateJobOffer_throwsDuplicateJobOfferException () throws Exception {
        JobOffer jobOffer = new JobOfferBuilder().build();
        ArrayList<JobOffer> jobList = new ArrayList<JobOffer>();
        jobList.add(jobOffer);
        Company company = new CompanyBuilder().withCompanyName(jobOffer.getCompanyName().toString()).build();
        ModelStubWithCompany modelStub = new ModelStubWithCompany(company, jobList);

        AddJobDetailsCommand addJobDetailsCommand = new AddJobDetailsCommand(jobOffer);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddJobDetailsCommand.MESSAGE_DUPLICATE_JOB_OFFER);
        addJobDetailsCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        JobOffer macs = new JobOfferBuilder().withCompanyName("macs").build();
        JobOffer kfc = new JobOfferBuilder().withCompanyName("kfc").build();
        AddJobDetailsCommand addMacsCommand = new AddJobDetailsCommand(macs);
        AddJobDetailsCommand addKfcCommand = new AddJobDetailsCommand(kfc);

        // same object -> returns true
        assertTrue(addMacsCommand.equals(addMacsCommand));

        // same values -> returns true
        AddJobDetailsCommand addMacsCommandCopy = new AddJobDetailsCommand(macs);
        assertTrue(addMacsCommand.equals(addMacsCommandCopy));

        // different types -> returns false
        assertFalse(addMacsCommand.equals(1));

        // null -> returns false
        assertFalse(addMacsCommand.equals(null));

        // different candidate -> returns false
        assertFalse(addMacsCommand.equals(addKfcCommand));
    }


    /**
     * A Model stub with an empty CompanyBook
     */

    private class ModelStubEmptyCompanyBook extends CommandTestUtil.ModelStub {
        private final ArrayList<JobOffer> companyJobList = new ArrayList<JobOffer>();
        private final ArrayList<Company> companyList = new ArrayList<Company>();


        @Override
        public void addJobOffer(JobOffer jobOffer) {
            companyJobList.add(jobOffer);
        }

        @Override
        public void commitCompanyBook() {
            // called by {@code AddJobDetailsCommand#execute()}
        }

        @Override
        public boolean hasJobOffer(JobOffer jobOffer) {
            return companyJobList.contains(jobOffer);
        }

        @Override
        public int getCompanyIndexFromName(CompanyName companyName) {
            for (int index = 0; index < companyList.size(); index++) {
                if (companyList.get(index).getCompanyName().equals(companyName)) {
                    return index;
                }
            }
            return -1;
        }



    }

    /**
     * A Model stub which contains a Company
     */

    private class ModelStubWithCompany extends CommandTestUtil.ModelStub {
        private final ArrayList<Company> companyList = new ArrayList<Company>();
        private final ArrayList<JobOffer> companyJobList = new ArrayList<JobOffer>();

        ModelStubWithCompany(Company company, List<JobOffer> jobList) {
            companyList.add(company);
            companyJobList.addAll(jobList);
        }

        @Override
        public boolean hasJobOffer(JobOffer jobOffer) {
            return companyJobList.contains(jobOffer);
        }

        @Override
        public int getCompanyIndexFromName(CompanyName companyName) {
            for (int index = 0; index < companyList.size(); index++) {
                if (companyList.get(index).getCompanyName().equals(companyName)) {
                    return index;
                }
            }
            return -1;
        }

        @Override
        public Company getCompanyFromIndex(int index) {
            return companyList.get(index);
        }

        @Override
        public void addJobOffer(JobOffer jobOffer) {
            companyJobList.add(jobOffer);
        }

        @Override
        public void commitCompanyBook() {
            // called by {@code AddJobDetailsCommand#execute()}
        }

    }


}
