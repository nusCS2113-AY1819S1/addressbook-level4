//@@author Meowzz95
package com.t13g2.forum.model;

import com.t13g2.forum.model.forum.Announcement;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

/**
 * Provides APIs to manipulate {@link Announcement}
 */
public interface IAnnouncementRepository {
    /**
     * Adds an {@link Announcement} into database
     *
     * @param announcement
     * @return Id of the object added
     */
    int addAnnouncement(Announcement announcement);

    /**
     * Deletes an {@link Announcement}
     * @param announcement
     */
    void deleteAnnouncement(Announcement announcement);

    /**
     * Deletes an {@link Announcement}
     * @param announcementId
     */
    void deleteAnnouncement(int announcementId);

    /**
     * Gets an {@link Announcement} by its object Id
     * @param announcementId
     * @return the {@link Announcement} queried
     * @throws EntityDoesNotExistException
     */
    Announcement getAnnouncement(int announcementId) throws EntityDoesNotExistException;

    /**
     * Gets the latest {@link Announcement}
     * @return latest {@link Announcement}
     * @throws EntityDoesNotExistException
     */
    Announcement getLatestAnnouncement() throws EntityDoesNotExistException;
}
