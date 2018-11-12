//@@author kohjunkiat
package seedu.address.logic.commands;

import seedu.address.commons.core.StatisticCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * View statistics of the bookinventory
 */
public class ViewStatisticCommand extends Command {

    public static final String COMMAND_WORD = "stats";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : View the statistics for BookInventory.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(StatisticCenter.getInstance().getStatistic().toString());
    }
}
