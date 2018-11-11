//@@author Meowzz95
package com.t13g2.forum.model;

import java.util.List;
import java.util.Optional;

import com.t13g2.forum.model.forum.BaseModel;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;
import com.t13g2.forum.storage.forum.IForumBookStorage;

/**
 * Defines a base class for all entity repositories
 */
public abstract class BaseRepository {
    protected IForumBookStorage forumBookStorage;

    /**
     * Creates a new BaseRepository instance.
     *
     * @param forumBookStorage of type IForumBookStorage
     */
    public BaseRepository(IForumBookStorage forumBookStorage) {
        this.forumBookStorage = forumBookStorage;
    }


    /**
     * Helper method to get an entity by its id
     *
     * @param list
     * @param id   of type int
     * @return T
     * @throws EntityDoesNotExistException when
     */
    protected <T extends BaseModel> T getById(List<T> list, int id) throws EntityDoesNotExistException {
        Optional<T> result = list.stream().filter(item -> item.getId() == id).findFirst();
        if (!result.isPresent()) {
            throw new EntityDoesNotExistException("Entity does not exist");
        }
        return result.get();
    }

    /**
     * Helper method to remove an entity from a list by id
     *
     * @param list
     * @param id of type int
     */
    protected <T extends BaseModel> void removeById(List<T> list, int id) {
        list.removeIf(item -> item.getId() == id);
    }
}
