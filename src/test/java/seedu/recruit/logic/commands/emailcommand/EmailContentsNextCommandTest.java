package seedu.recruit.logic.commands.emailcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.recruit.logic.commands.emailcommand.EmailContentsCommand.NEXT_CONTENTS_ERROR_NO_CONTENTS;

import org.junit.jupiter.api.Test;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.LogicState;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.testutil.CandidateBuilder;
import seedu.recruit.testutil.JobOfferBuilder;

class EmailContentNextCommandTest {

    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();
    private JobOfferBuilder jobOfferBuilder = new JobOfferBuilder();
    private CandidateBuilder candidateBuilder = new CandidateBuilder();
    private EmailContentsNextCommand emailContentsNextCommand = new EmailContentsNextCommand();

    @Test
    void execute_emailContentsNextCommand_withJobOffer() {
        Model model = new ModelManager();
        LogicManager logic = new LogicManager(model, userPrefs);
        model.getEmailUtil().addJobOffer(jobOfferBuilder.build());
        model.getEmailUtil().setAreRecipientsCandidates(true);
        CommandResult commandResult = emailContentsNextCommand.execute(model, commandHistory, userPrefs);
        assertEquals(EmailSendCommand.MESSAGE_USAGE, commandResult.feedbackToUser);
        assertEquals(EmailSendCommand.COMMAND_LOGIC_STATE, logic.getState().nextCommand);
    }

    @Test
    void execute_emailContentsNextCommand_withCandidate() {
        Model model = new ModelManager();
        LogicManager logic = new LogicManager(model, userPrefs);
        model.getEmailUtil().addCandidate(candidateBuilder.build());
        model.getEmailUtil().setAreRecipientsCandidates(false);
        CommandResult commandResult = emailContentsNextCommand.execute(model, commandHistory, userPrefs);
        assertEquals(EmailSendCommand.MESSAGE_USAGE, commandResult.feedbackToUser);
        assertEquals(EmailSendCommand.COMMAND_LOGIC_STATE, logic.getState().nextCommand);
    }

    @Test
    void execute_emailContentsNextCommand_empty() {
        Model model = new ModelManager();
        model.resetEmailUtil();
        LogicManager logic = new LogicManager(model, userPrefs);
        LogicState beforeCommandState = logic.getState();
        CommandResult commandResult = emailContentsNextCommand.execute(model, commandHistory, userPrefs);
        assertEquals(NEXT_CONTENTS_ERROR_NO_CONTENTS + EmailContentsCommand.MESSAGE_USAGE,
                commandResult.feedbackToUser);
        assertEquals(beforeCommandState, logic.getState());
    }
}
