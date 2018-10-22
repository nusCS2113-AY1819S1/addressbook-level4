package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.Model;

/**
 * Clears the CompanyBook.
 */

public class ClearCompanyBookCommand extends Command {
    public static final String COMMAND_WORD = "clearC";
    public static final String MESSAGE_SUCCESS = "CompanyBook has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetCompanyData(new CompanyBook());
        model.commitCompanyBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
