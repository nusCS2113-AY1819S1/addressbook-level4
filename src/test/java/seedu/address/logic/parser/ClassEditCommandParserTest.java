package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CLASS_ENROLLMENT_DESC_20;
import static seedu.address.logic.commands.CommandTestUtil.CLASS_ENROLLMENT_DESC_99;
import static seedu.address.logic.commands.CommandTestUtil.CLASS_NAME_DESC_T16;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CLASS_ENROLLMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CLASS_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CG1111;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASS_T16;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_ENROLLMENT_99;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CG1111;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.ClassEditCommand;
import seedu.address.model.StorageController;
import seedu.address.model.classroom.ClassName;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ClassroomManager;
import seedu.address.model.classroom.Enrollment;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleManager;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.testutil.ClassroomBuilder;
import seedu.address.testutil.ModuleBuilder;

/**
 * Provides a test for the classroom edit command parser
 */
public class ClassEditCommandParserTest {
    private static ClassroomManager classroomManager;
    private ClassEditCommandParser parser = new ClassEditCommandParser();

    @Before
    public void setUp() {
        StorageController.enterTestMode();
        ModuleManager moduleManager = ModuleManager.getInstance();
        classroomManager = ClassroomManager.getInstance();
        Module module = new ModuleBuilder().withModuleCode("CG1111").build();
        try {
            moduleManager.addModule(module);
        } catch (DuplicateModuleException e) {
            e.printStackTrace();
        }
        moduleManager.saveModuleList();
        Classroom classroom = new ClassroomBuilder().build();
        classroomManager.addClassroom(classroom);
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Classroom classroom = new ClassroomBuilder().build();
        ClassEditCommand.EditClassDescriptor editClassDescriptor = new ClassEditCommand.EditClassDescriptor();
        editClassDescriptor.setClassName(classroom.getClassName());
        editClassDescriptor.setModuleCode(classroom.getModuleCode());
        editClassDescriptor.setEnrollment(new Enrollment(VALID_MAX_ENROLLMENT_99));
        ClassEditCommand classEditCommand = new ClassEditCommand(VALID_CLASS_T16, VALID_MODULE_CODE_CG1111,
                editClassDescriptor);
        assertParseSuccess(parser, CLASS_NAME_DESC_T16 + MODULE_CODE_DESC_CG1111 + CLASS_ENROLLMENT_DESC_99,
                classEditCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClassEditCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, ClassEditCommand.COMMAND_WORD, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_CLASS_NAME_DESC
                + MODULE_CODE_DESC_CG1111
                + CLASS_ENROLLMENT_DESC_20, ClassName.MESSAGE_CLASSNAME_CONSTRAINTS); // invalid classname
        assertParseFailure(parser, CLASS_NAME_DESC_T16
                + INVALID_MODULE_CODE_DESC
                + CLASS_ENROLLMENT_DESC_20, ModuleCode.MESSAGE_MODULE_CODE_CONSTRAINT); // invalid module code
        assertParseFailure(parser, CLASS_NAME_DESC_T16
                + MODULE_CODE_DESC_CG1111
                + INVALID_CLASS_ENROLLMENT_DESC, Enrollment.MESSAGE_ENROLLMENT_CONSTRAINTS); // invalid enrollment size
    }

    @AfterClass
    public static void tearDown() {
        classroomManager.clearClassrooms();
        classroomManager.saveClassroomList();
    }
}
