package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

public class EmailSelectContentsCommand extends Command {
    public static final String COMMAND_WORD = "contents";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        String result = "Contents";
        try {
            System.out.println(EmailCommand.recipientCandidates.toString());
        } catch (NullPointerException e) {
            System.out.println("recipientCandidates is null :o");
        }
        return new CommandResult(result);
    }
}
