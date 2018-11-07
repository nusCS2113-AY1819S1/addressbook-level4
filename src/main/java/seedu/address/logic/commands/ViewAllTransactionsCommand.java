package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;

/**
 * This command allows users to view all transactions on a specified day
 */
public class ViewAllTransactionsCommand extends Command {

    public static final String COMMAND_WORD = "alltransactions";
    public static final String MESSAGE_USAGE = ": Shows all transactions on a specified day in chronological order\n"
            + "Format: "
            + COMMAND_WORD
            + " " + PREFIX_TIME
            + "<yyyy/MM/dd>";

    private final String date;

    public ViewAllTransactionsCommand(String date) {
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            return new CommandResult(model.getDaysTransactionsAsString(date));
        } catch (InvalidTimeFormatException e) {
            throw new CommandException("");
        }
    }
}
