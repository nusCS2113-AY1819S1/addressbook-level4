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
import seedu.address.ui.HtmlTableProcessor;

/**
 * Adds a person to the address book.
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
    public static final String MESSAGE_DUPLICATE_PERSON = "This course already exists in Trajectory";

    private Course internalCourse;
    /**
     * Creates an StudentAddCommand to add the specified {@code Person}
     */
    public CourseAddCommand(Course course) {
        requireNonNull(course);
        internalCourse = course;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        CourseManager cm = CourseManager.getInstance();
        cm.addCourse(internalCourse);
        cm.saveCourseList();
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                internalCourse.getCourseName()),
                HtmlTableProcessor.getH3Representation(String.format(MESSAGE_SUCCESS, internalCourse.getCourseName())));
    }

}
