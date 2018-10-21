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

    public static final String MESSAGE_SUCCESS = "Switched Book successfully.";

    private MainWindow mainWindow;

    public SwitchBookCommand () {
        /**
        switch (mainWindow.getDisplayedBook()) {
            case "companyBook":
            mainWindow.switchToCandidateBook();
                break;
            case "candidateBook":
            mainWindow.switchToCompanyBook();
                break;
            default:
        }*/
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        //model.updateFilteredCandidateList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
