package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CLASS_NAME_DESC_T16;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CG1111;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ClassListStudentAttendanceCommand;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ClassroomManager;
import seedu.address.testutil.ClassroomBuilder;

/**
 * Provides a test for the class list student attendance command parser
 */
public class ClassListStudentAttendanceCommandParserTest {
    private static ClassroomManager classroomManager;
    private ClassListStudentAttendanceCommandParser parser = new ClassListStudentAttendanceCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Classroom classroom = new ClassroomBuilder().build();
        assertParseSuccess(parser, CLASS_NAME_DESC_T16 + MODULE_CODE_DESC_CG1111,
                new ClassListStudentAttendanceCommand(classroom.getClassName().getValue(),
                        classroom.getModuleCode().moduleCode));
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClassListStudentAttendanceCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, ClassListStudentAttendanceCommand.COMMAND_WORD, expectedMessage);
    }
}
