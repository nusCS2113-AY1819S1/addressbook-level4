package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.time.LocalDate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.classroom.Attendance;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ClassroomManager;
import seedu.address.model.module.ModuleManager;
import seedu.address.model.student.StudentManager;

/**
 * Mark the class attendance list for a specified student for the system.
 */
public class ClassAddStudentAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "class markattendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Mark the class attendance list for a specified student"
            + " for the system. "
            + "Parameters: "
            + PREFIX_CLASS_NAME + "CLASS_NAME "
            + PREFIX_MODULE_CODE + "MODULE_CODE "
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
    private static final String MESSAGE_INVALID_STUDENT = "Student does not exist";
    private static final String MESSAGE_MODULE_CODE_INVALID = "Module code does not exist";

    private final String className;
    private final String moduleCode;
    private final String matricNo;
    private final String date;

    public ClassAddStudentAttendanceCommand(String className, String moduleCode, String matricNo) {
        requireAllNonNull(className, moduleCode, matricNo);
        this.className = className;
        this.moduleCode = moduleCode;
        this.matricNo = matricNo;
        LocalDate localDate = LocalDate.now();
        date = Attendance.DATE_FORMATTER.format(localDate);
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
        ModuleManager moduleManager = ModuleManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();

        if (!moduleManager.doesModuleExist(moduleCode)) {
            throw new CommandException(MESSAGE_MODULE_CODE_INVALID);
        }

        if (!studentManager.doesStudentExistForGivenMatricNo(matricNo)) {
            throw new CommandException(MESSAGE_INVALID_STUDENT);
        }

        Classroom classToMarkAttendance = classroomManager.findClassroom(className, moduleCode);
        if (classToMarkAttendance == null) {
            throw new CommandException(MESSAGE_FAIL);
        }

        Attendance attendance = classroomManager.findAttendanceForClass(classToMarkAttendance, date);

        if (classroomManager.isDuplicateClassroomStudentAttendance(classToMarkAttendance, matricNo, date)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_CLASSROOM_STUDENT_ATTENDANCE, matricNo));
        }

        classroomManager.markStudentAttendance(classToMarkAttendance, attendance, matricNo);
        classroomManager.saveClassroomAttendanceList();

        return new CommandResult(String.format(MESSAGE_SUCCESS, matricNo,
                classToMarkAttendance.getClassName(), classToMarkAttendance.getModuleCode()));
    }
}
