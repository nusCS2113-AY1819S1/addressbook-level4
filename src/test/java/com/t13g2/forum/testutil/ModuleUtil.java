package com.t13g2.forum.testutil;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_MODULE_TITLE;

import com.t13g2.forum.logic.commands.CreateModuleCommand;
import com.t13g2.forum.model.forum.Module;

/**
 * A utility class for Module.
 */
public class ModuleUtil {

    /**
     * Returns an create command string for adding the {@code module}.
     */
    public static String getCreateCommand(Module module) {
        return CreateModuleCommand.COMMAND_WORD + " " + getModuleDetails(module);
    }

    /**
     * Returns the part of command string for the given {@code module}'s details.
     */
    public static String getModuleDetails(Module module) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_MODULE_CODE + module.getModuleCode() + " ");
        sb.append(PREFIX_MODULE_TITLE + module.getTitle() + " ");
        return sb.toString();
    }
}
