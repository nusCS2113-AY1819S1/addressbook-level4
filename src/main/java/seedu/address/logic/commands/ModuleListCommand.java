package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.Module;
import seedu.address.model.StorageController;

/**
 * Lists all modules in Trajectory to the user.
 */
public class ModuleListCommand extends Command {

    public static final String COMMAND_WORD = "module list";

    public static final String MESSAGE_SUCCESS = "Listed %1$s module(s)";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        StorageController.retrieveData();

        StringBuilder sb = new StringBuilder();

        for (Module m: StorageController.getModuleStorage()) {
            sb.append("Module Name: ");
            sb.append(m.getModuleName() + "\n");
            sb.append("Module Code: ");
            sb.append(m.getModuleCode() + "\n");
            sb.append("\n");
        }

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, StorageController.getModuleStorage().size(), "s")
                + "\n" + sb.toString());
    }
}
