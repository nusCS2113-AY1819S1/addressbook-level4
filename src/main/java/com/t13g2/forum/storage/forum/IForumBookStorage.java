package com.t13g2.forum.storage.forum;

/**
 *
 */
import java.util.List;

import com.t13g2.forum.model.forum.Announcement;
import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.model.forum.User;

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

    List<Announcement> getAnnouncements();
    List<Comment> getComments();
    List<ForumThread> getForumThreads();
    List<Module> getModules();
    List<User> getUsers();


}
