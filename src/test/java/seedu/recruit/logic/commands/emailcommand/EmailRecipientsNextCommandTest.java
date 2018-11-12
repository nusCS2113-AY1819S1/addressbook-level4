package seedu.recruit.logic.commands.emailcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.recruit.logic.commands.emailcommand.EmailRecipientsCommand.NEXT_RECIPIENTS_ERROR_NO_RECIPIENTS;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.LogicState;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.testutil.CandidateBuilder;
import seedu.recruit.testutil.JobOfferBuilder;

public class EmailRecipientsNextCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();
    private JobOfferBuilder jobOfferBuilder = new JobOfferBuilder();
    private CandidateBuilder candidateBuilder = new CandidateBuilder();
    private EmailRecipientsNextCommand emailRecipientsNextCommand = new EmailRecipientsNextCommand();

    @Test
    public void execute_emailRecipientNextCommand_withJobOffer() {
        Model model = new ModelManager();
        LogicManager logic = new LogicManager(model, userPrefs);
        model.getEmailUtil().addJobOffer(jobOfferBuilder.build());
        model.getEmailUtil().setAreRecipientsCandidates(false);

        CommandResult commandResult = emailRecipientsNextCommand.execute(model, commandHistory, userPrefs);
        assertEquals(EmailContentsCommand.MESSAGE_USAGE, commandResult.feedbackToUser);
        assertEquals(EmailContentsCommand.COMMAND_LOGIC_STATE, logic.getState().nextCommand);
    }

    @Test
    public void execute_emailRecipientNextCommand_withCandidate() {
        Model model = new ModelManager();
        LogicManager logic = new LogicManager(model, userPrefs);
        model.getEmailUtil().addCandidate(candidateBuilder.build());
        model.getEmailUtil().setAreRecipientsCandidates(true);

        CommandResult commandResult = emailRecipientsNextCommand.execute(model, commandHistory, userPrefs);
        assertEquals(EmailContentsCommand.MESSAGE_USAGE, commandResult.feedbackToUser);
        assertEquals(EmailContentsCommand.COMMAND_LOGIC_STATE, logic.getState().nextCommand);
    }

    @Test
    public void execute_emailRecipientNextCommand_empty() {
        Model model = new ModelManager();
        model.resetEmailUtil();
        LogicManager logic = new LogicManager(model, userPrefs);
        LogicState beforeCommandState = logic.getState();
        CommandResult commandResult = emailRecipientsNextCommand.execute(model, commandHistory, userPrefs);
        assertEquals(NEXT_RECIPIENTS_ERROR_NO_RECIPIENTS + EmailRecipientsCommand.MESSAGE_USAGE,
                commandResult.feedbackToUser);
        assertEquals(beforeCommandState, logic.getState());
    }
}
