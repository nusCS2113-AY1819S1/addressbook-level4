package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.classroom.Attendance;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ClassroomManager;
import seedu.address.ui.HtmlTableProcessor;

/**
 * Lists the attendance for a class in the system.
 */
public class ClassListStudentAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "class listattendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": list class attendance list for a specified class"
            + " for the system. "
            + "Parameters: "
            + PREFIX_CLASS_NAME + "CLASS_NAME "
            + PREFIX_MODULE_CODE + "MODULE_CODE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLASS_NAME + "T16 "
            + PREFIX_MODULE_CODE + "CG1111";

    public static final String MESSAGE_SUCCESS = "Attendance is listed for"
            + " Class: %1$s"
            + ", Module code: %2$s";
    private static final String MESSAGE_FAIL = "Class belonging to module not found!";
    private static final String HTML_TABLE_TITLE_ATTENDANCE = "Attendance list for %1$s, %2$s";

    private final String className;
    private final String moduleCode;

    public ClassListStudentAttendanceCommand(String className, String moduleCode) {
        requireAllNonNull(className, moduleCode);
        this.className = className;
        this.moduleCode = moduleCode;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model   {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        ClassroomManager classroomManager = ClassroomManager.getInstance();
        final StringBuilder builder = new StringBuilder();

        Classroom classToListAttendance = classroomManager.findClassroom(className, moduleCode);
        if (classToListAttendance == null) {
            return new CommandResult(MESSAGE_FAIL);
        }

        builder.append(HtmlTableProcessor.getH3Representation(String.format(HTML_TABLE_TITLE_ATTENDANCE,
                classToListAttendance.getClassName(), classToListAttendance.getModuleCode())));
        String studentStatus = "";
        for (Attendance attendance : classToListAttendance.getAttendanceList()) {
            builder.append(HtmlTableProcessor.renderTableStart(new ArrayList<>(
                    Collections.singletonList("Date of attendance"))));
            builder.append(HtmlTableProcessor.getTableItemStart());
            builder.append(HtmlTableProcessor
                    .renderTableItem(new ArrayList<>(Collections.singletonList(attendance.getDate()))));
            builder.append(HtmlTableProcessor.getTableItemEnd());

            builder.append(HtmlTableProcessor.renderTableStart(new ArrayList<>(Arrays
                    .asList("Matric No", "Status"))));

            builder.append(HtmlTableProcessor.getTableItemStart());
            for (String student : classToListAttendance.getStudents()) {
                studentStatus = "Absent";
                if (attendance.getStudentsPresent().contains(student)) {
                    studentStatus = "Present";
                }
                builder.append(HtmlTableProcessor
                        .renderTableItem(new ArrayList<>(Arrays
                                .asList(student,
                                        studentStatus))));

            }
            builder.append(HtmlTableProcessor.getTableItemEnd());
        }


        return new CommandResult(String.format(MESSAGE_SUCCESS + "\n", className, moduleCode),
                builder.toString());
    }
}
