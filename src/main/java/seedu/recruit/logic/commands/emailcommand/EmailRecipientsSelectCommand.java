package seedu.recruit.logic.commands.emailcommand;

import static seedu.recruit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.Model;

/**
 * 2nd step of the Email command: selecting recipients
 */
public class EmailRecipientsSelectCommand extends Command {
    public static final String MESSAGE_USAGE = "Find/Filter the recipients you intend to email. "
            + "All items listed below will be added into the recipients field.\n"
            + "Type \"add\" to add recipients to the field.\n"
            + "Type \"next\" when you have finished adding recipients to move on to the next step.\n"
            + "Type \"cancel\" to cancel the email command.";
    public static final String COMMAND_LOGIC_STATE = "EmailSelectRecipients";

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws ParseException {
        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }
}
