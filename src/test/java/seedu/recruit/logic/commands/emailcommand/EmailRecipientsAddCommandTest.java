package seedu.recruit.logic.commands.emailcommand;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.recruit.logic.commands.emailcommand.EmailRecipientsCommand.ADD_RECIPIENTS_DUPLICATE_MESSAGE;
import static seedu.recruit.logic.commands.emailcommand.EmailRecipientsCommand.ADD_RECIPIENTS_NOTHING_SELECTED;
import static seedu.recruit.logic.commands.emailcommand.EmailRecipientsCommand.ADD_RECIPIENTS_RECIPIENTS_ADDED;
import static seedu.recruit.logic.commands.emailcommand.EmailRecipientsCommand.MESSAGE_USAGE;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.AUDI;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.BENTLEY;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.CASHIER_AUDI;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.CASHIER_BENTLEY;
import static seedu.recruit.testutil.TypicalPersons.AMY;
import static seedu.recruit.testutil.TypicalPersons.GEORGE;

import org.junit.Ignore;
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
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.testutil.CandidateBuilder;
import seedu.recruit.testutil.CompanyBuilder;
import seedu.recruit.testutil.JobOfferBuilder;

@Ignore
public class EmailRecipientsAddCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();
    private EmailRecipientsAddCommand command = new EmailRecipientsAddCommand();

    private Candidate testCandidate1 = new CandidateBuilder(GEORGE).build();
    private Candidate testCandidate2 = new CandidateBuilder(AMY).build();
    private Company testCompany1 = new CompanyBuilder(AUDI).build();
    private Company testCompany2 = new CompanyBuilder(BENTLEY).build();
    private JobOffer testJobOffer1 = new JobOfferBuilder(CASHIER_AUDI).build();
    private JobOffer testJobOffer2 = new JobOfferBuilder(CASHIER_BENTLEY).build();

    @Test
    public void execute_emailRecipientsAddNoRecipients() {
        Model model = new ModelManager();
        CommandResult result = command.execute(model, commandHistory, userPrefs);
        assertEquals(ADD_RECIPIENTS_NOTHING_SELECTED + MESSAGE_USAGE, result.feedbackToUser);
        assertFalse(model.getEmailUtil().getHasRecipientsAdded());
    }

    @Test
    public void execute_emailRecipientsAddCandidatesFirstTime() {
        CandidateBook cb = new CandidateBook();
        cb.addCandidate(testCandidate1);
        Model model = new ModelManager(cb, new CompanyBook(), userPrefs);
        CommandResult result = command.execute(model, commandHistory, userPrefs);
        String expectedResult = ADD_RECIPIENTS_RECIPIENTS_ADDED + testCandidate1.getName().toString() + '\n';
        assertEquals(expectedResult + MESSAGE_USAGE, result.feedbackToUser);
        assertTrue(model.getEmailUtil().getHasRecipientsAdded());
        assertTrue(model.getEmailUtil().getAreRecipientsCandidates());
    }

    @Test
    public void execute_emailRecipientsAddDuplicateCandidateOnly() {
        CandidateBook cb = new CandidateBook();
        cb.addCandidate(testCandidate1);
        Model model = new ModelManager(cb, new CompanyBook(), userPrefs);
        model.getEmailUtil().addCandidate(testCandidate1);
        model.getEmailUtil().setAreRecipientsCandidates(true);
        model.getEmailUtil().setHasRecipientsAdded(true);
        CommandResult result = command.execute(model, commandHistory, userPrefs);
        String expectedResult = ADD_RECIPIENTS_DUPLICATE_MESSAGE + testCandidate1.getName().toString() + '\n';
        assertEquals(expectedResult + MESSAGE_USAGE, result.feedbackToUser);
    }

    @Test
    public void execute_emailRecipientsAddDuplicateCandidateWithAddedCandidate() {
        CandidateBook cb = new CandidateBook();
        cb.addCandidate(testCandidate1);
        cb.addCandidate(testCandidate2);
        Model model = new ModelManager(cb, new CompanyBook(), userPrefs);
        model.getEmailUtil().addCandidate(testCandidate1);
        model.getEmailUtil().setAreRecipientsCandidates(true);
        model.getEmailUtil().setHasRecipientsAdded(true);
        CommandResult result = command.execute(model, commandHistory, userPrefs);
        String expectedResult = ADD_RECIPIENTS_DUPLICATE_MESSAGE + testCandidate1.getName().toString() + '\n'
                + ADD_RECIPIENTS_RECIPIENTS_ADDED + testCandidate2.getName().toString() + '\n';
        assertEquals(expectedResult + MESSAGE_USAGE, result.feedbackToUser);
    }
}
