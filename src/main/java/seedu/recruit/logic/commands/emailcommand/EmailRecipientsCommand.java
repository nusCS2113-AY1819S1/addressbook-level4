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
public class EmailRecipientsCommand extends Command {
    public static final String MESSAGE_USAGE = "Find/Filter the recipients you intend to email. "
            + "All items listed below will be added into the recipients field.\n"
            + "Type \"add\" to add recipients to the field.\n"
            + "Type \"next\" when you have finished adding recipients to move on to the next step.\n"
            + "Type \"cancel\" to cancel the email command.";
    public static final String COMMAND_LOGIC_STATE = "EmailSelectRecipients";
    public static final String ADD_RECIPIENTS_DUPLICATE_MESSAGE =
            "Unable to add the following because it already has been added before:\n";
    public static final String ADD_RECIPIENTS_RECIPIENTS_ADDED = "Recipients added:\n";
    public static final String ADD_RECIPIENTS_ERROR_ONLY_CANDIDATES = "ERROR: You can only add candidates!\n";
    public static final String ADD_RECIPIENTS_ERROR_ONLY_JOB_OFFERS = "ERROR: You can only add job offers!\n";
    public static final String ADD_RECIPIENTS_NOTHING_SELECTED = "ERROR: Nothing was selected!";
    public static final String NEXT_RECIPIENTS_ERROR_NO_RECIPIENTS = "ERROR: There are no recipients selected!\n";

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws ParseException {
        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }
}
