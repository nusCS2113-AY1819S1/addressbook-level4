package com.t13g2.forum.storage;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.t13g2.forum.model.IUnitOfWork;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.forum.Announcement;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

/**
 * Defines unit test for UnitOfWork
 */
public class UnitOfWorkTest {

    @Rule
    public ExpectedException exceptionThrown = ExpectedException.none();

    @Test
    public void testAddUser() throws EntityDoesNotExistException {

        User user = getSampleUser();
        StorageStub storage = StorageStub.getInstance();
        storage.refreshInstantce();

        IUnitOfWork unitOfWork = new UnitOfWork(storage);

        unitOfWork.getUserRepository().addUser(user);
        unitOfWork.commit();

        IUnitOfWork unitOfWorkCopy = new UnitOfWork(storage);

        User copy = unitOfWorkCopy.getUserRepository().getUser(user.getId());
        checkUserEqual(user, copy);
    }

    @Test
    public void testUpdateUser() throws EntityDoesNotExistException {
        User user = getSampleUser();
        StorageStub storage = StorageStub.getInstance();
        storage.refreshInstantce();

        IUnitOfWork unitOfWork = new UnitOfWork(storage);

        unitOfWork.getUserRepository().addUser(user);
        unitOfWork.commit();

        IUnitOfWork unitOfWork1 = new UnitOfWork(storage);

        User copy = unitOfWork1.getUserRepository().getUser(user.getId());
        copy.setPhone("22222222");
        unitOfWork1.getUserRepository().updateUser(copy);

        IUnitOfWork unitOfWork2 = new UnitOfWork(storage);
        User updated = unitOfWork2.getUserRepository().getUser(user.getId());
        checkUserEqual(user, updated);
    }

    @Test
    public void testDeleteUser() throws EntityDoesNotExistException {
        User user = getSampleUser();
        StorageStub storage = StorageStub.getInstance();
        storage.refreshInstantce();

        IUnitOfWork unitOfWork = new UnitOfWork(storage);

        unitOfWork.getUserRepository().addUser(user);
        unitOfWork.commit();

        IUnitOfWork unitOfWork1 = new UnitOfWork(storage);

        User copy = unitOfWork1.getUserRepository().getUser(user.getId());
        unitOfWork1.getUserRepository().deleteUser(copy);
        unitOfWork1.commit();

        IUnitOfWork unitOfWork2 = new UnitOfWork(storage);
        exceptionThrown.expect(EntityDoesNotExistException.class);
        unitOfWork2.getUserRepository().getUser(user.getId());
    }

    @Test
    public void testLatestAnnouncement() throws EntityDoesNotExistException {
        Announcement announcement1 = new Announcement("Earlier announcement", "Content");
        Announcement announcement2 = new Announcement("Later announcement", "Content");

        StorageStub storage = StorageStub.getInstance();
        storage.refreshInstantce();
        IUnitOfWork unitOfWork1 = new UnitOfWork(storage);
        unitOfWork1.getAnnouncementRepository().addAnnouncement(announcement1);
        unitOfWork1.getAnnouncementRepository().addAnnouncement(announcement2);
        unitOfWork1.commit();

        IUnitOfWork unitOfWork2 = new UnitOfWork(storage);
        Announcement latest = unitOfWork2.getAnnouncementRepository().getLatestAnnouncement();
        Assert.assertEquals(latest.getId(), announcement2.getId());
        Assert.assertEquals(latest.getTitle(), announcement2.getTitle());
        Assert.assertEquals(latest.getContent(), announcement2.getContent());

    }


    private User getSampleUser() {
        return new User("username", "password", true, false, "Email@1.com", "12345678");
    }

    /**
     * Checks if two user are the same
     *
     * @param user1
     * @param user2
     */
    private void checkUserEqual(User user1, User user2) {
        Assert.assertEquals(user1.getUsername(), user2.getUsername());
        Assert.assertEquals(user1.getPassword(), user2.getPassword());
        Assert.assertEquals(user1.isAdmin(), user2.isAdmin());
        Assert.assertEquals(user1.isBlock(), user2.isBlock());
        Assert.assertEquals(user1.getEmail(), user2.getEmail());
        Assert.assertEquals(user1.getPhone(), user2.getPhone());
    }
}
