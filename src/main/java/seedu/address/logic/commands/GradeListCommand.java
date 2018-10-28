package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.grades.Grades;
import seedu.address.model.grades.GradesManager;
import seedu.address.ui.HtmlTableProcessor;

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

        sb.append(HtmlTableProcessor.getH3Representation("Student Grades List"));
        sb.append(HtmlTableProcessor.renderTableStart(new ArrayList<String>(
                Arrays.asList("Index", "Module Code", "Component Name", "Admin. No", "Marks"))));

        sb.append(HtmlTableProcessor.getTableItemStart());
        for (Grades grade: gradesManager.getGrades()) {
            sb.append(HtmlTableProcessor
                    .renderTableItem(new ArrayList<String>(Arrays
                            .asList(Integer.toString(index++),
                                    grade.getModuleCode(),
                                    grade.getGradeComponentName(),
                                    grade.getAdminNo(),
                                    Float.toString(grade.getMarks())))));
        }
        sb.append(HtmlTableProcessor.getTableItemEnd());
        return new CommandResult(MESSAGE_LIST_GRADE_SUCCESS + count + "\n" + "", sb.toString());
    }

}
