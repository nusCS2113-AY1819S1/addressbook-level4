package com.t13g2.forum.storage.forum;

import java.util.Optional;

import com.t13g2.forum.model.forum.Announcement;

/**
 *
 */
public class AnnouncementRepository extends BaseRepository implements IAnnouncementRepository {
    public AnnouncementRepository(IForumBookStorage forumBookStorage) {
        super(forumBookStorage);
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
    public Announcement getLatestAnnouncement() throws EntityDoesNotExistException {
        Optional<Announcement> announcement = forumBookStorage.getAnnouncements().getList().stream()
            .min((o1, o2) -> o2.getCreated().compareTo(o1.getCreated()));
        if (!announcement.isPresent()) {
            throw new EntityDoesNotExistException("No Announcement found");
        }
        return announcement.get();
    }
}
