package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.GradeListCommand.MESSAGE_LIST_GRADE_SUCCESS;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.ModelManager;
import seedu.address.model.StorageController;
import seedu.address.model.grades.Grades;
import seedu.address.model.grades.GradesManager;
import seedu.address.testutil.GradeBuilder;

/**
 * Contains tests for GradeListCommand.
 */
public class GradeListCommandTest {
    private static GradesManager gradesManager = new GradesManager();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        StorageController.enterTestMode();
        gradesManager.clearGrade();
        Grades grades = new GradeBuilder()
                .withModuleCode("CS2113")
                .withComponentName("Finals")
                .withAdminNo("A0169999A")
                .withMarks(10)
                .build();
        gradesManager.addGrade(grades);
        gradesManager.saveGradeList();
    }

    @Test
    public void execute_gradeList_success() {
        int expectedSize = 1;
        String expectedMessage = MESSAGE_LIST_GRADE_SUCCESS + "%1$s\n";

        GradeListCommand gradeListCommand = new GradeListCommand();
        CommandResult result = gradeListCommand.execute(new ModelManager(), new CommandHistory());

        assertEquals(String.format(expectedMessage, expectedSize), result.feedbackToUser);
    }
}
