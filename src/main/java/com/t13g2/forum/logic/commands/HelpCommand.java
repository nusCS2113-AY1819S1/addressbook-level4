package com.t13g2.forum.logic.commands;

import com.t13g2.forum.commons.core.EventsCenter;
import com.t13g2.forum.commons.events.ui.ShowHelpRequestEvent;
import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }
}
