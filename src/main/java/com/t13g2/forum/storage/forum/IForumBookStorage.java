//@@author Meowzz95
package com.t13g2.forum.storage.forum;

import com.t13g2.forum.model.forum.Announcement;

/**
 * Provides APIs to access entity repositories and handle load/save operation
 */
public interface IForumBookStorage {

    /**
     * Saves all changes
     */
    void commit();

    /**
     * Saves changes for the class
     *
     * @param clazz
     */
    void save(Class clazz);

    /**
     * Loads data for the class
     * @param clazz
     */
    void load(Class clazz);

    /**
     * Executes save on entity {@link Announcement}
     * Dirty check is done before saving
     */
    void saveAnnouncement();

    /**
     * Executes load on entity {@link Announcement}
     * Dirty check is done before saving
     */
    void loadAnnouncement();

    /**
     * Executes save on entity {@link com.t13g2.forum.model.forum.Comment}
     * Dirty check is done before saving
     */
    void saveComment();

    /**
     * Executes load on entity {@link com.t13g2.forum.model.forum.Comment}
     * Dirty check is done before saving
     */
    void loadComment();

    /**
     * Executes save on entity {@link com.t13g2.forum.model.forum.ForumThread}
     * Dirty check is done before saving
     */
    void saveForumThread();

    /**
     * Executes load on entity {@link com.t13g2.forum.model.forum.ForumThread}
     * Dirty check is done before saving
     */
    void loadForumThread();

    /**
     * Executes save on entity {@link com.t13g2.forum.model.forum.User}
     * Dirty check is done before saving
     */
    void saveUser();

    /**
     * Executes load on entity {@link com.t13g2.forum.model.forum.User}
     * Dirty check is done before saving
     */
    void loadUser();

    /**
     * Executes save on entity {@link com.t13g2.forum.model.forum.Module}
     * Dirty check is done before saving
     */
    void saveModule();

    /**
     * Executes load on entity {@link com.t13g2.forum.model.forum.Module}
     * Dirty check is done before saving
     */
    void loadModule();

    /**
     * Checks if this is a fresh copy of the application
     * @return
     */
    boolean isFresh();

    /**
     * Gets announcement storage
     * @return {@link AnnouncementStorage}
     */
    AnnouncementStorage getAnnouncements();

    /**
     * Gets threads storage
     * @return {@link ForumThreadStorage}
     */
    ForumThreadStorage getForumThreads();

    /**
     * Gets user storage
     * @return {@link UserStorage}
     */
    UserStorage getUsers();

    /**
     * Gets module storage
     * @return {@link ModuleStorage}
     */
    ModuleStorage getModules();

    /**
     * Gets comment storage
     * @return {@link CommentStorage}
     */
    CommentStorage getComments();


}
