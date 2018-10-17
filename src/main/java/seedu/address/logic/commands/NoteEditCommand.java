package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static seedu.address.logic.commands.NoteListCommand.MESSAGE_NOT_FOUND;

public class NoteEditCommand extends Command {

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {


        //boolean isCancelled = showTextPrompt();
        return new CommandResult(String.format(MESSAGE_NOT_FOUND));
    }
}
