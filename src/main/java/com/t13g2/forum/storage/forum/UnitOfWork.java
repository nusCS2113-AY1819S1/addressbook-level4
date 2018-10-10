package com.t13g2.forum.storage.forum;

/**
 *
 */
public class UnitOfWork implements IUnitOfWork, AutoCloseable {
    private IAnnouncementRepository announcementRepository;
    private ICommentRepository commentRepository;
    private IForumThreadRepository forumThreadRepository;
    private ModuleRepository moduleRepository;
    private UserRepository userRepository;

    public UnitOfWork() {
        announcementRepository = new AnnouncementRepository();
        commentRepository = new CommentRepository();
        forumThreadRepository = new ForumThreadRepository();
        moduleRepository = new ModuleRepository();
        userRepository = new UserRepository();
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

    }

    @Override
    public void rollBack() {

    }

    @Override
    public void close() throws Exception {

    }
}

