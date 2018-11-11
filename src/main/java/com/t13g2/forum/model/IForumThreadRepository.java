//@@author Meowzz95
package com.t13g2.forum.model;

import java.util.List;

import com.sun.istack.NotNull;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.model.forum.Module;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

/**
 * Provides APIs to manipulate {@link ForumThread}
 */
public interface IForumThreadRepository {
    /**
     * Adds an {@link ForumThread} into database
     *
     * @param forumThread
     * @return object Id added
     */
    int addThread(ForumThread forumThread);

    /**
     * Update an {@link ForumThread}
     * @param forumThread
     */
    void updateThread(@NotNull ForumThread forumThread);

    /**
     * Gets a thread by its Id
     * @param forumThreadId
     * @return the {@link ForumThread} queried
     * @throws EntityDoesNotExistException
     */
    ForumThread getThread(int forumThreadId) throws EntityDoesNotExistException;

    /**
     * Gets all threads under a certain module
     * @param moduleId
     * @return a list of {@link ForumThread}
     */
    List<ForumThread> getThreadsByModule(int moduleId);

    /**
     * Gets all threads under a certain module
     * @param module
     * @return a list of {@link ForumThread}
     */
    List<ForumThread> getThreadsByModule(Module module);

    /**
     * Deletes an {@link ForumThread}
     * @param forumThreadId
     */
    void deleteThread(int forumThreadId);

    /**
     * Deletes an {@link ForumThread}
     * @param forumThread
     */
    void deleteThread(ForumThread forumThread);


}
