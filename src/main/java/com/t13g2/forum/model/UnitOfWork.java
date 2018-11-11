//@@author Meowzz95
package com.t13g2.forum.model;

import com.t13g2.forum.storage.forum.ForumBookStorage;
import com.t13g2.forum.storage.forum.IForumBookStorage;
import com.t13g2.forum.storage.forum.IStorage;
import com.t13g2.forum.storage.forum.JsonFileStorage;

/**
 * Provides a transaction API to manipulate application database
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

    /**
     * Gets {@link IAnnouncementRepository}
     *
     * @return {@link IAnnouncementRepository}
     */
    @Override
    public IAnnouncementRepository getAnnouncementRepository() {
        return announcementRepository;
    }

    /**
     * Gets {@link ICommentRepository}
     * @return {@link ICommentRepository}
     */
    @Override
    public ICommentRepository getCommentRepository() {
        return commentRepository;
    }

    /**
     * Gets {@link IForumThreadRepository}
     * @return {@link IForumThreadRepository}
     */
    @Override
    public IForumThreadRepository getForumThreadRepository() {
        return forumThreadRepository;
    }

    /**
     * Gets {@link IModuleRepository}
     * @return {@link IModuleRepository}
     */
    @Override
    public IModuleRepository getModuleRepository() {
        return moduleRepository;
    }

    /**
     * Gets {@link IUserRepository}
     * @return {@link IUserRepository}
     */
    @Override
    public IUserRepository getUserRepository() {
        return userRepository;
    }

    /**
     * Saves all changes
     */
    @Override
    public void commit() {

        forumBookStorage.commit();

    }

    /**
     * Reverts all changes
     */
    @Override
    public void rollBack() {

    }

    @Override
    public void close() throws Exception {

    }

    /**
     * Generates sample data is this is a fresh copy of the application
     */
    public void init() {
        if (this.forumBookStorage.isFresh()) {
            new SampleDataGenerator(this).generate();
            this.commit();
        }
    }
}

