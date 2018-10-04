package com.t13g2.forum.logic.commands;

import com.t13g2.forum.commons.core.EventsCenter;
import com.t13g2.forum.commons.events.ui.ExitAppRequestEvent;
import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
