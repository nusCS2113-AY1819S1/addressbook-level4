package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ClassroomManager;
import seedu.address.ui.HtmlTableProcessor;

/**
 * Lists all the class from the classroom list.
 */
public class ClassListCommand extends Command {

    public static final String COMMAND_WORD = "class list";

    public static final String MESSAGE_SUCCESS = "Listed all classes";
    public static final String HTML_TABLE_TITLE_STUDENT = "Assigned student(s) - %1$s";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        ClassroomManager classroomManager = ClassroomManager.getInstance();
        return new CommandResult(MESSAGE_SUCCESS + "\n",
                classroomManager.getClassroomHtmlRepresentation());
    }
}
