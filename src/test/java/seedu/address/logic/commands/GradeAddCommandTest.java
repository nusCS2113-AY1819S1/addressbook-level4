package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.GradeAddCommand.MESSAGE_ADD_GRADE_SUCCESS;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.grades.Grades;
import seedu.address.model.grades.GradesManager;
import seedu.address.testutil.GradeBuilder;

public class GradeAddCommandTest {
    private static GradesManager gradesManager = new GradesManager();
    private static GradeBuilder dummyGrade = new GradeBuilder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        gradesManager.clearGrade();
        gradesManager.saveGradeList();
    }

    @Test
    public void constructor_nullGradebook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new GradebookAddCommand(null);
    }

    @Test
    public void execute_gradeAdd_success() throws CommandException {
        String moduleCode = "CS2113";
        String gradebookComponentName = "Practical Exam";
        String adminNo = "A0169988P";
        float marks = 4;
        int expectedSize = 2;
        String expectedMessage = MESSAGE_ADD_GRADE_SUCCESS + "%1$s\n";

        gradesManager.addGrade(dummyGrade.build());
        gradesManager.saveGradeList();

        Grades grade = new Grades(moduleCode, gradebookComponentName, adminNo, marks);
        GradeAddCommand gradeAddCommand = new GradeAddCommand(grade);
        CommandResult result = gradeAddCommand.execute(new ModelManager(), new CommandHistory());

        assertEquals(String.format(expectedMessage, expectedSize), result.feedbackToUser);
    }
}
