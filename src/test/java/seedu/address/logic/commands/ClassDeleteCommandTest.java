package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ClassDeleteCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASS_T16;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CG1111;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.AfterClass;
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
 * Provides a test for the class delete command
 */
public class ClassDeleteCommandTest {
    private static ClassroomManager classroomManager;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();


    @Before
    public void setUp() {
        StorageController.enterTestMode();
        ModuleManager moduleManager = ModuleManager.getInstance();
        Module module = new ModuleBuilder().withModuleCode("CG1111").build();

        try {
            moduleManager.addModule(module);
        } catch (DuplicateModuleException e) {
            e.printStackTrace();
        }

        moduleManager.saveModuleList();
        classroomManager = ClassroomManager.getInstance();
        Classroom classroom = new ClassroomBuilder().build();
        classroomManager.addClassroom(classroom);
    }

    @Test
    public void constructor_nullClassroom_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ClassDeleteCommand(null, null);
    }

    @Test
    public void execute_deleteSuccessful() {
        Classroom classToDelete = classroomManager.findClassroom("T16", "CG1111");
        assertCommandSuccess(new ClassDeleteCommand(classToDelete.getClassName().getValue(),
                        classToDelete.getModuleCode().moduleCode), model, commandHistory,
                String.format(MESSAGE_SUCCESS, classToDelete.getClassName(),
                        classToDelete.getModuleCode(),
                        classToDelete.getMaxEnrollment()),
                model);
    }

    @Test
    public void execute_deleteInvalidClassroom_throwsNullPointerException() throws CommandException {
        ClassDeleteCommand classDeleteCommand = new ClassDeleteCommand("T16",
                "CG1112");
        thrown.expect(CommandException.class);
        thrown.expectMessage(ClassDeleteCommand.MESSAGE_FAIL);
        classDeleteCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        final String className = "T16";
        final String moduleCode = "CG1111";
        final ClassDeleteCommand standardCommand = new ClassDeleteCommand(className, moduleCode);

        // same values -> returns true
        ClassDeleteCommand commandWithSameValues = new ClassDeleteCommand(VALID_CLASS_T16, VALID_MODULE_CODE_CG1111);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
    }

    @AfterClass
    public static void tearDown() {
        classroomManager.clearClassrooms();
        classroomManager.saveClassroomList();
    }
}
