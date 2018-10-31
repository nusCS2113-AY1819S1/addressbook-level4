package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.gradebook.GradebookManager;

/**
 * Lists all gradebook components for module in Trajectory to the user.
 */
public class GradebookListCommand extends Command {
    public static final String COMMAND_WORD = "gradebook list";
    private static final String MESSAGE_LIST_SUCCESS = "Number of Grade Components Listed: ";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        GradebookManager gradebookManager = new GradebookManager();
        String gradebookList = gradebookManager.listGradebookComponent();
        int size = gradebookManager.getGradebookSize();
        return new CommandResult(MESSAGE_LIST_SUCCESS + size + "\n" + "", gradebookList);
    }
}
