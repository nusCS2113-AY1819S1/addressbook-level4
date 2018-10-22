package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.Model;
import seedu.recruit.ui.MainWindow;

/**
 * Switches view between Candidate Book and Company Book
 */
public class SwitchBookCommand extends Command {
    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_SUCCESSFULLY_SWITCHED_TO_CANDIDATE_BOOK
            = "Switched to Candidate Book successfully.";

    public static final String MESSAGE_SUCCESSFULLY_SWITCHED_TO_COMPANY_BOOK
            = "Switched to Company Book successfully.";

    public static final String MESSAGE_FAILURE
            = "Failed to switch book.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        switch (MainWindow.getDisplayedBook()) {
            case "companyBook":
                MainWindow.switchToCandidateBook();
                return new CommandResult(MESSAGE_SUCCESSFULLY_SWITCHED_TO_CANDIDATE_BOOK);
                //break;
            case "candidateBook":
                MainWindow.switchToCompanyBook();
                return new CommandResult(MESSAGE_SUCCESSFULLY_SWITCHED_TO_COMPANY_BOOK);
                //break;
            default:
        }
        return new CommandResult(MESSAGE_FAILURE);
    }
}
