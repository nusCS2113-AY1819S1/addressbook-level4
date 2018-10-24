package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_STUDENT_MARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ADMIN;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.gradebook.Gradebook;
import seedu.address.model.gradebook.GradebookManager;

/**
 * Assigns marks to student in Trajectory.
 */
public class GradebookAssignGradesCommand extends Command {
    public static final String COMMAND_WORD = "gradebook assign";
    public static final String MESSAGE_ADD_SUCCESS = "\nSuccessfully assigned! \nAdmin No: %1$s"
            + "\nModule Code: %2$s" + "\nGradebook Component Name: %3$s" + "\nMarks: %4$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a gradebook component to Trajectory. "
            + "\nParameters: "
            + PREFIX_STUDENT_ADMIN + "ADMIN. NO "
            + PREFIX_MODULE_CODE + "MODULE CODE  "
            + PREFIX_GRADEBOOK_ITEM + "ITEM "
            + PREFIX_GRADEBOOK_STUDENT_MARKS + "MARKS "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_STUDENT_ADMIN + "A0169980W "
            + PREFIX_MODULE_CODE + "CS2113 "
            + PREFIX_GRADEBOOK_ITEM + "Assignment 1 "
            + PREFIX_GRADEBOOK_STUDENT_MARKS + "50";

    private final Gradebook toAssignStudentGrades;

    public GradebookAssignGradesCommand (Gradebook gradebookComponent) {
        this.toAssignStudentGrades = gradebookComponent;
    }

    @Override
    public CommandResult execute (Model model, CommandHistory history) throws CommandException {
        GradebookManager gradebookManager = new GradebookManager();
        gradebookManager.addGradebookComponent(toAssignStudentGrades);
        gradebookManager.saveGradebookList();

        return new CommandResult(String.format(
                MESSAGE_ADD_SUCCESS,
                toAssignStudentGrades.getStudentAdminNo(),
                toAssignStudentGrades.getModuleCode(),
                toAssignStudentGrades.getGradeComponentName(),
                toAssignStudentGrades.getStudentGrade()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof GradebookAssignGradesCommand //instanceof handles nulls
                && toAssignStudentGrades.equals(((GradebookAssignGradesCommand) other).toAssignStudentGrades));
    }
}
