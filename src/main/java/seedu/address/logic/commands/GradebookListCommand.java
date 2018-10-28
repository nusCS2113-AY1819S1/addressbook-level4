package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.gradebook.Gradebook;
import seedu.address.model.gradebook.GradebookManager;
import seedu.address.ui.HtmlTableProcessor;

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

        sb.append(HtmlTableProcessor.getH3Representation("Gradebook List"));
        sb.append(HtmlTableProcessor.renderTableStart(new ArrayList<String>(
                Arrays.asList("Index", "Module Code", "Component Name", "Maximum Marks", "Weightage"))));

        sb.append(HtmlTableProcessor.getTableItemStart());
        for (Gradebook gradebook: gradebookManager.getGradebooks()) {
            sb.append(HtmlTableProcessor
                    .renderTableItem(new ArrayList<String>(Arrays
                            .asList(Integer.toString(index++),
                                    gradebook.getModuleCode(),
                                    gradebook.getGradeComponentName(),
                                    Integer.toString(gradebook.getGradeComponentMaxMarks()),
                                    Integer.toString(gradebook.getGradeComponentWeightage())))));
        }
        sb.append(HtmlTableProcessor.getTableItemEnd());
        return new CommandResult(MESSAGE_LIST_SUCCESS + count + "\n" + "", sb.toString());
    }
}
