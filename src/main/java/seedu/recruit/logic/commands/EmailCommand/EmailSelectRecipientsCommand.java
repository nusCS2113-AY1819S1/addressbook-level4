package seedu.recruit.logic.commands.EmailCommand;

import static java.util.Objects.requireNonNull;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.logic.parser.RecruitBookParser;
import seedu.recruit.model.Model;


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
        String lastCommandUsed = history.getLast();

        if(lastCommandUsed.toUpperCase().contains("LISTC")) {
            RecruitBookParser.emailCommand.setRecipients(model.getFilteredCandidateList());
            RecruitBookParser.emailCommand.setAreRecipientsCandidates(true);
        } else {
            RecruitBookParser.emailCommand.setRecipients(model.getFilteredCompanyList());
            RecruitBookParser.emailCommand.setAreRecipientsCandidates(false);
        }

        LogicManager.setLogicState(EmailSelectContentsCommand.COMMAND_LOGIC_STATE);
        return new CommandResult(EmailSelectContentsCommand.COMMAND_WORD);
    }
}
