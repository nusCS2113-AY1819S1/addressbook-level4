package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.model.company.Company;

/**
 * Deletes a company identified using it's displayed index from the recruit book.
 */

public class DeleteCompanyCommand extends Command {

    public static final String COMMAND_WORD = "deleteC";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the company identified by the index number used in the displayed company list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_COMPANY_SUCCESS = "Deleted Company: %1$s";

    private final Index targetIndex;

    public DeleteCompanyCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Company> lastShownList = model.getFilteredCompanyList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
        }

        Company companyToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteCompany(companyToDelete);
        model.commitCompanyBook();
        return new CommandResult(String.format(MESSAGE_DELETE_COMPANY_SUCCESS, companyToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCompanyCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCompanyCommand) other).targetIndex)); // state check
    }


}
