//@@author Meowzz95
package com.t13g2.forum.storage.forum;

import com.t13g2.forum.model.forum.Announcement;

/**
 * Provides APIs to access entity repositories and handle load/save operation
 */
public class ForumBookStorage implements IForumBookStorage {
    protected IStorage underlyingStorage;

    private AnnouncementStorage announcements;
    private CommentStorage comments;
    private ForumThreadStorage forumThreads;
    private ModuleStorage modules;
    private UserStorage users;

    private boolean isFresh;


    public ForumBookStorage(IStorage underlyingStorage) {
        this.underlyingStorage = underlyingStorage;
        init();
    }

    /**
     * Initializes forum book storage
     */
    private void init() {
        loadAnnouncement();
        loadComment();
        loadForumThread();
        loadUser();
        loadModule();
    }

    /**
     * Saves all changes
     */
    @Override
    public void commit() {
        saveAnnouncement();
        saveComment();
        saveForumThread();
        saveUser();
        saveModule();
    }

    /**
     * Saves changes for the class
     *
     * @param clazz
     */
    @Override
    public void save(Class clazz) {

    }

    /**
     * Loads data for the class
     * @param clazz
     */
    @Override
    public void load(Class clazz) {

    }

    /**
     * Executes save on entity {@link Announcement}
     * Dirty check is done before saving
     */
    @Override
    public void saveAnnouncement() {
        if (announcements.isDirty()) {
            announcements.setFresh();
            underlyingStorage.write(announcements);
        }

    }

    /**
     * Executes load on entity {@link Announcement}
     * Dirty check is done before saving
     */
    @Override
    public void loadAnnouncement() {
        announcements = underlyingStorage.read(AnnouncementStorage.class);
        if (announcements == null) {
            announcements = new AnnouncementStorage();
            announcements.setDirty();
            saveAnnouncement();
        }
    }

    /**
     * Executes save on entity {@link com.t13g2.forum.model.forum.Comment}
     * Dirty check is done before saving
     */
    @Override
    public void saveComment() {
        if (comments.isDirty()) {
            comments.setFresh();
            underlyingStorage.write(comments);
        }
    }

    /**
     * Executes load on entity {@link com.t13g2.forum.model.forum.Comment}
     * Dirty check is done before saving
     */
    @Override
    public void loadComment() {
        comments = underlyingStorage.read(CommentStorage.class);
        if (comments == null) {
            comments = new CommentStorage();
            comments.setDirty();
            saveComment();
        }
    }

    /**
     * Executes save on entity {@link com.t13g2.forum.model.forum.ForumThread}
     * Dirty check is done before saving
     */
    @Override
    public void saveForumThread() {
        if (forumThreads.isDirty()) {
            forumThreads.setFresh();
            underlyingStorage.write(forumThreads);
        }

    }

    /**
     * Executes load on entity {@link com.t13g2.forum.model.forum.ForumThread}
     * Dirty check is done before saving
     */
    @Override
    public void loadForumThread() {
        forumThreads = underlyingStorage.read(ForumThreadStorage.class);
        if (forumThreads == null) {
            forumThreads = new ForumThreadStorage();
            forumThreads.setDirty();
            saveForumThread();
        }
    }


    /**
     * Executes save on entity {@link com.t13g2.forum.model.forum.User}
     * Dirty check is done before saving
     */
    @Override
    public void saveUser() {
        if (users.isDirty()) {
            users.setFresh();
            underlyingStorage.write(users);

        }
    }

    /**
     * Executes load on entity {@link com.t13g2.forum.model.forum.User}
     * Dirty check is done before saving
     */
    @Override
    public void loadUser() {
        users = underlyingStorage.read(UserStorage.class);
        if (users == null) {
            users = new UserStorage();
            users.setDirty();
            saveUser();
            this.isFresh = true;
        }
    }

    /**
     * Executes save on entity {@link com.t13g2.forum.model.forum.Module}
     * Dirty check is done before saving
     */
    @Override
    public void saveModule() {
        if (modules.isDirty()) {
            modules.setFresh();
            underlyingStorage.write(modules);
        }
    }

    /**
     * Executes load on entity {@link com.t13g2.forum.model.forum.Module}
     * Dirty check is done before saving
     */
    @Override
    public void loadModule() {
        modules = underlyingStorage.read(ModuleStorage.class);
        if (modules == null) {
            modules = new ModuleStorage();
            modules.setDirty();
            saveModule();
        }
    }

    /**
     * Gets announcement storage
     * @return {@link AnnouncementStorage}
     */
    @Override
    public AnnouncementStorage getAnnouncements() {
        return announcements;
    }

    /**
     * Gets threads storage
     * @return {@link ForumThreadStorage}
     */
    @Override
    public ForumThreadStorage getForumThreads() {
        return forumThreads;
    }

    /**
     * Gets user storage
     * @return {@link UserStorage}
     */
    @Override
    public UserStorage getUsers() {
        return users;
    }

    /**
     * Gets module storage
     * @return {@link ModuleStorage}
     */
    @Override
    public ModuleStorage getModules() {
        return modules;
    }

    /**
     * Gets comment storage
     * @return {@link CommentStorage}
     */
    @Override
    public CommentStorage getComments() {
        return comments;
    }

    /**
     * Checks if this is a fresh copy of the application
     * @return
     */
    public boolean isFresh() {
        return this.isFresh;
    }


}
