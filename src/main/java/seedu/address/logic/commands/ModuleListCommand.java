package seedu.address.logic.commands;

import java.util.Iterator;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
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
        List<Module> moduleList = ModuleManager.getInstance().getModules();
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, ModuleManager.getInstance().getModules().size())
                + "\n" + getPrintableModuleList(moduleList));
    }

    /**
     * Converts a list of modules into a print-friendly string.
     */
    public static String getPrintableModuleList(List<Module> moduleList) {
        StringBuilder sb = new StringBuilder();

        for (Iterator<Module> iterator = moduleList.iterator(); iterator.hasNext();) {
            Module m = iterator.next();
            sb.append("Module Code: ");
            sb.append(m.getModuleCode()).append("\n");
            sb.append("Module Name: ");
            sb.append(m.getModuleName());
            if (iterator.hasNext()) {
                sb.append("\n").append("\n");
            }
        }

        return sb.toString();
    }
}
