package seedu.planner.logic.commands;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;

import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;

/**
 * Represents a basic summary command with hidden internal logic and the ability to be executed.
 * This command should not be executed
 */
public abstract class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists the summary for each day/month "
            + "for a period of time.\n"
            + "There are 2 MODEs, specify your MODE with either \"date\" or \"month\".\n"
            + "Parameters: "
            + "MODE " + PREFIX_DATE + "DATE_START " + "DATE_END\n"
            + "Example: \"" + COMMAND_WORD + " date " + PREFIX_DATE + "18-9-2018 " + "20-9-2018\" "
            + "OR \"" + COMMAND_WORD + " month " + PREFIX_DATE + "apr-2018 " + "may-2018\"";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
