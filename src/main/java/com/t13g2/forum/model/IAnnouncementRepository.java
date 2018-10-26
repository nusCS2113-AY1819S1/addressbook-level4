//@@Meowzz95
package com.t13g2.forum.model;

import com.t13g2.forum.model.forum.Announcement;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

/**
 *
 */
public interface IAnnouncementRepository {
    int addAnnouncement(Announcement announcement);

    void deleteAnnouncement(Announcement announcement);

    void deleteAnnouncement(int announcementId);

    Announcement getAnnouncement(int announcementId) throws EntityDoesNotExistException;

    Announcement getLatestAnnouncement() throws EntityDoesNotExistException;
}
