package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CLASS_NAME_DESC_T16;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CG1111;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASS_T16;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CG1111;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.ClassDeleteCommand;
import seedu.address.model.StorageController;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ClassroomManager;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleManager;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.testutil.ClassroomBuilder;
import seedu.address.testutil.ModuleBuilder;

/**
 * Provides a test for the classroom delete command parser
 */
public class ClassDeleteCommandParserTest {
    private static ClassroomManager classroomManager;
    private ClassDeleteCommandParser parser = new ClassDeleteCommandParser();

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
        assertParseSuccess(parser, CLASS_NAME_DESC_T16 + MODULE_CODE_DESC_CG1111,
                new ClassDeleteCommand(VALID_CLASS_T16, VALID_MODULE_CODE_CG1111));
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClassDeleteCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, ClassDeleteCommand.COMMAND_WORD, expectedMessage);
    }

    @AfterClass
    public static void tearDown() {
        classroomManager.clearClassrooms();
        classroomManager.saveClassroomList();
    }
}
