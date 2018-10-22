package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.gradebook.Gradebook;
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
        StringBuilder sb = new StringBuilder();

        int count = gradebookManager.getGradebooks().size();
        int index = 1;

        for (Gradebook gradebook: gradebookManager.getGradebooks()) {
            sb.append(index++ + ") ");
            sb.append("Module Code: ");
            sb.append(gradebook.getModuleCode() + "\n");
            sb.append("Grade Component Name: ");
            sb.append(gradebook.getGradeComponentName() + "\n");
            sb.append("Maximum Marks: ");
            sb.append(gradebook.getGradeComponentMaxMarks() + "\n");
            sb.append("Weightage: ");
            sb.append(gradebook.getGradeComponentWeightage() + "\n");
        }
        return new CommandResult(
                String.format("\n" + (MESSAGE_LIST_SUCCESS) + count + "\n" + sb.toString()));
    }
}
