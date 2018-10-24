package com.t13g2.forum.storage.forum;

import java.util.Objects;
import java.util.Optional;

import com.sun.istack.NotNull;
import com.t13g2.forum.commons.util.Extensions;
import com.t13g2.forum.model.forum.User;

/**
 *
 */
public class UserRepository extends BaseRepository implements IUserRepository {
    public UserRepository(IForumBookStorage forumBookStorage) {
        super(forumBookStorage);
    }

    @Override
    public int addUser(@NotNull User user) {
        Objects.requireNonNull(user, "user can't be null");
        forumBookStorage.getUsers().getList().add(user);
        forumBookStorage.getUsers().setDirty();
        return user.getId();
    }

    @Override
    public void updateUser(@NotNull User user) {
        Objects.requireNonNull(user, "user can't be null");
        Extensions.updateObjectInList(forumBookStorage.getUsers().getList(), user);
        forumBookStorage.getUsers().setDirty();
    }

    @Override
    public void deleteUser(@NotNull User user) {
        Objects.requireNonNull(user, "user can't be null");
        this.deleteUser(user.getId());
    }

    @Override
    public void deleteUser(int userId) {
        this.removeById(forumBookStorage.getUsers().getList(), userId);
        forumBookStorage.getUsers().setDirty();
    }

    @Override
    public User getUser(int userId) throws EntityDoesNotExistException {
        return this.getById(forumBookStorage.getUsers().getList(), userId);
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
