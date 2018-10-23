package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.model.Model.PREDICATE_SHOW_ALL_COMPANIES;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;

/**
 * Reverts the {@code model}'s CompanyBook to its previously undone state.
 */

public class RedoCompanyBookCommand extends Command {

    public static final String COMMAND_WORD = "redoC";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more CompanyBook commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoCompanyBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoCompanyBook();
        model.updateFilteredCompanyList(PREDICATE_SHOW_ALL_COMPANIES);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
