package seedu.address.logic.commands.EmailCommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.RecruitBookParser;
import seedu.address.model.Model;


/**
 * 2nd step of the Email command
 */
public class EmailSelectRecipientsCommand extends Command {
    public static final String COMMAND_WORD = "next";
    public static final String MESSAGE_USAGE = "Find/Filter the recipients that you are going to email";
    public static final String COMMAND_LOGIC_STATE = "EmailSelectRecipients";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EmailCommand emailCommand = RecruitBookParser.getEmailCommand();
        String lastCommandUsed = history.getLast();

        if(lastCommandUsed.toUpperCase().contains("LISTC")) {
            emailCommand.recipients = model.getFilteredCandidateList();
            emailCommand.areRecipientsCandidates = true;
        } else {
            emailCommand.recipients = model.getFilteredCompanyList();
            emailCommand.areRecipientsCandidates = false;
        }

        RecruitBookParser.setEmailCommand(emailCommand);
        LogicManager.setLogicState(EmailSelectContentsCommand.COMMAND_LOGIC_STATE);
        return new CommandResult(EmailSelectContentsCommand.COMMAND_WORD);
    }
}
