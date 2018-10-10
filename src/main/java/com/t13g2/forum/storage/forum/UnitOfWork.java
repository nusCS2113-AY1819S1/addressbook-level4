package com.t13g2.forum.storage.forum;

/**
 *
 */
public class UnitOfWork implements IUnitOfWork,AutoCloseable {
    private IForumBookStorage forumBookStorage;

    private AnnouncementRepository announcementRepository;
    private ICommentRepository commentRepository;
    private IForumThreadRepository forumThreadRepository;
    private ModuleRepository moduleRepository;
    private UserRepository userRepository;

    public UnitOfWork() {
        forumBookStorage = new ForumBookStorage(new FileStorage());

        announcementRepository = new AnnouncementRepository(forumBookStorage);
        commentRepository = new CommentRepository(forumBookStorage);
        forumThreadRepository = new ForumThreadRepository(forumBookStorage);
        moduleRepository = new ModuleRepository(forumBookStorage);
        userRepository = new UserRepository(forumBookStorage);
    }

    public IAnnouncementRepository getAnnouncementRepository() {
        return announcementRepository;
    }

    public ICommentRepository getCommentRepository() {
        return commentRepository;
    }

    public IForumThreadRepository getForumThreadRepository() {
        return forumThreadRepository;
    }

    public ModuleRepository getModuleRepository() {
        return moduleRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Override
    public void commit() {
        announcementRepository.commit();
    }

    @Override
    public void rollBack() {

    }

    @Override
    public void close() throws Exception {

    }
}

