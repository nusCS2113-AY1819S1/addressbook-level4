package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.classroom.Attendance;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ClassroomManager;

/**
 * Mark the class attendance list for a specified student for the system.
 */
public class ClassAddStudentAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "class markattendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Mark the class attendance list for a specified student"
            + " for the system. "
            + "Parameters: "
            + PREFIX_CLASS_NAME + "CLASS_NAME "
            + PREFIX_MODULE_CODE + "MODULE_NAME "
            + PREFIX_MATRIC + "MATRIC_NO\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLASS_NAME + "T16 "
            + PREFIX_MODULE_CODE + "CG1111 "
            + PREFIX_MATRIC + "A6942069M";

    public static final String MESSAGE_SUCCESS = "Student is marked present: %1$s"
            + ", Class: %2$s"
            + ", Module code: %3$s";
    private static final String MESSAGE_FAIL = "Class belonging to module not found!";

    private static final String MESSAGE_DUPLICATE_CLASSROOM_STUDENT_ATTENDANCE = "This student already"
            + " present in class: %1$s";

    private Classroom classToMarkAttendance;
    private final String className;
    private final String moduleCode;
    private final String matricNo;
    private final String date;
    private Attendance attendance;


    public ClassAddStudentAttendanceCommand(String className, String moduleCode, String matricNo) {
        requireAllNonNull(className, moduleCode);
        this.className = className;
        this.moduleCode = moduleCode;
        this.matricNo = matricNo;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.now();
        date = dtf.format(localDate);
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

        classToMarkAttendance = classroomManager.findClassroom(className, moduleCode);
        if (classToMarkAttendance == null) {
            return new CommandResult(MESSAGE_FAIL);
        }

        attendance = classroomManager.findAttendanceForClass(classToMarkAttendance, date);
        if (attendance == null) {
            attendance = new Attendance(date);
        }

        if (classroomManager.isDuplicateClassroomStudentAttendance(classToMarkAttendance, matricNo, date)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_CLASSROOM_STUDENT_ATTENDANCE, matricNo));
        }

        attendance.getStudentsPresent().add(matricNo);
        classroomManager.markStudentAttendance(classToMarkAttendance, attendance);
        classroomManager.saveClassroomAttendanceList();

        return new CommandResult(String.format(MESSAGE_SUCCESS, matricNo,
                classToMarkAttendance.getClassName(), classToMarkAttendance.getModuleCode()));
    }
}
