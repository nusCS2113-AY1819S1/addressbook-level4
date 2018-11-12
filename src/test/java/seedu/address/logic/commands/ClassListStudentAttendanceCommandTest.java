package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ClassListStudentAttendanceCommand.MESSAGE_FAIL;
import static seedu.address.logic.commands.ClassListStudentAttendanceCommand.MESSAGE_MODULE_CODE_INVALID;
import static seedu.address.logic.commands.ClassListStudentAttendanceCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASS_T16;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CG1111;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.StorageController;
import seedu.address.model.UserPrefs;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ClassroomManager;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleManager;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.testutil.ClassroomBuilder;
import seedu.address.testutil.ModuleBuilder;

/**
 * Provides a test for the class list student attendance command
 */
public class ClassListStudentAttendanceCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private Classroom classroom;
    private ClassroomManager classroomManager;

    @Before
    public void setUp() {
        StorageController.enterTestMode();
        ModuleManager moduleManager = ModuleManager.getInstance();
        classroomManager = ClassroomManager.getInstance();
        Module module = new ModuleBuilder().withModuleCode("CG1111").build();

        try {
            moduleManager.addModule(module);
        } catch (DuplicateModuleException e) {
            e.getMessage();
        }
        classroom = new ClassroomBuilder().withClassName("T16").withModuleCode("CG1111").build();
    }

    @Test
    public void execute_invalidModuleCode_throwsCommandException() throws CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_MODULE_CODE_INVALID);
        ClassListStudentAttendanceCommand classListStudentAttendanceCommand =
                new ClassListStudentAttendanceCommand(classroom.getClassName().getValue(),
                "Invalid Module Code");
        classListStudentAttendanceCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_invalidClassroom_throwsCommandException() throws CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_FAIL);
        ClassListStudentAttendanceCommand classListStudentAttendanceCommand =
                new ClassListStudentAttendanceCommand("Invalid Classroom",
                        classroom.getModuleCode().moduleCode);
        classListStudentAttendanceCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_classroomListStudentAttendanceSuccessful() {
        classroomManager.addClassroom(classroom);
        String expectedMessage = String.format(MESSAGE_SUCCESS + "\n",
                classroom.getClassName(), classroom.getModuleCode());
        assertCommandSuccess(new ClassListStudentAttendanceCommand(classroom.getClassName().getValue(),
                        classroom.getModuleCode().moduleCode), commandHistory,
                expectedMessage);
    }

    @Test
    public void equals() {
        final String className = "T16";
        final String moduleCode = "CG1111";
        final ClassListStudentAttendanceCommand standardCommand = new ClassListStudentAttendanceCommand(className,
                moduleCode);
        // same values -> returns true
        ClassListStudentAttendanceCommand commandWithSameValues = new ClassListStudentAttendanceCommand(VALID_CLASS_T16,
                VALID_MODULE_CODE_CG1111);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
    }
}
