package com.t13g2.forum.logic.commands;

import static java.util.Objects.requireNonNull;

import com.t13g2.forum.commons.core.EventsCenter;
import com.t13g2.forum.commons.events.model.ShowAnnouncementEvent;
import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.forum.Announcement;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

//@@author xllx1
/**
 * Allow user to check if there is any announcement
 */
public class CheckAnnouncmentCommand extends Command {
    public static final String COMMAND_WORD = "checkAnnounce";

    public static final String MESSAGE_SUCCESS = "Showing new announcement: %1$s";
    public static final String MESSAGE_FAIL = "There is no announcement at the moment";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Announcement announcement = null;
        // if user has not login or is not admin, then throw exception
        if (!Context.getInstance().isLoggedIn()) {
            throw new CommandException(User.MESSAGE_NOT_LOGIN);
        }

        Announcement latestAnnouncement = null;
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            latestAnnouncement = unitOfWork.getAnnouncementRepository().getLatestAnnouncement();
        } catch (EntityDoesNotExistException e) {
            latestAnnouncement = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (latestAnnouncement != null) {
            EventsCenter.getInstance().post(new ShowAnnouncementEvent(latestAnnouncement.getTitle(),
                latestAnnouncement.getContent()));
        } else {
            throw new CommandException(MESSAGE_FAIL);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, latestAnnouncement));
    }
}
