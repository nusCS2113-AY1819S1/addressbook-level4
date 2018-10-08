package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Distribute will automatically split all persons into n number of groups based on user choice
 * Will be able to split into n groups base on gender or nationality or both
 */
public class DistributeCommand extends Command {
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }
}
