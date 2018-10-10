package com.t13g2.forum.storage.forum;

import java.util.List;

import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.model.forum.Module;

public class ForumThreadRepository extends BaseRepository implements IForumThreadRepository {
    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public int addThread(ForumThread forumThread) {
        return 0;
    }

    @Override
    public void updateThread(ForumThread forumThread) {

    }

    @Override
    public ForumThread getThread(int forumThreadId) {
        return null;
    }

    @Override
    public List<ForumThread> getThreadsByModule(int moduleId) {
        return null;
    }

    @Override
    public List<ForumThread> getThreadsByModule(Module module) {
        return null;
    }

    @Override
    public void deleteThread(int forumThreadId) {

    }

    @Override
    public void deleteThread(ForumThread forumThread) {

    }
}
