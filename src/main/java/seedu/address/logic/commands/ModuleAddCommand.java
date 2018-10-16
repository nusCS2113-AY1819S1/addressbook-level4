package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_NAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleManager;

/**
 * Adds a module to Trajectory
 */
public class ModuleAddCommand extends Command {

    public static final String COMMAND_WORD = "module add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module into Trajectory. "
            + "Parameters: "
            + PREFIX_MODULE_NAME + "MODULE_NAME "
            + PREFIX_MODULE_CODE + "MODULE_CODE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE_NAME + "Software Engineering & Object-Oriented Programming "
            + PREFIX_MODULE_CODE + "CS2113";

    public static final String MESSAGE_SUCCESS = "New module added: %1$s %2$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This module already exists in Trajectory";

    private final Module moduleToAdd;

    public ModuleAddCommand(Module module) {
        this.moduleToAdd = module;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        ModuleManager moduleManager = new ModuleManager();
        moduleManager.addModule(moduleToAdd);
        moduleManager.saveModuleList();

        return new CommandResult(String.format(MESSAGE_SUCCESS, moduleToAdd.getModuleCode(),
                moduleToAdd.getModuleName()));
    }
}
