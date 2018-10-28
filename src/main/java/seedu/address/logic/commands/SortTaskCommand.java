package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.SortTaskCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

//@@author ChanChunCheong
/**
 * Sorts the tasks list in the task book based on the method chosen
 */
public class SortTaskCommand extends Command implements CommandParser {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sort the tasks in the task book by preferred way. "
            + "Parameters: "
            + PREFIX_SORT + "METHOD \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SORT + "modules";

    //public static final String MESSAGE_NOT_IMPLEMENTED_YET = "SortTask command not implemented yet";
    public static final String MESSAGE_ARGUMENTS = "method: %1$s";
    public static final String MESSAGE_SUCCESS = "Sort task based on: %1$s";
    private final String method;

    public SortTaskCommand() {
        // Null so that it can be initialized in LogicManager
        // Check in JUnit test
        method = null;
    }

    /**
     * Creates an DeferDeadlineCommand to add the specified {@code Task & @code Deadline}
     */
    public SortTaskCommand(String method) {
        requireNonNull(method);
        this.method = method.toLowerCase();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        requireNonNull(model);
        //throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
        //throw new CommandException(String.format(MESSAGE_ARGUMENTS, method));
        if (method.equals("modules") || method.equals("deadlines") || method.equals("priority")
                || method.equals("title")) {
            model.sortTask(method);
            model.commitTaskBook();
            return new CommandResult(String.format(MESSAGE_SUCCESS, method));
        } else {
            //if the methods called are not within the list of methods called then throw CommandException
            throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortTaskCommand.MESSAGE_USAGE));
        }

    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new SortTaskCommandParser().parse(arguments);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    /*
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof SortTaskCommand)) {
            return false;
        }
        // state check
        SortTaskCommand e = (SortTaskCommand) other;
        return method.equals(e.method);
    }
    */
}
