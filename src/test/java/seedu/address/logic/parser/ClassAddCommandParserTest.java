package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CLASS_ENROLLMENT_DESC_20;
import static seedu.address.logic.commands.CommandTestUtil.CLASS_NAME_DESC_T16;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CG1111;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.ClassAddCommand;
import seedu.address.model.StorageController;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ClassroomManager;
import seedu.address.testutil.ClassroomBuilder;

/**
 * Provides a test for the class add command parser
 */
public class ClassAddCommandParserTest {
    private static ClassroomManager classroomManager;
    private ClassAddCommandParser parser = new ClassAddCommandParser();

    @Before
    public void setUp() {
        StorageController.enterTestMode();
        classroomManager = ClassroomManager.getInstance();
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Classroom classroom = new ClassroomBuilder().build();
        assertParseSuccess(parser, CLASS_NAME_DESC_T16 + MODULE_CODE_DESC_CG1111 + CLASS_ENROLLMENT_DESC_20,
                new ClassAddCommand(classroom));
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClassAddCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, ClassAddCommand.COMMAND_WORD, expectedMessage);
    }

    @AfterClass
    public static void tearDown() {
        classroomManager.clearClassrooms();
        classroomManager.saveClassroomList();
    }
}
