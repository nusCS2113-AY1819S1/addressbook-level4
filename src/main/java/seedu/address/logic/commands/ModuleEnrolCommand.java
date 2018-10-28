package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleManager;
import seedu.address.model.person.Person;
import seedu.address.model.student.StudentManager;

/**
 * Enrols a student in a particular module in Trajcetory.
 */
public class ModuleEnrolCommand extends Command {

    public static final String COMMAND_WORD = "module enrol";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enrols students into the specified module using "
            + "their matriculation number.\n"
            + "Parameters: "
            + PREFIX_MODULE_CODE + "MODULE_CODE "
            + PREFIX_MATRIC + "MATRIC_NUMBER\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MATRIC + "A0161234B";

    public static final String MESSAGE_MODULE_NOT_FOUND = "Module with code %s doesn't exist in Trajectory!";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student with matric no. %s doesn't exist in Trajectory!";
    public static final String MESSAGE_ENROL_SUCCESS = "Enrolled %1$s (%2$s) in %3$s %4$s.";

    private final ModuleCode moduleCode;
    private final String studentMatricNo;

    public ModuleEnrolCommand(ModuleCode moduleCode, String studentMatricNo) {
        this.moduleCode = moduleCode;
        this.studentMatricNo = studentMatricNo;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        ModuleManager moduleManager = ModuleManager.getInstance();

        if (!moduleManager.doesModuleExist(moduleCode.moduleCode)) {
            throw new CommandException(String.format(MESSAGE_MODULE_NOT_FOUND, moduleCode.moduleCode));
        }
        if (!StudentManager.getInstance().doesStudentExistForGivenMatricNo(studentMatricNo)) {
            throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, studentMatricNo));
        }

        Module moduleToEnrol = moduleManager.getModuleByModuleCode(moduleCode.moduleCode);
        Person studentToEnrol = StudentManager.getInstance().retrieveStudentByMatricNo(studentMatricNo);

        moduleManager.enrolStudentInModule(moduleToEnrol, studentToEnrol);
        moduleManager.saveModuleList();

        return new CommandResult(String.format(MESSAGE_ENROL_SUCCESS,
                studentToEnrol.getName().fullName,
                studentToEnrol.getMatricNo(),
                moduleToEnrol.getModuleCode().moduleCode,
                moduleToEnrol.getModuleName().moduleName));
    }
}
