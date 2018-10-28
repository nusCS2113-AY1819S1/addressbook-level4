package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.course.CourseManager;



/**
 * Lists all courses within Trajectory.
 */
public class CourseListCommand extends Command {

    public static final String COMMAND_WORD = "course list";

    public static final String MESSAGE_SUCCESS = "Listed all courses";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        CourseManager cm = CourseManager.getInstance();
        return new CommandResult(MESSAGE_SUCCESS + "\n" + "", cm.getTableRepresentation());
    }
}
