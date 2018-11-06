package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.ui.FocusOnCompanyBookRequestEvent;
import seedu.recruit.commons.events.ui.ShowUpdatedCompanyJobListRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.Model;

/**
 * Clears the CompanyBook.
 */

public class ClearCompanyBookCommand extends Command {
    public static final String COMMAND_WORD = "clearC";
    public static final String MESSAGE_SUCCESS = "CompanyBook has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears Company Book.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EventsCenter.getInstance().post(new FocusOnCompanyBookRequestEvent());
        model.resetCompanyData(new CompanyBook());
        model.commitCompanyBook();
        EventsCenter.getInstance().post(new ShowUpdatedCompanyJobListRequestEvent(
                model.getFilteredCompanyJobList().size()));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
