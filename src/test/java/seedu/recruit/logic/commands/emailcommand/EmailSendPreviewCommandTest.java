package seedu.recruit.logic.commands.emailcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.recruit.logic.commands.emailcommand.EmailSendCommand.EMAIL_SEND_SHOWING_PREVIEW_MESSAGE;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.AUDI;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.BENTLEY;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.CASHIER_AUDI;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.SALESPERSON_AUDI;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.SALESPERSON_BENTLEY;
import static seedu.recruit.testutil.TypicalCompaniesAndJobOffers.getTypicalCompanyBookWithoutJobOffers;
import static seedu.recruit.testutil.TypicalPersons.ALICE;
import static seedu.recruit.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.testutil.CandidateBuilder;
import seedu.recruit.testutil.CompanyBuilder;
import seedu.recruit.testutil.JobOfferBuilder;

public class EmailSendPreviewCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalCompanyBookWithoutJobOffers(), userPrefs);
    private EmailSendPreviewCommand command = new EmailSendPreviewCommand();

    private Candidate testCandidate = new CandidateBuilder(ALICE).build();
    private JobOffer testJobOffer = new JobOfferBuilder(CASHIER_AUDI).build();
    private Company testCompany = new CompanyBuilder(AUDI).build();

    @Before
    public void setUp() {
        model.resetEmailUtil();
        command = new EmailSendPreviewCommand();
    }

    @Test
    public void execute_emailSendPreviewTestCommandExecute() {
        assertEquals(EMAIL_SEND_SHOWING_PREVIEW_MESSAGE
                + EmailSendCommand.MESSAGE_USAGE, command.execute(model, commandHistory, userPrefs).feedbackToUser);
    }

    @Test
    public void execute_emailSendPreviewCandidateRecipients() {
        model.getEmailUtil().setAreRecipientsCandidates(true);
        model.getEmailUtil().addCandidate(testCandidate);
        model.getEmailUtil().addJobOffer(testJobOffer);
        command.execute(model, commandHistory, userPrefs);

        String expectedEmailPreview = "To: [" + testCandidate.getEmail().toString() + "]\n"
                + "Subject: " + model.getEmailUtil().getEmailSettings().getSubjectCandidateAsRecipient()
                + "\nContents of the email:\n\n"
                + model.getEmailUtil().getEmailSettings().getBodyTextCandidateAsRecipient()
                + "\nCompany: " + testJobOffer.getCompanyName().toString()
                + "\nJob: " + testJobOffer.getJob().toString()
                + "\nSalary offered: " + testJobOffer.getSalary().toString() + '\n';
        assertEquals(expectedEmailPreview, command.getEmailPreviewToString());
    }

    @Test
    public void execute_emailSendPreviewCompanyRecipient() {
        model.getEmailUtil().setAreRecipientsCandidates(false);
        model.getEmailUtil().addCandidate(testCandidate);
        model.getEmailUtil().addJobOffer(testJobOffer);
        command.execute(model, commandHistory, userPrefs);

        String expectedEmailPreview = "To: "
                + testCompany.getCompanyName().toString()
                + '(' + testCompany.getEmail().toString() + ')'
                + " regarding job offers: "
                + '[' + testJobOffer.getJob().toString() + "]\n"
                + "Subject: " + model.getEmailUtil().getEmailSettings().getSubjectCompanyAsRecipient()
                + "\nContents of the email:\n\n"
                + model.getEmailUtil().getEmailSettings().getBodyTextCompanyAsRecipient() + '\n'
                + "\nName: " + testCandidate.getName().toString()
                + "\nAge: " + testCandidate.getAge().toString()
                + "\nEducation: " + testCandidate.getEducation().toString()
                + "\nEmail: " + testCandidate.getEmail().toString()
                + "\nPhone Number: " + testCandidate.getPhone().toString()
                + '\n';

        assertEquals(expectedEmailPreview, command.getEmailPreviewToString());
    }

    @Test
    public void execute_emailSendPreviewCompanyRecipientTwoSameCompanyJobOffers() {
        model.getEmailUtil().setAreRecipientsCandidates(false);
        model.getEmailUtil().addCandidate(testCandidate);
        model.getEmailUtil().addJobOffer(testJobOffer);
        JobOffer secondJobOffer = new JobOfferBuilder(SALESPERSON_AUDI).build();
        model.getEmailUtil().addJobOffer(secondJobOffer);
        command.execute(model, commandHistory, userPrefs);

        String expectedEmailPreview = "To: "
                + testCompany.getCompanyName().toString()
                + '(' + testCompany.getEmail().toString() + ')'
                + " regarding job offers: "
                + '[' + testJobOffer.getJob().toString() + ", " + secondJobOffer.getJob().toString() + "]\n"
                + "Subject: " + model.getEmailUtil().getEmailSettings().getSubjectCompanyAsRecipient()
                + "\nContents of the email:\n\n"
                + model.getEmailUtil().getEmailSettings().getBodyTextCompanyAsRecipient() + '\n'
                + "\nName: " + testCandidate.getName().toString()
                + "\nAge: " + testCandidate.getAge().toString()
                + "\nEducation: " + testCandidate.getEducation().toString()
                + "\nEmail: " + testCandidate.getEmail().toString()
                + "\nPhone Number: " + testCandidate.getPhone().toString()
                + '\n';

        assertEquals(expectedEmailPreview, command.getEmailPreviewToString());
    }


    @Test
    public void execute_emailSendPreviewCompanyRecipientTwoDifferentCompanyJobOffers() {
        model.getEmailUtil().setAreRecipientsCandidates(false);
        model.getEmailUtil().addCandidate(testCandidate);
        model.getEmailUtil().addJobOffer(testJobOffer);
        JobOffer secondJobOffer = new JobOfferBuilder(SALESPERSON_BENTLEY).build();
        Company secondCompany = new CompanyBuilder(BENTLEY).build();
        model.getEmailUtil().addJobOffer(secondJobOffer);
        command.execute(model, commandHistory, userPrefs);

        String expectedEmailPreview = "To: "
                + testCompany.getCompanyName().toString()
                + '(' + testCompany.getEmail().toString() + ')'
                + " regarding job offers: "
                + '[' + testJobOffer.getJob().toString() + "]\n"
                + "To: "
                + secondCompany.getCompanyName().toString()
                + '(' + secondCompany.getEmail().toString() + ')'
                + " regarding job offers: "
                + '[' + secondJobOffer.getJob().toString() + "]\n"
                + "Subject: " + model.getEmailUtil().getEmailSettings().getSubjectCompanyAsRecipient()
                + "\nContents of the email:\n\n"
                + model.getEmailUtil().getEmailSettings().getBodyTextCompanyAsRecipient() + '\n'
                + "\nName: " + testCandidate.getName().toString()
                + "\nAge: " + testCandidate.getAge().toString()
                + "\nEducation: " + testCandidate.getEducation().toString()
                + "\nEmail: " + testCandidate.getEmail().toString()
                + "\nPhone Number: " + testCandidate.getPhone().toString()
                + '\n';

        assertEquals(expectedEmailPreview, command.getEmailPreviewToString());
    }
}
