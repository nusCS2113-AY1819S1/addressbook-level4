package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.gradebook.Gradebook;
import seedu.address.model.gradebook.GradebookManager;

/**
 * Deletes gradebook component for module in Trajectory to the user.
 */
public class GradebookDeleteCommand extends Command {
    public static final String COMMAND_WORD = "gradebook delete";
    private static final String MESSAGE_DELETE_SUCCESS = "\nModule Code: %1$s \nGradebook Component: %2$s \n"
            + "Successfully deleted!";
    private static final String MESSAGE_DELETE_FAIL = "\nUnsuccessful Deletion";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a gradebook component to Trajectory. "
            + "Parameters: "
            + PREFIX_MODULE_CODE + "MODULE_CODE  "
            + PREFIX_GRADEBOOK_ITEM + "ITEM "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2113 "
            + PREFIX_GRADEBOOK_ITEM + "Assignment 1";

    private final Gradebook toDeleteGradebookComponent;
    private final GradebookManager gradebookManager;

    /**
     * Command deletes a classroom.
     */
    public GradebookDeleteCommand(String moduleCode, String gradebookComponentName) {
        gradebookManager = new GradebookManager();
        this.toDeleteGradebookComponent = gradebookManager.findGradebookComponent(moduleCode, gradebookComponentName);
    }

    @Override
    public CommandResult execute (Model model, CommandHistory history) throws CommandException {
        if (toDeleteGradebookComponent == null) {
            return new CommandResult(MESSAGE_DELETE_FAIL);
        }
        gradebookManager.deleteGradebookComponent(toDeleteGradebookComponent);
        gradebookManager.saveGradebookList();
        return new CommandResult(String.format(
                MESSAGE_DELETE_SUCCESS,
                toDeleteGradebookComponent.getModuleCode(),
                toDeleteGradebookComponent.getGradeComponentName(),
                toDeleteGradebookComponent.getGradeComponentMaxMarks(),
                toDeleteGradebookComponent.getGradeComponentWeightage()));
    }
}
