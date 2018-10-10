package com.t13g2.forum.storage.forum;

import com.t13g2.forum.model.forum.Announcement;

public interface IAnnouncementRepository {
    int addAnnouncement(Announcement announcement);

    void deleteAnnouncement(Announcement announcement);

    void deleteAnnouncement(int announcementId);

    Announcement getAnnouncement(int announcementId);
}
