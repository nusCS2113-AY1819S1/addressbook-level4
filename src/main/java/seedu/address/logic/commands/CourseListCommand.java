package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseManager;
import seedu.address.ui.HtmlTableProcessor;


/**
 * Lists all courses within Trajectory.
 */
public class CourseListCommand extends Command {

    public static final String COMMAND_WORD = "course list";

    public static final String MESSAGE_SUCCESS = "Listed all courses";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        CourseManager cm = new CourseManager();
        StringBuilder sb = new StringBuilder();

        sb.append(HtmlTableProcessor.getH3Representation("Course List"));
        sb.append(HtmlTableProcessor.renderTableStart(new ArrayList<String>(
                Arrays.asList("Course Name", "Course Code", "School/Faculty"))));

        sb.append(HtmlTableProcessor.getTableItemStart());
        for (Course c: cm.getCourses()) {
            sb.append(HtmlTableProcessor
                    .renderTableItem(new ArrayList<String>(Arrays
                            .asList(c.getCourseName(), c.getCourseCode(), c.getFacultyName()))));
        }
        sb.append(HtmlTableProcessor.getTableItemEnd());

        return new CommandResult(MESSAGE_SUCCESS + "\n" + "", sb.toString());
    }
}
