package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.grades.Grades;
import seedu.address.model.grades.GradesManager;

/**
 * Lists all gradebook components for module in Trajectory to the user.
 */
public class GradeListCommand extends Command {
    public static final String COMMAND_WORD = "grade list";
    private static final String MESSAGE_LIST_GRADE_SUCCESS = "Number of Student Grades Listed: ";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        GradesManager gradesManager = new GradesManager();
        StringBuilder sb = new StringBuilder();

        int count = gradesManager.getGrades().size();
        int index = 1;

        for (Grades grades: gradesManager.getGrades()) {
            sb.append(index++ + ") ");
            sb.append("Module Code: ");
            sb.append(grades.getModuleCode() + "\n");
            sb.append("Grade Component Name: ");
            sb.append(grades.getGradeComponentName() + "\n");
            sb.append("Admin No: ");
            sb.append(grades.getAdminNo() + "\n");
            sb.append("Marks: ");
            sb.append(grades.getMarks() + "\n");

        }
        return new CommandResult(
                String.format("\n" + (MESSAGE_LIST_GRADE_SUCCESS) + count + "\n" + sb.toString()));
    }

}
