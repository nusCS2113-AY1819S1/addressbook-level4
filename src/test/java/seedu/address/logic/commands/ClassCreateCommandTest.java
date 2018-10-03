package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ClassCreateCommand.MESSAGE_ARGUMENTS;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASS_T16;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_ENROLLMENT;
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


public class ClassCreateCommandTest {

    @Test
    public void execute() {
        //TBC
    }
    @Test
    public void equals() {
        final ClassCreateCommand standardCommand = new ClassCreateCommand
                (new Classroom(new ClassName(VALID_CLASS_T16),
                        new ClassModule(VALID_MODULE_CODE),
                        new Enrollment(VALID_MAX_ENROLLMENT)));
        // same values -> returns true
        ClassCreateCommand commandWithSameValues = new ClassCreateCommand(
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
