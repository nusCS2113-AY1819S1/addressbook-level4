package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

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
            + ": Deletes the company(s) identified by the index number(s) used in the displayed company list.\n"
            + "Parameters: INDEX,INDEX ... (INDEX must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1,2";

    public static final String MESSAGE_DELETE_COMPANY_SUCCESS = "Deleted Company(s):\n%1$s";

    private final Set<Index> targetIndexes;

    public DeleteCompanyCommand(Set<Index> targetIndexes) {
        this.targetIndexes = targetIndexes;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Company> lastShownList = model.getFilteredCompanyList();
        StringBuilder deletedCompanies = new StringBuilder();

        //Check if any of the specified indexes are invalid
        for (Index index: targetIndexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
            }
        }

        for (Index index: targetIndexes) {
            Company companyToDelete = lastShownList.get(index.getZeroBased());
            model.deleteCompany(companyToDelete);
            deletedCompanies.append(companyToDelete + "\n");
        }

        model.commitCompanyBook();
        return new CommandResult(String.format(MESSAGE_DELETE_COMPANY_SUCCESS, deletedCompanies));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCompanyCommand // instanceof handles nulls
                && targetIndexes.equals(((DeleteCompanyCommand) other).targetIndexes)); // state check
    }


}
