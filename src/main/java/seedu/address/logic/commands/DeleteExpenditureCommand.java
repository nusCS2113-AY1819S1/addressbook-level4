//@@author feijunzi
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expenditureinfo.Expenditure;

/**
 *  Deletes an expenditure identified using it's displayed index from the expenditure tracker.
 */

public class DeleteExpenditureCommand extends Command {
    public static final String COMMAND_WORD = "ET_delete";
    //public static final String COMMAND_ALIAS = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the expenditure identified by the index number used in the displayed expenditure list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_SUCCESS = "Deleted expenditure: %1$s";

    private final Index targetIndex;

    public DeleteExpenditureCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Expenditure> lastShownList = model.getFilteredExpenditureList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXPENDITURE_DISPLAYED_INDEX);
        }

        Expenditure expenditureToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteExpenditure(expenditureToDelete);
        model.commitExpenditureList();
        model.commitUndoableExpenditure();
        return new CommandResult(String.format(MESSAGE_DELETE_SUCCESS, expenditureToDelete));
    }
}

