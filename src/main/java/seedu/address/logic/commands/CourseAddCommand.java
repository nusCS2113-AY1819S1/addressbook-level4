package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSENAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACULTY;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Course;
import seedu.address.model.Model;
import seedu.address.model.StorageController;




/**
 * Adds a person to the address book.
 */
public class CourseAddCommand extends Command {

    public static final String COMMAND_WORD = "course add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a course into Trajectory. "
            + "Parameters: "
            + PREFIX_COURSECODE + "CEG1 "
            + PREFIX_COURSENAME + "Computer Engineering "
            + PREFIX_FACULTY + "School of Computing ";

    public static final String MESSAGE_SUCCESS = "New course added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This course already exists in Trajectory";

    private Course internalCourse;
    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public CourseAddCommand(Course course) {
        internalCourse = course;

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        StorageController.retrieveData();
        StorageController.getCourseStorage().add(new Course(internalCourse.getCourseCode(),
                internalCourse.getCourseName(), internalCourse.getFacultyName()));
        StorageController.storeData();
        return new CommandResult(String.format(MESSAGE_SUCCESS, internalCourse.getCourseName()));
    }

}
