package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CourseAddCommand.MESSAGE_SUCCESS;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.StorageController;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseCode;
import seedu.address.model.course.CourseName;
import seedu.address.model.course.FacultyName;
import seedu.address.testutil.CourseBuilder;

/**
 * This is a test class for CourseAddCommand
 */
public class CourseAddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullCourse_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CourseAddCommand(null);
    }

    @Test
    public void execute_addSuccessful() {

        StorageController.enterTestMode();
        final CourseCode courseCode = new CourseCode("CES");
        final CourseName courseName = new CourseName("Computer Engineering");
        final FacultyName faculty = new FacultyName("School of Computing");

        assertCommandSuccess(new CourseAddCommand(new Course(courseCode, courseName, faculty)), new CommandHistory(),
                String.format(MESSAGE_SUCCESS, courseName));
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

    }

    @Test
    public void execute_duplicateCourse_throwsCommandException() throws Exception {
        StorageController.enterTestMode();
        Course validCourse = new CourseBuilder().build();
        CourseAddCommand addCommand = new CourseAddCommand(validCourse);


        thrown.expect(CommandException.class);
        thrown.expectMessage(CourseAddCommand.MESSAGE_DUPLICATE_COURSE);
        addCommand.execute(null, commandHistory);
        addCommand.execute(null, commandHistory);


    }

    @Test
    public void equals() {
        Course ceg = new CourseBuilder().withCourseName("CEG").build();
        Course cs = new CourseBuilder().withCourseName("CS").build();

        CourseAddCommand addCegCommand = new CourseAddCommand(ceg);
        CourseAddCommand addCsCommand = new CourseAddCommand(cs);

        assertTrue(addCegCommand.equals(addCegCommand));

        CourseAddCommand addCegCommandCopy = new CourseAddCommand(ceg);
        assertTrue(addCegCommand.equals(addCegCommand));
        assertFalse(addCegCommand.equals(1));
        assertFalse(addCegCommand.equals(null));
        assertFalse(addCegCommand.equals(addCsCommand));


    }
}


