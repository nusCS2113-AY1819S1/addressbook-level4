//@@Meowzz95
package com.t13g2.forum.storage.forum;

import java.util.Objects;
import java.util.Optional;

import com.sun.istack.NotNull;
import com.t13g2.forum.model.forum.Announcement;

/**
 *
 */
public class AnnouncementRepository extends BaseRepository implements IAnnouncementRepository {
    public AnnouncementRepository(IForumBookStorage forumBookStorage) {
        super(forumBookStorage);
    }

    @Override
    public int addAnnouncement(@NotNull Announcement announcement) {
        Objects.requireNonNull(announcement, "announcement can't be null");
        forumBookStorage.getAnnouncements().getList().add(announcement);
        forumBookStorage.getAnnouncements().setDirty();
        return announcement.getId();
    }

    @Override
    public void deleteAnnouncement(@NotNull Announcement announcement) {
        Objects.requireNonNull(announcement, "announcement can't be null");
        this.deleteAnnouncement(announcement.getId());
    }

    @Override
    public void deleteAnnouncement(int announcementId) {
        forumBookStorage.getAnnouncements().getList().removeIf(announcement -> announcement.getId() == announcementId);
        forumBookStorage.getAnnouncements().setDirty();
    }

    @Override
    public Announcement getAnnouncement(int announcementId) throws EntityDoesNotExistException {
        return this.getById(forumBookStorage.getAnnouncements().getList(), announcementId);
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
