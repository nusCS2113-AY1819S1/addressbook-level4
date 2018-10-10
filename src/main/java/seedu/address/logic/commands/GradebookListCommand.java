package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.StorageController;
import seedu.address.model.gradebook.GradebookComponent;

/**
 * Lists all gradebook components for module in Trajectory to the user.
 */
public class GradebookListCommand extends Command {

    public static final String COMMAND_WORD = "gradebook list";
    public static final String MESSAGE_SUCCESS = "Success! List of components in the module:";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        StorageController.retrieveData();
        StringBuilder sb = new StringBuilder();
        for (GradebookComponent gc: StorageController.getGradebookStorage()) {
            sb.append("Module Code: ");
            sb.append(gc.getModuleCode() + "\n");
            sb.append("List of Grade Component: ");
            sb.append(gc.getGradeItemName() + "\n");
        }
        return new CommandResult("\n" + MESSAGE_SUCCESS + "\n" + sb.toString());
    }
}

