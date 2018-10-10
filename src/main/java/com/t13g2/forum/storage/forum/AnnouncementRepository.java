package com.t13g2.forum.storage.forum;

import com.t13g2.forum.model.forum.Announcement;

public class AnnouncementRepository extends BaseRepository implements IAnnouncementRepository {
    @Override
    public void commit() {
        forumBookStorage.saveAnnouncement();
    }

    @Override
    public void rollback() {

    }

    @Override
    public int addAnnouncement(Announcement announcement) {
        forumBookStorage.getAnnouncements().getList().add(announcement);
        return announcement.getId();
    }

    @Override
    public void deleteAnnouncement(Announcement announcement) {

    }

    @Override
    public void deleteAnnouncement(int announcementId) {

    }

    @Override
    public Announcement getAnnouncement(int announcementId) {
        return null;
    }

    @Override
    public Announcement getLatestAnnouncement() {
        return null;
    }
}
