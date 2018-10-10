package com.t13g2.forum.storage.forum;

import java.util.List;
import java.util.Optional;

import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.model.forum.Module;

public class ForumThreadRepository extends BaseRepository implements IForumThreadRepository {
    public ForumThreadRepository(IForumBookStorage forumBookStorage) {
        super(forumBookStorage);
    }

    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public int addThread(ForumThread forumThread) {
        forumBookStorage.getForumThreads().getList().add(forumThread);
        return forumThread.getId();
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
        List<ForumThread> pointer = forumBookStorage.getForumThreads().getList();
        Optional<ForumThread> toBeDeleted = pointer.stream().filter(forumThread -> forumThread.getId() == forumThreadId).findFirst();
        toBeDeleted.ifPresent(pointer::remove);
    }

    @Override
    public void deleteThread(ForumThread forumThread) {
        this.deleteThread(forumThread.getId());
    }
}
