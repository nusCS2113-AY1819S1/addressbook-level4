package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ADMIN_NO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_MARKS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.grades.Grades;
import seedu.address.model.grades.GradesManager;

/**
 * Adds student grade to Trajectory.
 */
public class StudentGradeAddCommand extends Command {
    public static final String COMMAND_WORD = "student grade add";
    public static final String MESSAGE_ADD_GRADE_SUCCESS = "\nSuccessfully assign! \nModule Code: %1$s"
            + "\nGradebook Component Name: %2$s" + "\nAdmin No.: %3$s" + "\nMarks: %4$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds grade to Trajectory. "
            + "\nParameters: "
            + PREFIX_MODULE_CODE + "MODULE CODE  "
            + PREFIX_GRADEBOOK_ITEM + "ITEM "
            + PREFIX_STUDENT_ADMIN_NO + "ADMIN. NO "
            + PREFIX_STUDENT_MARKS + "MARKS "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2113 "
            + PREFIX_GRADEBOOK_ITEM + "Assignment 1 "
            + PREFIX_STUDENT_ADMIN_NO + "A0167789S "
            + PREFIX_STUDENT_MARKS + "50";
    private final Grades toAddGrade;
    public StudentGradeAddCommand(Grades grade) {
        this.toAddGrade = grade;
    }

    @Override
    public CommandResult execute (Model model, CommandHistory history) throws CommandException {
        GradesManager gradesManager = new GradesManager();
        gradesManager.addGrade(toAddGrade);
        gradesManager.saveGradeList();
        return new CommandResult(String.format(
                MESSAGE_ADD_GRADE_SUCCESS,
                toAddGrade.getModuleCode(),
                toAddGrade.getGradeComponentName(),
                toAddGrade.getAdminNo(),
                toAddGrade.getMarks()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof StudentGradeAddCommand //instanceof handles nulls
                && toAddGrade.equals(((StudentGradeAddCommand) other).toAddGrade));
    }
}
