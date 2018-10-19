package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.gradebook.Gradebook;
import seedu.address.model.gradebook.GradebookManager;

/**
 * Finds gradebook component for module in Trajectory to the user.
 */
public class GradebookFindCommand extends Command {
    public static final String COMMAND_WORD = "gradebook find";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds a gradebook component to module in Trajectory. "
            + "Parameters: "
            + PREFIX_MODULECODE + "MODULE_CODE  "
            + PREFIX_GRADEBOOK_ITEM + "ITEM "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULECODE + "CS2113 "
            + PREFIX_GRADEBOOK_ITEM + "Assignment 1";
    private static final String MESSAGE_FIND_FAIL = "Unsuccessful find";
    private static final String MESSAGE_FIND_SUCCESS = "Successfully found!"
            + "\nModule Code: %1$s"
            + "\nComponent Name: %2$s"
            + "\nMaximum Marks: %3$s"
            + "\nWeightage: %4$s";


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

        return new CommandResult(String.format(MESSAGE_FIND_SUCCESS,
                toFindGradebookComponent.getModuleCode(),
                toFindGradebookComponent.getGradeComponentName(),
                toFindGradebookComponent.getGradeComponentMaxMarks(),
                toFindGradebookComponent.getGradeComponentWeightage()));
    }
}
