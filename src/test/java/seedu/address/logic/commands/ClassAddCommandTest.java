package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.ClassAddCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.classroom.ClassModule;
import seedu.address.model.classroom.ClassName;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.Enrollment;


public class ClassAddCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        final String className = "T16";
        final String moduleCode = "CG1111";
        final String maxEnrollment = "20";

        assertCommandSuccess(new ClassAddCommand(new Classroom(new ClassName(className),
                        new ClassModule(moduleCode), new Enrollment(maxEnrollment))), model, new CommandHistory(),
                String.format(MESSAGE_SUCCESS, className),
                model);
    }

    @Test
    public void equals() {
        final ClassAddCommand standardCommand = new ClassAddCommand
                (new Classroom(new ClassName(VALID_CLASS_T16),
                        new ClassModule(VALID_MODULE_CODE),
                        new Enrollment(VALID_MAX_ENROLLMENT)));
        // same values -> returns true
        ClassAddCommand commandWithSameValues = new ClassAddCommand(
                new Classroom(new ClassName(VALID_CLASS_T16),
                        new ClassModule(VALID_MODULE_CODE),
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
