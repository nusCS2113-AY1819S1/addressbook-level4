package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_CODE_CEG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_CODE_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_NAME_CS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CourseDeleteCommand.MESSAGE_DELETE_COURSE_SUCCESS;
import static seedu.address.logic.commands.CourseDeleteCommand.MESSAGE_NO_SUCH_COURSE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.StorageController;
import seedu.address.model.UserPrefs;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseManager;
import seedu.address.model.student.StudentManager;
import seedu.address.testutil.CourseBuilder;

/**
 * This is a test class for the CourseDeleteCommand.
 */
public class CourseDeleteCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullCourse_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CourseDeleteCommand(null);
    }


    @Test
    public void execute_deleteSuccessful() {

        StorageController.enterTestMode();
        StudentManager.getInstance().initializeModel(model);
        Course validCourse = new CourseBuilder().build();
        CourseManager.getInstance().addCourse(validCourse);
        CourseManager.getInstance().saveCourseList();

        assertCommandSuccess(new CourseDeleteCommand(VALID_COURSE_CODE_CS), new CommandHistory(),
                String.format(MESSAGE_DELETE_COURSE_SUCCESS, VALID_COURSE_NAME_CS));
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

    }

    @Test
    public void execute_deleteInvalidCourseCode() {

        StorageController.enterTestMode();
        StudentManager.getInstance().initializeModel(model);

        CourseDeleteCommand deleteCommand = new CourseDeleteCommand("POLSCI");

        assertCommandFailure(deleteCommand, model, commandHistory, MESSAGE_NO_SUCH_COURSE);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

    }

    @Test
    public void equals() {
        CourseDeleteCommand deleteFirstCommand = new CourseDeleteCommand(VALID_COURSE_CODE_CEG);
        CourseDeleteCommand deleteSecondCommand = new CourseDeleteCommand(VALID_COURSE_CODE_CS);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }






}
