package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Tracks the productivity of tasks
 * for the previous week based on hours
 */
public class TrackProductivityCommand extends Command implements CommandParser {
    public static final String COMMAND_WORD = "track";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tracks your productivity.";

    public static final String MESSAGE_SUCCESS = "Recent productvity: %1$s";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new TrackProductivityCommand();
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
