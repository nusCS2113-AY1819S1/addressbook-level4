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
    private static final String HTML_TABLE_TITLE_STUDENT = "Assigned person(s) - %1$s";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        ClassroomManager classroomManager = ClassroomManager.getInstance();

        final StringBuilder builder = new StringBuilder();

        builder.append(HtmlTableProcessor.getH3Representation("Class List"));

        for (Classroom c : classroomManager.getClassroomList()) {
            builder.append(HtmlTableProcessor.renderTableStart(new ArrayList<String>(
                    Arrays.asList("Class Name", "Module Code", "Max Enrollment Size"))));
            builder.append(HtmlTableProcessor.getTableItemStart());
            builder.append(c.toClassHtmlString());
            if (!c.getStudents().isEmpty()) {
                builder.append(HtmlTableProcessor.renderTableStart(new ArrayList<>(
                        Collections.singletonList(String.format(HTML_TABLE_TITLE_STUDENT,
                                c.getClassName())))));
                builder.append(HtmlTableProcessor.getTableItemStart());
                for (String matricNo : c.getStudents()) {
                    builder.append(HtmlTableProcessor
                            .renderTableItem(new ArrayList<>(Collections.singletonList(matricNo))));
                }
                builder.append(HtmlTableProcessor.getTableItemEnd());
            }
        }
        builder.append(HtmlTableProcessor.getTableItemEnd());
        return new CommandResult(MESSAGE_SUCCESS + "\n", builder.toString());
    }
}
