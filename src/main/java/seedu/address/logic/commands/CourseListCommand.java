package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseManager;


/**
 * Lists all persons in the address book to the user.
 */
public class CourseListCommand extends Command {

    public static final String COMMAND_WORD = "course list";

    public static final String MESSAGE_SUCCESS = "Listed all courses";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        CourseManager cm = new CourseManager();
        StringBuilder sb = new StringBuilder();

        for (Course c: cm.getCourses()) {
            sb.append("Course Name:");
            sb.append(c.getCourseName() + "\n");
            sb.append("Course Code: ");
            sb.append(c.getCourseCode() + "\n");
            sb.append("School/Faculty: ");
            sb.append(c.getFacultyName() + "\n");
            sb.append("\n");


        }
        return new CommandResult(MESSAGE_SUCCESS + "\n" + sb.toString());
    }
}
