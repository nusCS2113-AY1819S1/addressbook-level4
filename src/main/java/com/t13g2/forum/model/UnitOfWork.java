//@@Meowzz95
package com.t13g2.forum.model;

import com.t13g2.forum.storage.forum.ForumBookStorage;
import com.t13g2.forum.storage.forum.IForumBookStorage;
import com.t13g2.forum.storage.forum.IStorage;
import com.t13g2.forum.storage.forum.JsonFileStorage;
import com.t13g2.forum.storage.forum.SampleDataGenerator;

/**
 *
 */
public class UnitOfWork implements IUnitOfWork, AutoCloseable {
    private IForumBookStorage forumBookStorage;

    private IAnnouncementRepository announcementRepository;
    private ICommentRepository commentRepository;
    private IForumThreadRepository forumThreadRepository;
    private IModuleRepository moduleRepository;
    private IUserRepository userRepository;

    public UnitOfWork(IStorage storage) {
        forumBookStorage = new ForumBookStorage(storage);

        announcementRepository = new AnnouncementRepository(forumBookStorage);
        commentRepository = new CommentRepository(forumBookStorage);
        forumThreadRepository = new ForumThreadRepository(forumBookStorage);
        moduleRepository = new ModuleRepository(forumBookStorage);
        userRepository = new UserRepository(forumBookStorage);
    }

    public UnitOfWork() {
        this(new JsonFileStorage());
    }

    @Override
    public IAnnouncementRepository getAnnouncementRepository() {
        return announcementRepository;
    }

    @Override
    public ICommentRepository getCommentRepository() {
        return commentRepository;
    }

    @Override
    public IForumThreadRepository getForumThreadRepository() {
        return forumThreadRepository;
    }

    @Override
    public IModuleRepository getModuleRepository() {
        return moduleRepository;
    }

    @Override
    public IUserRepository getUserRepository() {
        return userRepository;
    }

    @Override
    public void commit() {

        forumBookStorage.commit();

    }

    @Override
    public void rollBack() {

    }

    @Override
    public void close() throws Exception {

    }

    /**
     *
     */
    public void init() {
        if (this.forumBookStorage.isFresh()) {
            new SampleDataGenerator(this).generate();
            this.commit();
        }
    }
}

