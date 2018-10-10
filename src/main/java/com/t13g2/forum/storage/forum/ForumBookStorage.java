package com.t13g2.forum.storage.forum;

public class ForumBookStorage implements IForumBookStorage {
    protected IStorage underlyingStorage;

    private AnnouncementStorage announcements;
    private CommentStorage comments;
    private ForumThreadStorage forumThreads;
    private ModuleStorage modules;
    private UserStorage users;

    public ForumBookStorage(IStorage underlyingStorage) {
        this.underlyingStorage=underlyingStorage;

        this.announcements = new AnnouncementStorage();
        this.comments = new CommentStorage();
        this.forumThreads = new ForumThreadStorage();
        this.modules = new ModuleStorage();
        this.users = new UserStorage();

        init();
    }

    private void init() {
        loadAnnouncement();

    }

    @Override
    public void commit() {
        saveAnnouncement();
        saveComment();
        saveForumThread();
        saveUser();
        saveModule();
    }

    @Override
    public void save(Class clazz) {

    }

    @Override
    public void load(Class clazz) {

    }

    @Override
    public void saveAnnouncement() {
        underlyingStorage.write(announcements);
    }

    @Override
    public void loadAnnouncement() {
        announcements = (AnnouncementStorage) underlyingStorage.read(AnnouncementStorage.class);
        if (announcements == null) {
            announcements = new AnnouncementStorage();
            saveAnnouncement();
        }
    }

    @Override
    public void saveComment() {
        underlyingStorage.write(comments);
    }

    @Override
    public void loadComment() {
        comments = (CommentStorage) underlyingStorage.read(CommentStorage.class);
        if (comments == null) {
            comments = new CommentStorage();
            saveComment();
        }
    }

    @Override
    public void saveForumThread() {
        underlyingStorage.write(forumThreads);
    }

    @Override
    public void loadForumThread() {
        forumThreads = (ForumThreadStorage) underlyingStorage.read(ForumThreadStorage.class);
        if (forumThreads == null) {
            forumThreads = new ForumThreadStorage();
            saveForumThread();
        }
    }


    @Override
    public void saveUser() {
        underlyingStorage.write(users);
    }

    @Override
    public void loadUser() {
        users = (UserStorage) underlyingStorage.read(UserStorage.class);
        if (users == null) {
            users = new UserStorage();
            saveUser();
        }
    }

    @Override
    public void saveModule() {
        underlyingStorage.write(modules);
    }

    @Override
    public void loadModule() {
        modules = (ModuleStorage) underlyingStorage.read(ModuleStorage.class);
        if (modules == null) {
            modules = new ModuleStorage();
            saveModule();
        }
    }

    @Override
    public AnnouncementStorage getAnnouncements() {
        return announcements;
    }


}
