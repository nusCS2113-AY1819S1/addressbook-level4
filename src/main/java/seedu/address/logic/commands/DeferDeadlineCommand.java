package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.DeferDeadlineCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Task;

//@@author ChanChunCheong
/**
 * Defer deadline of a specific task in the taskbook.
 */

public class DeferDeadlineCommand extends Command implements CommandParser {

    public static final String COMMAND_WORD = "defer";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Defers the deadline of the selected task in the taskbook. "
            + "Existing deadline will be overwritten by the input. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_INDEX + "INDEX (must be a positive integer) "
            + PREFIX_DAY + "DAY (months [4, 6, 9, 11] have 30 days and [1, 3, 5, 7, 8, 10, 12] have 31 days) "
            + PREFIX_MONTH + "MONTH (between 1 and 12, inclusive) "
            + PREFIX_YEAR + "YEAR (between 2018 and 9999, inclusive)\n"

            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_DAY + "04 "
            + PREFIX_MONTH + "01 "
            + PREFIX_YEAR + "2018 ";
    /*
    + PREFIX_DAY + "DAY"
    + PREFIX_MONTH + "MONTH"
    + PREFIX_YEAR +  "YEAR \n"
    + "Example: " + COMMAND_WORD + "1"
    + PREFIX_DAY + "01"
    + PREFIX_MONTH + "01"
    + PREFIX_YEAR + "2018";
    */

    public static final String MESSAGE_INVALID_DEADLINE = "The date selected does not exist";
    public static final String MESSAGE_NONEXISTENT_TASK = "This task does not exist in the task book";
    public static final String MESSAGE_SUCCESS = "Date deferred for task: %1$s";
    //public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Defer deadline command not implemented yet";

    private final Index taskIndex;
    private final Deadline deadline;

    public DeferDeadlineCommand() {
        // Null so that it can be initialized in LogicManager
        // Check in JUnit test
        taskIndex = null;
        deadline = null;
    }

    /**
     * Creates an DeferDeadlineCommand to add the specified {@code Task & @code Deadline}
     */
    public DeferDeadlineCommand(Index taskIndex, Deadline deadline) {
        requireNonNull(taskIndex);
        requireNonNull(deadline);
        this.taskIndex = taskIndex;
        this.deadline = deadline;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (taskIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_NONEXISTENT_TASK);
        }

        Task taskToDefer = lastShownList.get(taskIndex.getZeroBased()); // get the task from the filteredtasklist;
        model.deferTaskDeadline(taskToDefer, deadline);
        model.commitTaskBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToDefer));

        /*
        requireNonNull(model);
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
        */
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new DeferDeadlineCommandParser().parse(arguments);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
