package com.t13g2.forum.storage;

import com.t13g2.forum.storage.forum.AnnouncementStorage;
import com.t13g2.forum.storage.forum.CommentStorage;
import com.t13g2.forum.storage.forum.ForumBookStorage;
import com.t13g2.forum.storage.forum.ForumThreadStorage;
import com.t13g2.forum.storage.forum.IForumBookStorage;
import com.t13g2.forum.storage.forum.IStorage;
import com.t13g2.forum.storage.forum.ModuleStorage;
import com.t13g2.forum.storage.forum.UserStorage;

/**
 * Defines a stub for ForumBookStorage
 */
public class ForumBookStorageStub implements IForumBookStorage {
    private ForumBookStorage forumBookStorage;

    public ForumBookStorageStub(IStorage storage) {
        forumBookStorage = new ForumBookStorage(storage);
    }

    @Override
    public void commit() {
        forumBookStorage.commit();
    }

    @Override
    public void save(Class clazz) {
        forumBookStorage.save(clazz);
    }

    @Override
    public void load(Class clazz) {
        forumBookStorage.load(clazz);
    }

    @Override
    public void saveAnnouncement() {
        forumBookStorage.saveAnnouncement();
    }

    @Override
    public void loadAnnouncement() {
        forumBookStorage.loadAnnouncement();
    }

    @Override
    public void saveComment() {
        forumBookStorage.saveComment();
    }

    @Override
    public void loadComment() {
        forumBookStorage.loadComment();
    }

    @Override
    public void saveForumThread() {
        forumBookStorage.saveForumThread();
    }

    @Override
    public void loadForumThread() {
        forumBookStorage.loadForumThread();
    }

    @Override
    public void saveUser() {
        forumBookStorage.saveUser();
    }

    @Override
    public void loadUser() {
        forumBookStorage.loadUser();
    }

    @Override
    public void saveModule() {
        forumBookStorage.saveModule();
    }

    @Override
    public void loadModule() {
        forumBookStorage.loadModule();
    }

    @Override
    public boolean isFresh() {
        return forumBookStorage.isFresh();
    }

    @Override
    public AnnouncementStorage getAnnouncements() {
        return forumBookStorage.getAnnouncements();
    }

    @Override
    public ForumThreadStorage getForumThreads() {
        return forumBookStorage.getForumThreads();
    }

    @Override
    public UserStorage getUsers() {
        return forumBookStorage.getUsers();
    }

    @Override
    public ModuleStorage getModules() {
        return forumBookStorage.getModules();
    }

    @Override
    public CommentStorage getComments() {
        return forumBookStorage.getComments();
    }
}
