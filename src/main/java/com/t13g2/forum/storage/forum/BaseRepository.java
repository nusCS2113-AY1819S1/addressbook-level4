package com.t13g2.forum.storage.forum;

import java.util.List;
import java.util.Optional;

import com.t13g2.forum.model.forum.BaseModel;

/**
 *
 */
public abstract class BaseRepository {
    protected IForumBookStorage forumBookStorage;

    public BaseRepository(IForumBookStorage forumBookStorage) {
        this.forumBookStorage = forumBookStorage;
    }

    protected <T extends BaseModel> T getById(List<T> list, int id) throws EntityDoesNotExistException {
        Optional<T> result = list.stream().filter(item -> item.getId() == id).findFirst();
        if (!result.isPresent()) {
            throw new EntityDoesNotExistException("Entity does not exist");
        }
        return result.get();
    }

    protected <T extends BaseModel> void removeById(List<T> list, int id) {
        list.removeIf(item -> item.getId() == id);
    }
}
