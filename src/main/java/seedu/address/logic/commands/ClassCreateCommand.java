package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class ClassCreateCommand extends Command{
    public static final String COMMAND_WORD = "classcreate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a class and assigns it to a module "
            + " for the system. "
            + "Parameters: "
            + PREFIX_CLASSNAME + "CLASS_NAME "
            + PREFIX_MODULECODE + "COURSE_NAME "
            + PREFIX_MAXENROLLMENT + "ENROLLMENT_SIZE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLASSNAME + "T16 "
            + PREFIX_MODULECODE + "CG1111 "
            + PREFIX_MAXENROLLMENT + "20";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Class create command not implemented yet.";

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
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
