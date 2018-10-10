package com.t13g2.forum.logic.commands;

import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.model.Model;

/**
 * Allow user to check if there is any announcement
 */
public class CheckAnnouncmentCommand extends Command {
    public static final String COMMAND_WORD = "checkAnnounce";

    public static final String MESSAGE_SUCCESS = "Showing new announcement!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
