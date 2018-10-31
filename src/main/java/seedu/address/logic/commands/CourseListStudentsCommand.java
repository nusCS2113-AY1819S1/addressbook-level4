package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.course.CourseManager;

/**
 * Lists students registered to each course within Trajectory.
 */
public class CourseListStudentsCommand extends Command {

    public static final String COMMAND_WORD = "course liststudentsbycourses";

    public static final String MESSAGE_SUCCESS = "Listed all students ordered by courses";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        CourseManager cm = CourseManager.getInstance();
        return new CommandResult(MESSAGE_SUCCESS + "\n" + "", cm.getOrderedByCourseRepresentation());
    }
}
