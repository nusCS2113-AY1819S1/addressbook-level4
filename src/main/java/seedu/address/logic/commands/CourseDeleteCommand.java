package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseManager;
import seedu.address.model.student.StudentManager;

/**
 * Deletes a course via a provided course code
 */
public class CourseDeleteCommand extends Command {
    public static final String COMMAND_WORD = "course delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the course based on the course code that was provided.\n"
            + "Parameters: " + PREFIX_COURSE_CODE + "COURSE_CODE\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_COURSE_CODE + "CS";


    public static final String MESSAGE_DELETE_COURSE_SUCCESS = "Deleted course: %1$s";
    public static final String MESSAGE_NO_SUCH_COURSE = "No such course exists.";
    public static final String MESSAGE_ATTACHED_STUDENTS = "You may not delete a course that has registered students.";


    private String courseCode;
    public CourseDeleteCommand(String courseCode) {
        requireNonNull(courseCode);
        this.courseCode = courseCode;
    }

    /**
     * Executes the deletion of a course.
     */
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        CourseManager cm = CourseManager.getInstance();
        if (!cm.hasCourse(courseCode)) {
            throw new CommandException(MESSAGE_NO_SUCH_COURSE);
        }
        if (StudentManager.getInstance().getStudentsInCourse(courseCode).size() > 0) {
            throw new CommandException(MESSAGE_ATTACHED_STUDENTS);
        }

        Course foundCourse = cm.getCourse(courseCode);
        cm.deleteCourse(foundCourse);
        cm.saveCourseList();

        return new CommandResult(String.format(MESSAGE_DELETE_COURSE_SUCCESS,
                foundCourse.getCourseName()), cm.getTableRepresentation());
    }


}
