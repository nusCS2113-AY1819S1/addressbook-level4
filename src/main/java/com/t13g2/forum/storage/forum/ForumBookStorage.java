package com.t13g2.forum.storage.forum;

import java.util.ArrayList;
import java.util.List;

import com.t13g2.forum.model.forum.Announcement;
import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.model.forum.User;

public class ForumBookStorage implements IForumBookStorage {
    private IStorage underlyingStorage;

    private List<Announcement> announcements;
    private List<Comment> comments;
    private List<ForumThread> forumThreads;
    private List<Module> modules;
    private List<User> users;

    public ForumBookStorage(IStorage underlyingStorage) {
        this.underlyingStorage=underlyingStorage;

        this.announcements = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.forumThreads = new ArrayList<>();
        this.modules = new ArrayList<>();
        this.users = new ArrayList<>();
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

    }

    @Override
    public void saveComment() {

    }

    @Override
    public void loadComment() {

    }

    @Override
    public void saveForumThread() {

    }

    @Override
    public void loadForumThread() {

    }

    @Override
    public void saveUser() {

    }

    @Override
    public void loadUser() {

    }

    @Override
    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    @Override
    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public List<ForumThread> getForumThreads() {
        return forumThreads;
    }

    @Override
    public List<Module> getModules() {
        return modules;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }
}
