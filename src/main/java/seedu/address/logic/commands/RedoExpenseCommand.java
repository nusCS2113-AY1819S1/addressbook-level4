package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s expense book to its previously undone state.
 */
public class RedoExpenseCommand extends Command {

    public static final String COMMAND_WORD = "redoExpense";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoExpenseBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoExpenseBook();
        model.updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
