package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAXENROLLMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.classroom.ClassModule;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.Enrollment;

/**
 * Creates a class for a module.
 */
public class ClassCreateCommand extends Command {
    public static final String COMMAND_WORD = "classcreate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a class and assigns it to a module"
            + " for the system. "
            + "Parameters: "
            + PREFIX_CLASSNAME + "CLASS_NAME "
            + PREFIX_MODULECODE + "MODULE_NAME "
            + PREFIX_MAXENROLLMENT + "ENROLLMENT_SIZE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLASSNAME + "T16 "
            + PREFIX_MODULECODE + "CG1111 "
            + PREFIX_MAXENROLLMENT + "20";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Class create command not implemented yet.";

    public static final String MESSAGE_ARGUMENTS = "Class name: %1$s, ClassModule code: %2$s, Enrollment size: %3$s";
    private final Classroom className;
    private final ClassModule moduleCode;
    private final Enrollment maxEnrollment;

    /**
     * @param index  of the person in the filtered person list to edit the remark
     * @param remark of the person to be updated to
     */
    public ClassCreateCommand(Classroom className, ClassModule moduleCode, Enrollment maxEnrollment) {
        requireAllNonNull(className, moduleCode, maxEnrollment);
        this.className = className;
        this.moduleCode = moduleCode;
        this.maxEnrollment = maxEnrollment;
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
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, className, moduleCode, maxEnrollment));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof ClassCreateCommand)) {
            return false;
        }
        // state check
        ClassCreateCommand e = (ClassCreateCommand) other;
        return className.equals(e.className)
                && moduleCode.equals(e.moduleCode)
                && maxEnrollment.equals(e.maxEnrollment);
    }
}
