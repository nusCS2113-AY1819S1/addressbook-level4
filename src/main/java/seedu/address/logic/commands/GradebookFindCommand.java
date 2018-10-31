package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.gradebook.Gradebook;
import seedu.address.model.gradebook.GradebookManager;
import seedu.address.ui.HtmlTableProcessor;

/**
 * Finds gradebook component for module in Trajectory to the user.
 */
public class GradebookFindCommand extends Command {
    public static final String COMMAND_WORD = "gradebook find";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds a gradebook component to module in Trajectory.\n"
            + "Parameters: "
            + PREFIX_MODULE_CODE + "MODULE_CODE  "
            + PREFIX_GRADEBOOK_ITEM + "COMPONENT_NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2113 "
            + PREFIX_GRADEBOOK_ITEM + "Assignment 1";
    public static final String MESSAGE_FIND_SUCCESS = "\nSuccessfully found!";
    public static final String MESSAGE_FIND_FAIL = "\nGrade component does not exist!";


    private final Gradebook toFindGradebookComponent;
    public GradebookFindCommand (Gradebook gradebookComponent) {
        toFindGradebookComponent = gradebookComponent;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        GradebookManager gradebookManager = new GradebookManager();
        Gradebook gradebook = gradebookManager.findGradebookComponent(
                toFindGradebookComponent.getModuleCode(),
                toFindGradebookComponent.getGradeComponentName());
        if (gradebook == null) {
            return new CommandResult(MESSAGE_FIND_FAIL);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(HtmlTableProcessor.getH3Representation("Details of Gradebook Component"));
        sb.append(HtmlTableProcessor.renderTableStart(new ArrayList<String>(
                Arrays.asList("Module Code", "Component Name", "Maximum Marks", "Weightage"))));

        sb.append(HtmlTableProcessor.getTableItemStart());
        sb.append(HtmlTableProcessor.renderTableItem(new ArrayList<String>(Arrays
                            .asList(gradebook.getModuleCode(),
                                    gradebook.getGradeComponentName(),
                                    Integer.toString(gradebook.getGradeComponentMaxMarks()),
                                    Integer.toString(gradebook.getGradeComponentWeightage())))));
        sb.append(HtmlTableProcessor.getTableItemEnd());
        return new CommandResult(MESSAGE_FIND_SUCCESS + "\n" + "", sb.toString());
    }
}
