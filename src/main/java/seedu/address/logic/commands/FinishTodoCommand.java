package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.todo.Todo;

//@@author linnnruoo
/**
 * Complete a todo task identified using it's displayed index from the address book.
 */
public class FinishTodoCommand extends Command {

    public static final String COMMAND_WORD = "finishTodo";
    public static final String COMMAND_ALIAS = "ftd";


    public static final String COMMAND_PARAMETERS = "Parameters: INDEX (must be a positive integer)\n";
    public static final String COMMAND_EXAMPLE = "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Complete the to-do task identified by the index number used in the displayed to-do task list.\n"
        + COMMAND_PARAMETERS
        + COMMAND_EXAMPLE;

    public static final String MESSAGE_FINISH_TODO_SUCCESS = "The selected to-do task is completed: %1$s";

    private final Index targetIndex;

    public FinishTodoCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Todo> lastShownList = model.getFilteredTodoList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
        }

        Todo todoToFinish = lastShownList.get(targetIndex.getZeroBased());
        model.finishTodo(todoToFinish);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_FINISH_TODO_SUCCESS, todoToFinish));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FinishTodoCommand // instanceof handles nulls
                && targetIndex.equals(((FinishTodoCommand) other).targetIndex)); // state check
    }
}
