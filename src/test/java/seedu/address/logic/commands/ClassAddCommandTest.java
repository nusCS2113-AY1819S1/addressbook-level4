package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.ClassAddCommand.MESSAGE_SUCCESS;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASS_T16;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_ENROLLMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE;
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
import seedu.address.model.classroom.ClassName;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.Enrollment;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleManager;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.testutil.ClassroomBuilder;
import seedu.address.testutil.ModuleBuilder;

/**
 * Provides a test for the class add command
 */
public class ClassAddCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setup() {
        StorageController.enterTestMode();
        ModuleManager moduleManager = ModuleManager.getInstance();
        Module module = new ModuleBuilder().withModuleCode("CG1111").build();
        try {
            moduleManager.addModule(module);
        } catch (DuplicateModuleException e) {
            e.printStackTrace();
        }
        moduleManager.saveModuleList();
    }

    @Test
    public void execute_classroomAccepted_addSuccessful() {
        Classroom classroom = new ClassroomBuilder().build();

        assertCommandSuccess(new ClassAddCommand(classroom), model, commandHistory,
                String.format(MESSAGE_SUCCESS, classroom.getClassName(),
                        classroom.getModuleCode(), classroom.getMaxEnrollment()),
                model);
    }

    @Test
    public void execute_duplicateClassroom_throwsCommandException() throws Exception {
        Classroom validClassroom = new ClassroomBuilder().build();
        ClassAddCommand classAddCommand = new ClassAddCommand(validClassroom);

        thrown.expect(CommandException.class);
        thrown.expectMessage(ClassAddCommand.MESSAGE_DUPLICATE_CLASSROOM);
        classAddCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_classroomInvalidModule_throwsCommandException() throws Exception {
        Classroom validClassroom = new ClassroomBuilder().withModuleCode("CG1112").build();
        ClassAddCommand classAddCommand = new ClassAddCommand(validClassroom);

        thrown.expect(CommandException.class);
        thrown.expectMessage(ClassAddCommand.MESSAGE_MODULE_CODE_INVALID);
        classAddCommand.execute(model, commandHistory);
    }


    @Test
    public void constructor_nullClassroom_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ClassAddCommand(null);
    }

    @Test
    public void equals() {
        final ClassAddCommand standardCommand = new ClassAddCommand(new Classroom(
                new ClassName(VALID_CLASS_T16),
                        new ModuleCode(VALID_MODULE_CODE),
                        new Enrollment(VALID_MAX_ENROLLMENT)));
        // same values -> returns true
        ClassAddCommand commandWithSameValues = new ClassAddCommand(
                new Classroom(new ClassName(VALID_CLASS_T16),
                        new ModuleCode(VALID_MODULE_CODE),
                        new Enrollment(VALID_MAX_ENROLLMENT)));
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
    }
}
