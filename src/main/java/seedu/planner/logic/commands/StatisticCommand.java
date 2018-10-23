package seedu.planner.logic.commands;

import seedu.planner.commons.core.EventsCenter;
import seedu.planner.commons.events.ui.ShowPieChartStatsEvent;
import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;

/**
 * Shows the statistics of the current summary
 */
public class StatisticCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = "Provides a statistic based on the summary table displayed.";

    public static final String MESSAGE_SUCCESS = "Stats generated!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ShowPieChartStatsEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
