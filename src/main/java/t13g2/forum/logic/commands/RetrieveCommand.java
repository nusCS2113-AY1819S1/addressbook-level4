package t13g2.forum.logic.commands;

import t13g2.forum.logic.CommandHistory;
import t13g2.forum.logic.commands.exceptions.CommandException;
import t13g2.forum.model.Model;

public class RetrieveCommand extends Command{

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }
}
