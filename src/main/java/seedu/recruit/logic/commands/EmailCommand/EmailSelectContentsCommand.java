package seedu.recruit.logic.commands.EmailCommand;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.logic.parser.RecruitBookParser;
import seedu.recruit.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * 3rd step of the Email command
 */
public class EmailSelectContentsCommand extends Command {
    public static final String COMMAND_WORD = "next";
    public static final String MESSAGE_USAGE = "Find/Filter the content that you are going to send";
    public static final String COMMAND_LOGIC_STATE = "EmailSelectContents";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EmailCommand emailCommand = RecruitBookParser.getEmailCommand();

        if(emailCommand.isAreRecipientsCandidates()) {
            emailCommand.setContents(model.getFilteredJobList());
        } else {
            emailCommand.setContents(model.getFilteredCandidateList());
        }

        LogicManager.setLogicState(EmailSelectContentsCommand.COMMAND_LOGIC_STATE);
        return new CommandResult(EmailSendCommand.COMMAND_WORD);
    }
}
