package com.t13g2.forum.storage.forum;

import java.util.Optional;

import com.t13g2.forum.model.forum.User;

/**
 *
 */
public class UserRepository extends BaseRepository implements IUserRepository {
    public UserRepository(IForumBookStorage forumBookStorage) {
        super(forumBookStorage);
    }

    @Override
    public int addUser(User user) {
        forumBookStorage.getUsers().getList().add(user);
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
    public User getUserByUsername(String username) throws EntityDoesNotExistException {
        Optional<User> userInDb = forumBookStorage.getUsers().getList().stream()
            .filter(user -> user.getUsername().equals(username)).findFirst();
        if (!userInDb.isPresent()) {
            throw new EntityDoesNotExistException("Username does not exist");
        }
        return userInDb.get();
    }

    @Override
    public User authenticate(String username, String password) throws EntityDoesNotExistException {
        User user = getUserByUsername(username);
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
