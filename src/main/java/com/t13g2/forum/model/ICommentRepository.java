//@@author Meowzz95
package com.t13g2.forum.model;

import java.util.List;

import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

/**
 *Provides APIs to manipulate {@link Comment}
 */
public interface ICommentRepository {
    /**
     * Adds an {@link Comment} into database
     *
     * @param comment
     * @return Id of the object added
     */
    int addComment(Comment comment);

    /**
     * Deletes an {@link Comment}
     * @param comment
     */
    void deleteComment(Comment comment);

    /**
     * Deletes an {@link Comment}
     * @param commentId
     */
    void deleteComment(int commentId);

    /**
     * Gets an {@link Comment} by its object Id
     * @param commentId
     * @return the {@link Comment} queried
     * @throws EntityDoesNotExistException
     */
    Comment getComment(int commentId) throws EntityDoesNotExistException;

    /**
     * Gets all comments under a certain thread
     * @param threadId
     * @return a list of {@link Comment}
     */
    List<Comment> getCommentsByThread(int threadId);

    /**
     * Gets all comments under a certain thread
     * @param forumThread
     * @return a list of {@link Comment}
     */
    List<Comment> getCommentsByThread(ForumThread forumThread);
}
