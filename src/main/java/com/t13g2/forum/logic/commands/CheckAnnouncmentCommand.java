package com.t13g2.forum.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.EventQueue;
import javax.swing.JOptionPane;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.Announcement;
import com.t13g2.forum.storage.forum.UnitOfWork;

//@@xllx1
/**
 * Allow user to check if there is any announcement
 */
public class CheckAnnouncmentCommand extends Command {
    public static final String COMMAND_WORD = "checkAnnounce";

    public static final String MESSAGE_SUCCESS = "Showing new announcement: %1$s";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        Announcement announcement = new Announcement();
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            announcement = unitOfWork.getAnnouncementRepository().getLatestAnnouncement();
            final String title = announcement.getTitle();
            final String content = announcement.getContent();
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    JOptionPane.showMessageDialog(null, content, title, JOptionPane.PLAIN_MESSAGE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, announcement));
    }
}
