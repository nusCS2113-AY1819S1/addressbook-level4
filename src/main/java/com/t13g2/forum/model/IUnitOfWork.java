//@@Meowzz95
package com.t13g2.forum.model;

/**
 *
 */
public interface IUnitOfWork {
    void commit();

    void rollBack();

    IAnnouncementRepository getAnnouncementRepository();

    ICommentRepository getCommentRepository();

    IForumThreadRepository getForumThreadRepository();

    IModuleRepository getModuleRepository();

    IUserRepository getUserRepository();
}
