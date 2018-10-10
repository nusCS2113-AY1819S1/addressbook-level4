package com.t13g2.forum.storage.forum;

/**
 *
 */

public interface IForumBookStorage {

    void save(Class clazz);
    void load(Class clazz);

    void saveAnnouncement();
    void loadAnnouncement();

    void saveComment();
    void loadComment();

    void saveForumThread();
    void loadForumThread();

    void saveUser();
    void loadUser();

    AnnouncementStorage getAnnouncements();


}
