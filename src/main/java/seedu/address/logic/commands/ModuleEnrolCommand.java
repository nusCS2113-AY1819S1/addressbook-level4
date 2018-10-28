package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;

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

    private final ModuleCode moduleCode;
    private final String studentMatricNo;

    public ModuleEnrolCommand(ModuleCode moduleCode, String studentMatricNo) {
        this.moduleCode = moduleCode;
        this.studentMatricNo = studentMatricNo;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        return null;
    }
}
