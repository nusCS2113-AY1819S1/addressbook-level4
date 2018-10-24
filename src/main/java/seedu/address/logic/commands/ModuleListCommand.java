package seedu.address.logic.commands;

import java.util.Iterator;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.StorageController;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleManager;

/**
 * Lists all modules in Trajectory to the user.
 */
public class ModuleListCommand extends Command {

    public static final String COMMAND_WORD = "module list";

    public static final String MESSAGE_SUCCESS = "Listed %1$s module(s).";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        ModuleManager moduleManager = new ModuleManager();

        StringBuilder sb = new StringBuilder();

        for (Iterator<Module> iter = moduleManager.getModules().iterator(); iter.hasNext();) {
            Module m = iter.next();
            sb.append("Module Code: ");
            sb.append(m.getModuleCode()).append("\n");
            sb.append("Module Name: ");
            sb.append(m.getModuleName());
            if (iter.hasNext()) {
                sb.append("\n").append("\n");
            }
        }

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, StorageController.getModuleStorage().size())
                + "\n" + sb.toString());
    }
}
