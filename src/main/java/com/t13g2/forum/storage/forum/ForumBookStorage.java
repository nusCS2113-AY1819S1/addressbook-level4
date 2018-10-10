package com.t13g2.forum.storage.forum;

import java.util.ArrayList;
import java.util.List;

import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.model.forum.User;

public class ForumBookStorage implements IForumBookStorage {
    protected IStorage underlyingStorage;

    private AnnouncementStorage announcements;
    private List<Comment> comments;
    private List<ForumThread> forumThreads;
    private List<Module> modules;
    private List<User> users;

    public ForumBookStorage(IStorage underlyingStorage) {
        this.underlyingStorage=underlyingStorage;

        this.announcements = new AnnouncementStorage();
        loadAnnouncement();
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
        underlyingStorage.write(announcements);
    }

    @Override
    public void loadAnnouncement() {
        announcements = (AnnouncementStorage) underlyingStorage.read(AnnouncementStorage.class);
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
    public AnnouncementStorage getAnnouncements() {
        return announcements;
    }


}
