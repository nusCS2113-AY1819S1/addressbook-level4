//@@author Meowzz95
package com.t13g2.forum.model;

import java.util.Objects;
import java.util.Optional;

import com.sun.istack.NotNull;
import com.t13g2.forum.model.forum.Announcement;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;
import com.t13g2.forum.storage.forum.IForumBookStorage;

/**
 * Provides APIs to manipulate {@link Announcement}
 */
public class AnnouncementRepository extends BaseRepository implements IAnnouncementRepository {
    /**
     * Instantiates a new Announcement repository.
     *
     * @param forumBookStorage the forum book storage
     */
    public AnnouncementRepository(IForumBookStorage forumBookStorage) {
        super(forumBookStorage);
    }


    /**
     * Adds an {@link Announcement} into database
     *
     * @param announcement
     * @return Id of the object added
     */
    @Override
    public int addAnnouncement(@NotNull Announcement announcement) {
        Objects.requireNonNull(announcement, "announcement can't be null");
        forumBookStorage.getAnnouncements().getList().add(announcement);
        forumBookStorage.getAnnouncements().setDirty();
        return announcement.getId();
    }

    /**
     * Deletes an {@link Announcement}
     * @param announcement
     */
    @Override
    public void deleteAnnouncement(@NotNull Announcement announcement) {
        Objects.requireNonNull(announcement, "announcement can't be null");
        this.deleteAnnouncement(announcement.getId());
    }

    /**
     * Deletes an {@link Announcement}
     * @param announcementId
     */
    @Override
    public void deleteAnnouncement(int announcementId) {
        forumBookStorage.getAnnouncements().getList().removeIf(announcement -> announcement.getId() == announcementId);
        forumBookStorage.getAnnouncements().setDirty();
    }

    /**
     * Gets an {@link Announcement} by its object Id
     * @param announcementId
     * @return the {@link Announcement} queried
     * @throws EntityDoesNotExistException
     */
    @Override
    public Announcement getAnnouncement(int announcementId) throws EntityDoesNotExistException {
        return this.getById(forumBookStorage.getAnnouncements().getList(), announcementId);
    }

    /**
     * Gets the latest {@link Announcement}
     * @return latest {@link Announcement}
     * @throws EntityDoesNotExistException
     */
    @Override
    public Announcement getLatestAnnouncement() throws EntityDoesNotExistException {
        Optional<Announcement> announcement = forumBookStorage.getAnnouncements().getList().stream()
            .min((o1, o2) -> o2.getCreated().compareTo(o1.getCreated()));
        if (!announcement.isPresent()) {
            throw new EntityDoesNotExistException("No Announcement found");
        }
        return announcement.get();
    }
}
