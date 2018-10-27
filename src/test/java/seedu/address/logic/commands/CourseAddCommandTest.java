package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.CommandHistory;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseCode;
import seedu.address.model.course.CourseName;
import seedu.address.model.course.FacultyName;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CourseAddCommand.MESSAGE_SUCCESS;

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
       final CourseCode courseCode = new CourseCode("CEG");
       final CourseName courseName = new CourseName("Computer Engineering");
       final FacultyName faculty = new FacultyName("School of Computing");

       assertCommandSuccess(new CourseAddCommand(new Course(courseCode,courseName,faculty)), new CommandHistory(),
                String.format(MESSAGE_SUCCESS, courseName));
       assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }
}
