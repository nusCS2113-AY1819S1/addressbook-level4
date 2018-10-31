package seedu.address.logic.commands;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleManager;

/**
 * Searches for modules in Trajectory that match the given keywords.
 */
public class ModuleFindCommand extends Command {

    public static final String COMMAND_WORD = "module find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all modules whose module code or module name "
            + "contain any of the specified keywords (case-insensitive) and displays them as a list.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " cs2113 engineering structures";

    private static final String MESSAGE_SUCCESS = "Found %1$s module(s).";

    private final List<String> keywords;

    public ModuleFindCommand(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Module> foundModules = ModuleManager.getInstance().searchModulesWithKeywords(keywords);
        return new CommandResult(String.format(MESSAGE_SUCCESS, foundModules.size())
                + "\n" + ModuleListCommand.getPrintableModuleList(foundModules));
    }
}
