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
import seedu.address.model.module.ModuleName;
import seedu.address.model.module.exceptions.DuplicateModuleException;

/**
 * Provides a test for the class add command
 */
public class ClassAddCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Before
    public void setup() {
        ModuleManager moduleManager = ModuleManager.getInstance();
        final String moduleCodeStr = "CG1111";
        final String moduleNameStr = "Computer Engineering Physics";
        ModuleCode moduleCode = new ModuleCode(moduleCodeStr);
        ModuleName moduleName = new ModuleName(moduleNameStr);
        Module module = new Module(moduleCode, moduleName);
        try {
            moduleManager.addModule(module);
        } catch (DuplicateModuleException e) {
            e.printStackTrace();
        }
        moduleManager.saveModuleList();
    }

    @Test
    public void execute() {
        final String className = "T16";
        final String moduleCode = "CG1111";
        final String maxEnrollment = "20";

        assertCommandSuccess(new ClassAddCommand(new Classroom(new ClassName(className),
                        new ModuleCode(moduleCode), new Enrollment(maxEnrollment))), model, new CommandHistory(),
                String.format(MESSAGE_SUCCESS, className, moduleCode, maxEnrollment),
                model);
    }


    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
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
