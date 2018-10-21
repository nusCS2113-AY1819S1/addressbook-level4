package com.t13g2.forum.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import com.t13g2.forum.model.forum.Announcement;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.model.person.Person;
import com.t13g2.forum.model.person.UniquePersonList;
import com.t13g2.forum.storage.forum.Context;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;
import com.t13g2.forum.storage.forum.UnitOfWork;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class ForumBook implements ReadOnlyForumBook {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public ForumBook() {}

    /**
     * Creates an ForumBook using the Persons in the {@code toBeCopied}
     */
    public ForumBook(ReadOnlyForumBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code ForumBook} with {@code newData}.
     */
    public void resetData(ReadOnlyForumBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void updatePerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code ForumBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ForumBook // instanceof handles nulls
                && persons.equals(((ForumBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }

    //@@xllx1
    /**
     * User login to forum book.
     * The user must exist in the forum book.
     */
    public User userLogin(String userName, String userPassword) {
        User exist = null;
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            try {
                exist = unitOfWork.getUserRepository().authenticate(userName, userPassword);
                if (exist == null) {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Context.getInstance().setCurrentUser(exist);
        return exist;
    }

    /**
     * adds new announcement to storage by admin.
     * @param toAnnounce
     */
    public void addAnnouncement(Announcement toAnnounce) {
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            toAnnounce.setCreatedByUserId(Context.getInstance().getCurrentUser().getId());
            unitOfWork.getAnnouncementRepository().addAnnouncement(toAnnounce);
            unitOfWork.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * returns latest announcement.
     */
    public Announcement checkAnnounce() {
        Announcement announcement = null;
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            announcement = unitOfWork.getAnnouncementRepository().getLatestAnnouncement();
        } catch (EntityDoesNotExistException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return announcement;
    }

    /**
     * checks if user exists.
     */
    public User doesUserExist(String userName) {
        User user = null;
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            user = unitOfWork.getUserRepository().getUserByUsername(userName);
        } catch (EntityDoesNotExistException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * blocks the specific user.
     */
    public boolean blockUser(User user) {
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            if (user.getIsBlock()) {
                return false;
            } else {
                user.setIsBlock(true);
                unitOfWork.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * set or revert the user as admin.
     */
    public void setAdmin(User user) {
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            user.setAdmin(!user.isAdmin());
            unitOfWork.getUserRepository().updateUser(user);
            unitOfWork.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
