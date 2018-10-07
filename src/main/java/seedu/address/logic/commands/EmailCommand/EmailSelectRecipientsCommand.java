package seedu.address.logic.commands.EmailCommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;


/**
 * 2nd step of the Email command
 */
public class EmailSelectRecipientsCommand extends EmailInitialiseCommand {
    public static final String COMMAND_WORD = "next";
    public static final String MESSAGE_USAGE = "Find/Filter the recipients that you are going to email";
    public static final String COMMAND_LOGIC_STATE = "EmailSelectRecipients";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        String lastCommandUsed = history.getLast();

        if(lastCommandUsed.toUpperCase().contains("LISTC")) {
            EmailInitialiseCommand.recipients = model.getFilteredCandidateList();
            EmailInitialiseCommand.areRecipientsCandidates = true;
        } else {
            EmailInitialiseCommand.recipients = model.getFilteredJobList();
            EmailInitialiseCommand.areRecipientsCandidates = false;
        }

        LogicManager.setLogicState(EmailSelectContentsCommand.COMMAND_LOGIC_STATE);
        return new CommandResult(EmailSelectContentsCommand.COMMAND_WORD);
    }
}
