package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.commons.events.ui.JumpToCompanyListRequestEvent;
import seedu.recruit.commons.events.ui.ShowCompanyBookRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.model.company.Company;

/**
 * Selects a company identified using it's displayed index from the recruit book.
 */
public class SelectCompanyCommand extends Command {

    public static final String COMMAND_WORD = "selectCompany";

    public static final String COMMAND_LOGIC_STATE = "SelectCompany";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the company identified by the index number used in the displayed company list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_COMPANY_SUCCESS = "Selected Company: %1$s\n";

    public static final String MESSAGE_SELECT_COMPANY_SUCCESS_NEXT_STEP =
            "Please select a job offer.\n";

    private static Company selectedCompany;

    private final Index targetIndex;

    public SelectCompanyCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    public static Company getSelectedCompany() {
        return selectedCompany;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Company> filteredCompanyList = model.getFilteredCompanyList();

        if (targetIndex.getZeroBased() >= filteredCompanyList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
        }

        selectedCompany = filteredCompanyList.get(targetIndex.getZeroBased());

        if (ShortlistCandidateInitializationCommand.isShortlisting()) {
            LogicManager.setLogicState(SelectJobCommand.COMMAND_LOGIC_STATE);
            return new CommandResult(String.format(MESSAGE_SELECT_COMPANY_SUCCESS,
                    targetIndex.getOneBased()) + MESSAGE_SELECT_COMPANY_SUCCESS_NEXT_STEP +
                    SelectJobCommand.MESSAGE_USAGE);
        }

        EventsCenter.getInstance().post(new ShowCompanyBookRequestEvent());
        EventsCenter.getInstance().post(new JumpToCompanyListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_COMPANY_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCompanyCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCompanyCommand) other).targetIndex)); // state check
    }
}
