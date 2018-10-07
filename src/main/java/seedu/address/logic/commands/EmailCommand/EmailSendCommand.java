package seedu.address.logic.commands.EmailCommand;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class EmailSendCommand extends EmailInitialiseCommand {

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        String result = "Send";
        return new CommandResult(result);
    }
}
