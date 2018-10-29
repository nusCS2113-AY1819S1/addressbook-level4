package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleManager;
import seedu.address.model.person.MatricNo;
import seedu.address.model.person.Person;
import seedu.address.model.student.StudentManager;

/**
 * Enrols a student in a particular module in Trajcetory.
 */
public class ModuleEnrolCommand extends Command {

    public static final String COMMAND_WORD = "module enrol";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enrols a student into the specified module using "
            + "their matriculation number.\n"
            + "Parameters: "
            + PREFIX_MODULE_CODE + "MODULE_CODE "
            + PREFIX_MATRIC + "MATRIC_NUMBER\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2113 "
            + PREFIX_MATRIC + "A0161234B";

    private static final String MESSAGE_MODULE_NOT_FOUND = "Module with code %s doesn't exist in Trajectory!";
    private static final String MESSAGE_STUDENT_NOT_FOUND = "Student with matric no. %s doesn't exist in Trajectory!";
    private static final String MESSAGE_ALREADY_ENROLLED = "%1$s (%2$s) is already enrolled in %3$s %4$s.";
    private static final String MESSAGE_ENROL_SUCCESS = "Enrolled %1$s (%2$s) in %3$s %4$s.";
    private static final String MESSAGE_COMMAND_SUMMARY =
            "%1$s student%2$s enrolled, %3$s student%4$s already enrolled, %5$s invalid matric number%6$s.";

    private final ModuleCode moduleCode;
    private final Set<MatricNo> matricNoSet;

    public ModuleEnrolCommand(ModuleCode moduleCode, Set<MatricNo> matricNoSet) {
        this.moduleCode = moduleCode;
        this.matricNoSet = matricNoSet;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        ModuleManager moduleManager = ModuleManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();

        if (!moduleManager.doesModuleExist(moduleCode.moduleCode)) {
            throw new CommandException(String.format(MESSAGE_MODULE_NOT_FOUND, moduleCode.moduleCode));
        }

        int numEnrolled = 0;
        int numDuplicates = 0;
        int numInvalid = 0;
        StringBuilder sb = new StringBuilder();
        for (MatricNo matricNo : matricNoSet) {
            if (studentManager.doesStudentExistForGivenMatricNo(matricNo.matricNo)) {
                Module moduleToEnrol = moduleManager.getModuleByModuleCode(moduleCode.moduleCode);
                Person studentToEnrol = studentManager.retrieveStudentByMatricNo(matricNo.matricNo);

                if (moduleManager.isStudentEnrolledInModule(moduleToEnrol, studentToEnrol)) {
                    sb.append(String.format(MESSAGE_ALREADY_ENROLLED,
                            studentToEnrol.getName().fullName,
                            studentToEnrol.getMatricNo().matricNo,
                            moduleToEnrol.getModuleCode().moduleCode,
                            moduleToEnrol.getModuleName().moduleName));

                    numDuplicates++;
                } else {
                    moduleManager.enrolStudentInModule(moduleToEnrol, studentToEnrol);

                    sb.append(String.format(MESSAGE_ENROL_SUCCESS,
                            studentToEnrol.getName().fullName,
                            studentToEnrol.getMatricNo(),
                            moduleToEnrol.getModuleCode().moduleCode,
                            moduleToEnrol.getModuleName().moduleName));

                    numEnrolled++;
                }
            } else {
                sb.append(String.format(MESSAGE_STUDENT_NOT_FOUND, matricNo));
                numInvalid++;
            }
            sb.append("\n");
        }

        moduleManager.saveModuleList();
        sb.append(String.format(MESSAGE_COMMAND_SUMMARY,
                numEnrolled, determinePluralOrSingular(numEnrolled),
                numDuplicates, determinePluralOrSingular(numDuplicates),
                numInvalid, determinePluralOrSingular(numInvalid)));

        return new CommandResult(sb.toString());
    }

    private char determinePluralOrSingular(int number) {
        return number == 0 || number > 1 ? 's' : Character.MIN_VALUE;
    }
}
