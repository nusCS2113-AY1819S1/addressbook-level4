//@@author Meowzz95
package com.t13g2.forum.model;

import java.util.Objects;
import java.util.Optional;

import com.sun.istack.NotNull;
import com.t13g2.forum.commons.util.Extensions;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;
import com.t13g2.forum.storage.forum.IForumBookStorage;

/**
 * Provides APIs to manipulate {@link User}
 */
public class UserRepository extends BaseRepository implements IUserRepository {
    /**
     * Creates a new BaseRepository instance.
     *
     * @param forumBookStorage of type IForumBookStorage
     */
    public UserRepository(IForumBookStorage forumBookStorage) {
        super(forumBookStorage);
    }

    /**
     * Adds an {@link User} into database
     *
     * @param user
     * @return Id of the object added
     */
    @Override
    public int addUser(@NotNull User user) {
        Objects.requireNonNull(user, "user can't be null");
        forumBookStorage.getUsers().getList().add(user);
        forumBookStorage.getUsers().setDirty();
        return user.getId();
    }

    /**
     * Updates an {@link User}
     * @param user
     */
    @Override
    public void updateUser(@NotNull User user) {
        Objects.requireNonNull(user, "user can't be null");
        Extensions.updateObjectInList(forumBookStorage.getUsers().getList(), user);
        forumBookStorage.getUsers().setDirty();
    }

    /**
     * Deletes an {@link User}
     * @param user
     */
    @Override
    public void deleteUser(@NotNull User user) {
        Objects.requireNonNull(user, "user can't be null");
        this.deleteUser(user.getId());
    }

    /**
     * Deletes an {@link User}
     * @param userId
     */
    @Override
    public void deleteUser(int userId) {
        this.removeById(forumBookStorage.getUsers().getList(), userId);
        forumBookStorage.getUsers().setDirty();
    }

    /**
     * Gets an {@link User} by its object Id
     * @param userId
     * @return the {@link User} queried
     * @throws EntityDoesNotExistException
     */
    @Override
    public User getUser(int userId) throws EntityDoesNotExistException {
        return this.getById(forumBookStorage.getUsers().getList(), userId);
    }

    /**
     * Gets a user by its username
     * @param username
     * @return user queried
     * @throws EntityDoesNotExistException
     */
    @Override
    public User getUserByUsername(String username) throws EntityDoesNotExistException {
        Optional<User> userInDb = forumBookStorage.getUsers().getList().stream()
            .filter(user -> user.getUsername().equals(username)).findFirst();
        if (!userInDb.isPresent()) {
            throw new EntityDoesNotExistException("Username does not exist");
        }
        return userInDb.get();
    }

    /**
     * Checks if the given username and password match with the record in the database
     * @param username
     * @param password
     * @return user matched
     * @throws EntityDoesNotExistException
     */
    @Override
    public User authenticate(String username, String password) throws EntityDoesNotExistException {
        User user = getUserByUsername(username);
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
