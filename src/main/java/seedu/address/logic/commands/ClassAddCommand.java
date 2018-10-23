package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAXENROLLMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ClassroomManager;

/**
 * Creates a class for a module.
 */
public class ClassAddCommand extends Command {
    public static final String COMMAND_WORD = "class add";

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

    public static final String MESSAGE_SUCCESS = "New class added: %1$s,"
            + " ClassModule code: %2$s,"
            + " Enrollment size: %3$s";

    public static final String MESSAGE_DUPLICATE_CLASSROOM = "This classroom already exists in Trajectory";

    private final Classroom classToCreate;

    /**
     * Command creates a classroom to be added.
     */
    public ClassAddCommand(Classroom classRoom) {
        requireAllNonNull(classRoom);
        this.classToCreate = classRoom;
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

        if (classroomManager.hasClassroom(classToCreate)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLASSROOM);
        }

        classroomManager.addClassroom(classToCreate);
        classroomManager.saveClassroomList();

        return new CommandResult(String.format(MESSAGE_SUCCESS, classToCreate.getClassName(),
                classToCreate.getModuleCode(), classToCreate.getMaxEnrollment()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClassAddCommand // instanceof handles nulls
                && classToCreate.equals(((ClassAddCommand) other).classToCreate));

    }
}
