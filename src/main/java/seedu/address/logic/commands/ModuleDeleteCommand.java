package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleManager;

/**
 * Deletes a module identified using its module code.
 */
public class ModuleDeleteCommand extends Command {

    public static final String COMMAND_WORD = "module delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the module identified by its module code.\n"
            + "Parameters: "
            + PREFIX_MODULE_CODE + "MODULE_CODE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2113";

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted module: %1$s";

    private final String targetModuleCode;

    public ModuleDeleteCommand(String moduleCode) {
        this.targetModuleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        ModuleManager moduleManager = new ModuleManager();

        Module moduleToDelete = moduleManager.getModuleByModuleCode(targetModuleCode);
        moduleManager.deleteModule(moduleToDelete);
        moduleManager.saveModuleList();

        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleDeleteCommand // instanceof handles nulls
                && targetModuleCode.equals(((ModuleDeleteCommand) other).targetModuleCode)); // state check
    }
}
