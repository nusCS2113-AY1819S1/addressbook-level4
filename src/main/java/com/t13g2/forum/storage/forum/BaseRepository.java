package com.t13g2.forum.storage.forum;

/**
 *
 */
public abstract class BaseRepository {
    private IForumBookStorage forumBookStorage;

    public BaseRepository() {
        this.forumBookStorage = new ForumBookStorage(new FileStorage());
    }
    public BaseRepository(IStorage underlyingStorage){
        this.forumBookStorage = new ForumBookStorage(underlyingStorage);
    }
    public abstract void commit();
    public abstract void rollback();
}
