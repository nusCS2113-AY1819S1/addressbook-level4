package com.t13g2.forum.logic.commands;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;

/**
 *
 */
public class AdminRestrictiveCommand extends Command {
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }

    public boolean checkIsLogin() {
        return false;
    }
}
