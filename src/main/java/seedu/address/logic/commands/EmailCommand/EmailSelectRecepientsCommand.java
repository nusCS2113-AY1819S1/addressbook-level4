package seedu.address.logic.commands.EmailCommand;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class EmailSelectRecepientsCommand extends EmailInitialiseCommand {
    public static final String COMMAND_WORD = "recepients";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        String result = "Contents";
        return new CommandResult(result);
    }
}
