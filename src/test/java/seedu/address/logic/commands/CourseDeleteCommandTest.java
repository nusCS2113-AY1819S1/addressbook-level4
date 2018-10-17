package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.CommandHistory;
import seedu.address.model.course.Course;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CourseAddCommand.MESSAGE_SUCCESS;

public class CourseDeleteCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullCourse_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CourseDeleteCommand(null);
    }

}
