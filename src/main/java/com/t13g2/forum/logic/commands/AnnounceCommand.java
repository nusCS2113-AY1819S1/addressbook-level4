package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_ANNOUNCE_CONTENT;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_ANNOUNCE_TITLE;
import static java.util.Objects.requireNonNull;

import java.awt.EventQueue;
import javax.swing.JOptionPane;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.Announcement;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.storage.forum.Context;
import com.t13g2.forum.storage.forum.UnitOfWork;

//@@xllx1
/**
 * Announce new announcement.
 */
public class AnnounceCommand extends Command {

    public static final String COMMAND_WORD = "announce";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Post new announcement. "
        + "Parameters: "
        + PREFIX_ANNOUNCE_TITLE + "TITLE "
        + PREFIX_ANNOUNCE_CONTENT + "CONTENT "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_ANNOUNCE_TITLE + "Urgent! "
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
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        // if user has not login or is not admin, then throw exception
        if (Context.getInstance().getCurrentUser() == null) {
            throw new CommandException(User.MESSAGE_NOT_LOGIN);
        }
        if (!Context.getInstance().getCurrentUser().isAdmin()) {
            throw new CommandException(User.MESSAGE_NOT_ADMIN);
        }
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            toAnnounce.setCreatedByUserId(Context.getInstance().getCurrentUser().getId());
            unitOfWork.getAnnouncementRepository().addAnnouncement(toAnnounce);
            unitOfWork.commit();
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    JOptionPane.showMessageDialog(null, toAnnounce.getContent(),
                        toAnnounce.getTitle(), JOptionPane.PLAIN_MESSAGE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAnnounce));
    }
}

