package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.StorageController;
import seedu.address.storage.adapter.XmlAdaptedGradebook;

/**
 * Lists all gradebook components for module in Trajectory to the user.
 */
public class GradebookListCommand extends Command {

    public static final String COMMAND_WORD = "gradebook list";
    private static final String MESSAGE_LIST_SUCCESS = "\nNumber of Grade Components Listed: %1$s \n%2$s";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        int count = 0;
        int index = 1;

        StringBuilder sb = new StringBuilder();

        for (XmlAdaptedGradebook gradebookAdapted: StorageController.getGradebookStorage()) {
            sb.append(index++ + ") ");
            sb.append("Module Code: ");
            sb.append(gradebookAdapted.getModuleCode() + "\n");
            sb.append("Grade Component Name: ");
            sb.append(gradebookAdapted.getGradeComponentName() + "\n");
            sb.append("Maximum Marks: ");
            sb.append(gradebookAdapted.getGradeComponentMaxMarks() + "\n");
            sb.append("Weightage: ");
            sb.append(gradebookAdapted.getGradeComponentWeightage() + "\n");
            count++;
        }
        return new CommandResult(String.format(MESSAGE_LIST_SUCCESS, count, sb.toString()));
    }
}
