//@@author Meowzz95
package com.t13g2.forum.model;

/**
 * Provides a transaction API to manipulate application database
 */
public interface IUnitOfWork {
    /**
     * Saves all changes
     */
    void commit();

    /**
     * Revert all changes
     */
    void rollBack();

    /**
     * Gets {@link IAnnouncementRepository}
     *
     * @return {@link IAnnouncementRepository}
     */
    IAnnouncementRepository getAnnouncementRepository();

    /**
     * Gets {@link ICommentRepository}
     * @return {@link ICommentRepository}
     */
    ICommentRepository getCommentRepository();

    /**
     * Gets {@link IForumThreadRepository}
     * @return {@link IForumThreadRepository}
     */
    IForumThreadRepository getForumThreadRepository();

    /**
     * Gets {@link IModuleRepository}
     * @return {@link IModuleRepository}
     */
    IModuleRepository getModuleRepository();

    /**
     * Gets {@link IUserRepository}
     * @return {@link IUserRepository}
     */
    IUserRepository getUserRepository();
}
