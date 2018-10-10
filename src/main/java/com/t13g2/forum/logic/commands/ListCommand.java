package com.t13g2.forum.logic.commands;

import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.Announcement;
import com.t13g2.forum.storage.forum.IAnnouncementRepository;
import com.t13g2.forum.storage.forum.UnitOfWork;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            unitOfWork.getAnnouncementRepository().addAnnouncement(new Announcement("a", "b"));
            unitOfWork.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            IAnnouncementRepository announcementRepository = unitOfWork.getAnnouncementRepository();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
