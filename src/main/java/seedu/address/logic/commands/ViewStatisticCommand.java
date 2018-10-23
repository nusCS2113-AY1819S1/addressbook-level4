package seedu.address.logic.commands;

import seedu.address.commons.core.StatisticCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all books in the book inventory to the user.
 */
public class ViewStatisticCommand extends Command {

    public static final String COMMAND_WORD = "viewstatistic";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(StatisticCenter.getInstance().getStatistic().toString());
    }
}
