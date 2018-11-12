package com.t13g2.forum.storage;

import com.t13g2.forum.storage.forum.AnnouncementStorage;
import com.t13g2.forum.storage.forum.CommentStorage;
import com.t13g2.forum.storage.forum.ForumThreadStorage;
import com.t13g2.forum.storage.forum.IForumBookStorage;
import com.t13g2.forum.storage.forum.IStorage;
import com.t13g2.forum.storage.forum.ModuleStorage;
import com.t13g2.forum.storage.forum.UserStorage;

public class ForumBookStorageStub implements IForumBookStorage {
    private AnnouncementStorage announcementStorage;
    private ForumThreadStorage forumThreadStorage;
    private UserStorage userStorage;
    private ModuleStorage moduleStorage;
    private CommentStorage commentStorage;

    private IStorage underlyingStorage;

    public ForumBookStorageStub() {
        loadAnnouncement();
        loadComment();
        loadForumThread();
        loadUser();
        loadModule();
        underlyingStorage = StorageStub.getInstance();
    }

    @Override
    public void commit() {

    }

    @Override
    public void save(Class clazz) {

    }

    @Override
    public void load(Class clazz) {

    }

    @Override
    public void saveAnnouncement() {

    }

    @Override
    public void loadAnnouncement() {
        announcementStorage = new AnnouncementStorage();
    }

    @Override
    public void saveComment() {

    }

    @Override
    public void loadComment() {
        commentStorage = new CommentStorage();
    }

    @Override
    public void saveForumThread() {

    }

    @Override
    public void loadForumThread() {
        forumThreadStorage = new ForumThreadStorage();
    }

    @Override
    public void saveUser() {

    }

    @Override
    public void loadUser() {
        userStorage = new UserStorage();
    }

    @Override
    public void saveModule() {

    }

    @Override
    public void loadModule() {
        moduleStorage = new ModuleStorage();
    }

    @Override
    public boolean isFresh() {
        return false;
    }

    @Override
    public AnnouncementStorage getAnnouncements() {
        return announcementStorage;
    }

    @Override
    public ForumThreadStorage getForumThreads() {
        return forumThreadStorage;
    }

    @Override
    public UserStorage getUsers() {
        return userStorage;
    }

    @Override
    public ModuleStorage getModules() {
        return moduleStorage;
    }

    @Override
    public CommentStorage getComments() {
        return commentStorage;
    }
}
