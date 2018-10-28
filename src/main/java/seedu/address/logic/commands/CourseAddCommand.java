package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_FACULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseManager;

/**
 * Adds a student to the address book.
 */
public class CourseAddCommand extends Command {

    public static final String COMMAND_WORD = "course add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a course into Trajectory. "
            + "Parameters: "
            + PREFIX_COURSE_CODE + "COURSE_CODE"
            + PREFIX_COURSE_NAME + "COURSE_NAME"
            + PREFIX_COURSE_FACULTY + "COURSE_ORIGIN_FACULTY"
            + "\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_COURSE_CODE + "CEG1 "
            + PREFIX_COURSE_NAME + "Computer Engineering "
            + PREFIX_COURSE_FACULTY + "School of Computing ";

    public static final String MESSAGE_SUCCESS = "New course added: %1$s";
    public static final String MESSAGE_DUPLICATE_COURSE = "This course already exists in Trajectory";

    private Course internalCourse;
    /**
     * Creates an CourseAddCommand to add the specified {@code Course}
     */
    public CourseAddCommand(Course course) {
        requireNonNull(course);
        internalCourse = course;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        CourseManager cm = CourseManager.getInstance();
        if (cm.hasCourse(internalCourse.getCourseCode().toString())) {
            throw new CommandException(MESSAGE_DUPLICATE_COURSE);
        }

        cm.addCourse(internalCourse);
        cm.saveCourseList();
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                internalCourse.getCourseName()), cm.getTableRepresentation());
    }

}
