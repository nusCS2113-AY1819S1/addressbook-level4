//@@Meowzz95
package com.t13g2.forum.model;

import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

/**
 *
 */
public interface IUserRepository {
    int addUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    void deleteUser(int userId);

    User getUser(int userId) throws EntityDoesNotExistException;

    User getUserByUsername(String username) throws EntityDoesNotExistException;


    User authenticate(String username, String password) throws EntityDoesNotExistException;

}
