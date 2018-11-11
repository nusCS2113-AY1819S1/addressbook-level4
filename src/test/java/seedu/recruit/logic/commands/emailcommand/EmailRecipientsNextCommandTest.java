package seedu.recruit.logic.commands.emailcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.recruit.logic.commands.emailcommand.EmailRecipientsCommand.NEXT_RECIPIENTS_ERROR_NO_RECIPIENTS;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.testutil.CandidateBuilder;
import seedu.recruit.testutil.JobOfferBuilder;

class EmailRecipientsNextCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();
    private JobOfferBuilder jobOfferBuilder = new JobOfferBuilder();
    private CandidateBuilder candidateBuilder = new CandidateBuilder();
    private EmailRecipientsNextCommand emailRecipientsNextCommand = new EmailRecipientsNextCommand();

    @Test
    void execute_emailRecipientNextCommand_withJobOffer() {
        Model model = new ModelManager();
        model.getEmailUtil().addJobOffer(jobOfferBuilder.build());
        model.getEmailUtil().setAreRecipientsCandidates(false);
        CommandResult commandResult = emailRecipientsNextCommand.execute(model, commandHistory, userPrefs);
        assertEquals(EmailContentsCommand.MESSAGE_USAGE, commandResult.feedbackToUser);
    }

    @Test
    void execute_emailRecipientNextCommand_withCandidate() {
        Model model = new ModelManager();
        model.getEmailUtil().addCandidate(candidateBuilder.build());
        model.getEmailUtil().setAreRecipientsCandidates(true);
        CommandResult commandResult = emailRecipientsNextCommand.execute(model, commandHistory, userPrefs);
        assertEquals(EmailContentsCommand.MESSAGE_USAGE, commandResult.feedbackToUser);
    }

    @Test
    void execute_emailRecipientNextCommand_empty() {
        Model model = new ModelManager();
        model.resetEmailUtil();
        CommandResult commandResult = emailRecipientsNextCommand.execute(model, commandHistory, userPrefs);
        assertEquals(NEXT_RECIPIENTS_ERROR_NO_RECIPIENTS + EmailRecipientsCommand.MESSAGE_USAGE,
                commandResult.feedbackToUser);
    }
}
