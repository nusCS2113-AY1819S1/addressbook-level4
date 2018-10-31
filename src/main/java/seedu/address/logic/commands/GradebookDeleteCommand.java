package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.gradebook.Gradebook;
import seedu.address.model.gradebook.GradebookManager;
import seedu.address.model.grades.Grades;
import seedu.address.model.grades.GradesManager;

/**
 * Deletes gradebook component for module in Trajectory to the user.
 */
public class GradebookDeleteCommand extends Command {
    public static final String COMMAND_WORD = "gradebook delete";
    public static final String MESSAGE_DELETE_SUCCESS = "Successfully deleted! \n Number of grade components: ";
    public static final String MESSAGE_DELETE_FAIL = "\nGradebook component not found!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a gradebook component to Trajectory.\n"
            + "Parameters: "
            + PREFIX_MODULE_CODE + "MODULE_CODE  "
            + PREFIX_GRADEBOOK_ITEM + "COMPONENT_NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2113 "
            + PREFIX_GRADEBOOK_ITEM + "Assignment 1";

    private final Gradebook toDeleteGradebookComponent;
    private final GradebookManager gradebookManager;
    private final Grades toDeleteGrades;
    private final GradesManager gradesManager;

    /**
     * Command deletes a gradebook component.
     */
    public GradebookDeleteCommand(String moduleCode, String gradebookComponentName) {
        gradebookManager = new GradebookManager();
        gradesManager = new GradesManager();
        this.toDeleteGradebookComponent = gradebookManager.findGradebookComponent(moduleCode, gradebookComponentName);
        this.toDeleteGrades = gradesManager.findGrade(moduleCode, gradebookComponentName);
    }

    @Override
    public CommandResult execute (Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        GradesManager gradesManager = new GradesManager();
        if (toDeleteGradebookComponent == null) {
            return new CommandResult(MESSAGE_DELETE_FAIL);
        }
        gradebookManager.deleteGradebookComponent(toDeleteGradebookComponent);
        gradebookManager.saveGradebookList();
        gradesManager.deleteGrades(toDeleteGrades);
        gradesManager.saveGradeList();
        String gradebookList = gradebookManager.listGradebookComponent();
        int size = gradebookManager.getGradebookSize();
        return new CommandResult(MESSAGE_DELETE_SUCCESS + size + "\n" + "", gradebookList);
    }
}
