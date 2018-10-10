package com.t13g2.forum.storage.forum;

import java.util.List;

import com.sun.istack.NotNull;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.model.forum.Module;

/**
 *
 */
public interface IForumThreadRepository {
    int addThread(ForumThread forumThread);

    void updateThread(@NotNull ForumThread forumThread);

    ForumThread getThread(int forumThreadId);

    List<ForumThread> getThreadsByModule(int moduleId);

    List<ForumThread> getThreadsByModule(Module module);

    void deleteThread(int forumThreadId);

    void deleteThread(ForumThread forumThread);


}
