package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_ANNOUNCE_CONTENT;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_ANNOUNCE_TITLE;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.Announcement;

/**
 * Announce new announcement.
 */
public class AnnounceCommand extends Command {

    public static final String COMMAND_WORD = "announce";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Post new announcement. "
        + "Parameters: "
        + PREFIX_ANNOUNCE_TITLE + "TITLE "
        + PREFIX_ANNOUNCE_CONTENT + "PHONE "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_ANNOUNCE_TITLE + "Urgent!"
        + PREFIX_ANNOUNCE_CONTENT + "System maintenance from tomorrow 3PM to 5PM.";

    public static final String MESSAGE_SUCCESS = "New announcement posted: %1$s";

    private final Announcement toAnnounce;

    /**
     * Creates an AnnounceCommand to announce the specified {@code toAnnounce}.
     */
    public AnnounceCommand(Announcement toAnnounce) {
        requireNonNull(toAnnounce);
        this.toAnnounce = toAnnounce;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.addAnnounceToStorage(toAnnounce);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAnnounce));
    }
}

