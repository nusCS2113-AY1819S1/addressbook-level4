package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
//import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENDITURES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s address book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        /*
        if ("EMPTY".equals(model.getUndoableCommand())) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        else if ("TDL".equals(model.getUndoableCommand())) {
            if (!model.canUndoTodoList()) {
                throw new CommandException(MESSAGE_FAILURE);
            }

            model.undoTodoList();
            model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        }
        else if ("ET".equals(model.getUndoableCommand())) {
            if (!model.canUndoExpenditureList()) {
                throw new CommandException(MESSAGE_FAILURE);
            }

            model.undoExpenditureList();
            model.updateFilteredExpenditureList(PREDICATE_SHOW_ALL_EXPENDITURES);
        }
        */

        if (!model.canUndoTodoList()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoTodoList();
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
