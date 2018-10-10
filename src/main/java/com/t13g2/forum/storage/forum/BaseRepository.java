package com.t13g2.forum.storage.forum;

/**
 *
 */
public abstract class BaseRepository {
    protected IForumBookStorage forumBookStorage;

    public BaseRepository(IForumBookStorage forumBookStorage) {
        this.forumBookStorage = forumBookStorage;
    }

    public abstract void commit();

    public abstract void rollback();
}
