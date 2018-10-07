package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

public class EmailSelectRecepientsCommand extends Command {
    public static final String COMMAND_WORD = "recepients";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        String result = "Contents";
        return new CommandResult(result);
    }
}
