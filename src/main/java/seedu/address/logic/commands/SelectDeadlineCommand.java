package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.SelectDeadlineCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.task.Deadline;

//@@author emobeany
/**
 * Selects a date as a deadline for tasks to be added to
 */
public class SelectDeadlineCommand extends Command implements CommandParser {
    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Selects a date. "
            + "Parameters: "
            + PREFIX_DAY + "DAY "
            + PREFIX_MONTH + "MONTH "
            + PREFIX_YEAR + "YEAR \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DAY + "01 "
            + PREFIX_MONTH + "01 "
            + PREFIX_YEAR + "2018 ";

    public static final String MESSAGE_SUCCESS = "New date selected: %1$s";
    public static final String MESSAGE_INVALID_DEADLINE = "The date selected does not exist";

    private final Deadline toSelect;

    /**
     * Creates a SelectDeadline to select the specified {@code Deadline}
     */
    public SelectDeadlineCommand (Deadline deadline) {
        requireNonNull(deadline);
        toSelect = deadline;
    }

    public SelectDeadlineCommand() {
        toSelect = null;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.selectDeadline(toSelect);
        model.commitTaskBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toSelect));
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new SelectDeadlineCommandParser().parse(arguments);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
