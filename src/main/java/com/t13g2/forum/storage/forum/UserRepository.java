package com.t13g2.forum.storage.forum;

import com.t13g2.forum.model.forum.User;

public class UserRepository extends BaseRepository implements IUserRepository {
    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public int addUser(User user) {
        return 0;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public void deleteUser(int userId) {

    }

    @Override
    public User getUser(int userId) {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public User authenticate(String username, String password) {
        return null;
    }
}
