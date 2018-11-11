//@@author Meowzz95
package com.t13g2.forum.model;

import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

/**
 * Provides APIs to manipulate {@link User}
 */
public interface IUserRepository {
    /**
     * Adds an {@link User} into database
     *
     * @param user
     * @return Id of the object added
     */
    int addUser(User user);

    /**
     * Updates an {@link User}
     * @param user
     */
    void updateUser(User user);

    /**
     * Deletes an {@link User}
     * @param user
     */
    void deleteUser(User user);

    /**
     * Deletes an {@link User}
     * @param userId
     */
    void deleteUser(int userId);

    /**
     * Gets an {@link User} by its object Id
     * @param userId
     * @return the {@link User} queried
     * @throws EntityDoesNotExistException
     */
    User getUser(int userId) throws EntityDoesNotExistException;

    /**
     * Gets a user by its username
     * @param username
     * @return user queried
     * @throws EntityDoesNotExistException
     */
    User getUserByUsername(String username) throws EntityDoesNotExistException;

    /**
     * Checks if the given username and password match with the record in the database
     * @param username
     * @param password
     * @return user matched
     * @throws EntityDoesNotExistException
     */
    User authenticate(String username, String password) throws EntityDoesNotExistException;

}
