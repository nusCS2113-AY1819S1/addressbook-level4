package seedu.recruit.logic.commands.EmailCommand;

import javafx.collections.ObservableList;
import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.model.Model;

/**
 * Finally, send the email.
 */
public class EmailSendCommand extends Command {
    public static final String COMMAND_WORD = "send";
    public static final String MESSAGE_USAGE = "Sending...";
    public static final String COMMAND_LOGIC_STATE = "EmailSend";
    public static final String EMAIL_SUCCESS = "Successfully sent the email!";
    public static final String EMAIL_FAILURE = "Failed to send the email!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        String result = "Send";
        EmailUtil emailUtil = model.getEmailUtil();

        ObservableList<?> recipients = emailUtil.getRecipients();
        ObservableList<?> contents = emailUtil.getContents();

        for(Object recipient : recipients) {
            System.out.println(recipient.toString());
        }

        for(Object content : contents) {
            System.out.println(content.toString());
        }

        LogicManager.setLogicState("primary");
        return new CommandResult(result);
    }
}
