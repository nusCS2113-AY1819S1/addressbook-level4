package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.CommandHistory;
import seedu.address.model.grades.Grades;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

public class GradeAddCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private CommandHistory commandHistory = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_addGradeSuccessful() {
        final String moduleCode = "PC1222";
        final String gradebookComponentName = "Finals";
        final String adminNo = "A0177898T";
        final int studentMarks = 20;

        assertCommandSuccess(new GradeAddCommand(new Grades(
                        moduleCode,
                        gradebookComponentName,
                        adminNo,
                        studentMarks)),
                new CommandHistory(),
                String.format(GradeAddCommand.MESSAGE_ADD_GRADE_SUCCESS,
                        moduleCode,
                        gradebookComponentName,
                        adminNo,
                        studentMarks));
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }
}
