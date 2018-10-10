package com.t13g2.forum.storage.forum;

import com.t13g2.forum.model.forum.User;

/**
 *
 */
public interface IUserRepository {
    int addUser(User user);

    void updateUser(User user);

    void deleteUser(User user);
    void deleteUser(int userId);

    User getUser(int userId);

    User authenticate(String username, String password);
}
