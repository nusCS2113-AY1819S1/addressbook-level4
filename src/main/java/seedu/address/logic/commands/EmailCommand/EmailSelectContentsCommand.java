package seedu.address.logic.commands.EmailCommand;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.RecruitBookParser;
import seedu.address.model.Model;

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

        if(emailCommand.areRecipientsCandidates) {
            emailCommand.contents = model.getFilteredJobList();
        } else {
            emailCommand.contents = model.getFilteredCandidateList();
        }

        RecruitBookParser.setEmailCommand(emailCommand);
        LogicManager.setLogicState(EmailSelectContentsCommand.COMMAND_LOGIC_STATE);
        return new CommandResult(EmailSendCommand.COMMAND_WORD);
    }
}
