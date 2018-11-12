package seedu.recruit.logic.commands.emailcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.recruit.logic.commands.emailcommand.EmailContentsCommand.ADD_CONTENTS_CONTENTS_ADDED;
import static seedu.recruit.logic.commands.emailcommand.EmailContentsCommand.ADD_CONTENTS_DUPLICATE_MESSAGE;
import static seedu.recruit.logic.commands.emailcommand.EmailContentsCommand.ADD_CONTENTS_NOTHING_SELECTED;
import static seedu.recruit.logic.commands.emailcommand.EmailContentsCommand.MESSAGE_USAGE;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.CASHIER_AUDI;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.CASHIER_BENTLEY;
import static seedu.recruit.testutil.TypicalPersons.AMY;
import static seedu.recruit.testutil.TypicalPersons.GEORGE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.testutil.CandidateBuilder;
import seedu.recruit.testutil.JobOfferBuilder;

public class EmailContentsAddCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();
    private EmailContentsAddCommand command = new EmailContentsAddCommand();

    private Candidate testCandidate1 = new CandidateBuilder(GEORGE).build();
    private Candidate testCandidate2 = new CandidateBuilder(AMY).build();
    private JobOffer testJobOffer1 = new JobOfferBuilder(CASHIER_AUDI).build();
    private JobOffer testJobOffer2 = new JobOfferBuilder(CASHIER_BENTLEY).build();

    @Test
    public void execute_emailContentsAddNoContents() {
        Model model = new ModelManager();
        CommandResult result = command.execute(model, commandHistory, userPrefs);
        assertEquals(ADD_CONTENTS_NOTHING_SELECTED + MESSAGE_USAGE, result.feedbackToUser);
        assertFalse(model.getEmailUtil().getHasRecipientsAdded());
    }

    @Test
    public void execute_emailContentsAddAddCandidates() {
        CandidateBook cb = new CandidateBook();
        cb.addCandidate(testCandidate1);
        cb.addCandidate(testCandidate2);
        Model model = new ModelManager(cb, new CompanyBook(), userPrefs);
        model.getEmailUtil().setAreRecipientsCandidates(false);
        int size = model.getEmailUtil().getCandidates().size();

        CommandResult result = command.execute(model, commandHistory, userPrefs);
        String expectedResult = ADD_CONTENTS_CONTENTS_ADDED + testCandidate1.getName().toString()
                + '\n' + testCandidate2.getName().toString() + '\n';
        assertEquals(expectedResult + EmailContentsCommand.MESSAGE_USAGE, result.feedbackToUser);
        assertEquals(size + 2, model.getEmailUtil().getCandidates().size());
    }

    @Test
    public void execute_emailContentsAddDuplicateCandidateOnly() {
        CandidateBook cb = new CandidateBook();
        cb.addCandidate(testCandidate1);
        Model model = new ModelManager(cb, new CompanyBook(), userPrefs);
        model.getEmailUtil().setAreRecipientsCandidates(false);
        model.getEmailUtil().addCandidate(testCandidate1);
        int size = model.getEmailUtil().getCandidates().size();

        CommandResult result = command.execute(model, commandHistory, userPrefs);
        String expectedResult = ADD_CONTENTS_DUPLICATE_MESSAGE + testCandidate1.getName().toString() + '\n';
        assertEquals(expectedResult + EmailContentsCommand.MESSAGE_USAGE, result.feedbackToUser);
        assertEquals(size, model.getEmailUtil().getCandidates().size());
    }

    @Test
    public void execute_emailContentsAddDuplicateCandidateWithAddedCandidate() {
        CandidateBook cb = new CandidateBook();
        cb.addCandidate(testCandidate1);
        cb.addCandidate(testCandidate2);
        Model model = new ModelManager(cb, new CompanyBook(), userPrefs);
        model.getEmailUtil().setAreRecipientsCandidates(false);
        model.getEmailUtil().addCandidate(testCandidate1);
        int size = model.getEmailUtil().getCandidates().size();

        CommandResult result = command.execute(model, commandHistory, userPrefs);
        String expectedResult = ADD_CONTENTS_DUPLICATE_MESSAGE + testCandidate1.getName().toString() + '\n'
                + ADD_CONTENTS_CONTENTS_ADDED + testCandidate2.getName().toString() + '\n';
        assertEquals(expectedResult + EmailContentsCommand.MESSAGE_USAGE, result.feedbackToUser);
        assertEquals(size + 1, model.getEmailUtil().getCandidates().size());
    }

    @Test
    public void execute_emailContentsAddAddJobOffer() {
        CompanyBook cb = new CompanyBook();
        cb.addJobOffer(testJobOffer1);
        cb.addJobOffer(testJobOffer2);
        Model model = new ModelManager(new CandidateBook(), cb, userPrefs);
        model.getEmailUtil().setAreRecipientsCandidates(true);
        int size = model.getEmailUtil().getJobOffers().size();

        CommandResult result = command.execute(model, commandHistory, userPrefs);
        String expectedResult = ADD_CONTENTS_CONTENTS_ADDED + model.getEmailUtil().getContentJobOfferName(testJobOffer1)
                + '\n' + model.getEmailUtil().getContentJobOfferName(testJobOffer2) + '\n';
        assertEquals(expectedResult + EmailContentsCommand.MESSAGE_USAGE, result.feedbackToUser);
        assertEquals(size + 2, model.getEmailUtil().getJobOffers().size());
    }

    @Test
    public void execute_emailContentsAddDuplicateJobOfferOnly() {
        CompanyBook cb = new CompanyBook();
        cb.addJobOffer(testJobOffer1);
        Model model = new ModelManager(new CandidateBook(), cb, userPrefs);
        model.getEmailUtil().setAreRecipientsCandidates(true);
        model.getEmailUtil().addJobOffer(testJobOffer1);
        int size = model.getEmailUtil().getJobOffers().size();

        CommandResult result = command.execute(model, commandHistory, userPrefs);
        String expectedResult = ADD_CONTENTS_DUPLICATE_MESSAGE
                + model.getEmailUtil().getContentJobOfferName(testJobOffer1) + '\n';
        assertEquals(expectedResult + EmailContentsCommand.MESSAGE_USAGE, result.feedbackToUser);
        assertEquals(size, model.getEmailUtil().getJobOffers().size());
    }

    @Test
    public void execute_emailContentsAddDuplicateJobOfferWithAddedJobOffer() {
        CompanyBook cb = new CompanyBook();
        cb.addJobOffer(testJobOffer1);
        cb.addJobOffer(testJobOffer2);
        Model model = new ModelManager(new CandidateBook(), cb, userPrefs);
        model.getEmailUtil().setAreRecipientsCandidates(true);
        model.getEmailUtil().addJobOffer(testJobOffer1);
        int size = model.getEmailUtil().getJobOffers().size();

        CommandResult result = command.execute(model, commandHistory, userPrefs);
        String expectedResult = ADD_CONTENTS_DUPLICATE_MESSAGE
                + model.getEmailUtil().getContentJobOfferName(testJobOffer1) + '\n'
                + ADD_CONTENTS_CONTENTS_ADDED + model.getEmailUtil().getContentJobOfferName(testJobOffer2) + '\n';
        assertEquals(expectedResult + EmailContentsCommand.MESSAGE_USAGE, result.feedbackToUser);
        assertEquals(size + 1, model.getEmailUtil().getJobOffers().size());
    }
}
