package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CLASS_NAME_DESC_T16;
import static seedu.address.logic.commands.CommandTestUtil.MATRIC_DESC_MEGAN;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CG1111;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATRIC_NO_MEGAN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.ClassDeleteStudentCommand;
import seedu.address.model.StorageController;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ClassroomManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.ClassroomBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Provides a test for the class delete student command parser
 */
public class ClassDeleteStudentCommandParserTest {
    private static ClassroomManager classroomManager;
    private ClassDeleteStudentCommandParser parser = new ClassDeleteStudentCommandParser();

    @Before
    public void setUp() {
        StorageController.enterTestMode();
        classroomManager = ClassroomManager.getInstance();
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Classroom classroom = new ClassroomBuilder().build();
        Person studentMegan = new PersonBuilder().withMatricNo(VALID_MATRIC_NO_MEGAN).build();
        assertParseSuccess(parser, CLASS_NAME_DESC_T16 + MODULE_CODE_DESC_CG1111 + MATRIC_DESC_MEGAN,
                new ClassDeleteStudentCommand(classroom.getClassName().getValue(), classroom.getModuleCode().moduleCode,
                        studentMegan.getMatricNo().matricNo));
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClassDeleteStudentCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, ClassDeleteStudentCommand.COMMAND_WORD, expectedMessage);
    }

    @AfterClass
    public static void tearDown() {
        classroomManager.clearClassrooms();
        classroomManager.saveClassroomList();
    }
}
