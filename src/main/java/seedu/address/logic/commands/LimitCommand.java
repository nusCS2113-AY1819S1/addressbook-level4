package seedu.address.logic.commands.exceptions;


import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.record.Limit;
import seedu.address.model.record.Record;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;


public class LimitCommand extends Command {
    public static final String COMMAND_WORD= "limit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set a limit for a period of time. "
            + "Parameters: "
            + PREFIX_DATE + "DATE_START "
            + PREFIX_DATE + "DATE_END "
            + PREFIX_MONEYFLOW + "LIMIT_MONEY "

            + "Example: " + COMMAND_WORD + " "

            + PREFIX_DATE + "18-9-2018 "
            + PREFIX_DATE + "20-9-2018 "
            + PREFIX_MONEYFLOW + "100 ";

    //public static Limit limit;
    public static Limit limit;


    public static final String MESSAGE_SUCCESS = "Limit has been set: %1$s";

    public static final String MESSAGE_DUPLICATE_LIMIT = "There is already a limit for this period ";

    public  LimitCommand (Limit limitin){
        requireNonNull(limitin);
        limit=limitin;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        //if (model.hasRecord(dummy)) {
        if (false) {
            throw new CommandException(MESSAGE_DUPLICATE_LIMIT);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, limit));
    }
    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof LimitCommand // instanceof handles nulls
                && limit.equals(((LimitCommand) other).limit));
    }
}
