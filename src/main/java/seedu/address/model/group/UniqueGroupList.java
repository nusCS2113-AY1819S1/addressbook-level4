//@@author rajdeepsh

package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;


/**
 * A list of groups that enforces uniqueness between its elements and does not allow nulls.
 * A group is considered unique by comparing using {@code Group#isSameGroup(Group)}. As such, adding and updating of
 * groups uses Group#isSameGroup(Group) for equality so as to ensure that the group being added or updated is
 * unique in terms of identity fields.
 * However, the removal of a Group uses Group#equals(Object) so
 * as to ensure that the group with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Group#isSameGroup(Group).
 */
public class UniqueGroupList implements Iterable<Group> {

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the internal list contains an equivalent group as the given argument.
     *
     * @param toCheck Group to compare with.
     * @return Match result.
     */
    public boolean contains(Group toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameGroup);
    }

    /**
     * Returns true if a specific group in the internal list contains at least one same person as the persons in the
     * given argument.
     *
     * @param toCheck Contains persons to compare with.
     * @return Person match result.
     */
    public boolean contains(AddGroup toCheck) {
        requireNonNull(toCheck);
        for (Group g : internalList) {
            if (g.isSameGroup(toCheck.getGroup())) {
                return contains(g, toCheck);
            }
        }
        return false;
    }

    /**
     * Returns true if the group in the given argument contains least one
     * same person as the persons in the given argument.
     *
     * @param group Contains persons to compare with.
     * @param toCheck Contains persons to compare with.
     * @return Person match result.
     */
    public boolean contains(Group group, AddGroup toCheck) {
        requireAllNonNull(group, toCheck);
        for (Person p : group.getPersons()) {
            for (Person p2 : toCheck.getPersonSet()) {
                if (p.equals(p2)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Adds a group to the internal list.
     * The group must not already exist in the list.
     *
     * @param toCreate Group to be added.
     */
    public void createGroup(Group toCreate) {
        requireNonNull(toCreate);
        if (contains(toCreate)) {
            throw new DuplicateGroupException();
        }
        internalList.add(toCreate);
    }

    /**
     * Removes the equivalent group from the list.
     * The group must exist in the list.
     *
     * @param toRemove Group to be removed.
     */
    public void remove(Group toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new GroupNotFoundException();
        }
    }

    /**
     * Removes a person from a group in the list.
     * The group must exist in the list.
     * The person must exist in the group.
     *
     * @param group Group to remove person from.
     * @param toRemove Person to be removed.
     */
    public void removeGroupPerson(Group group, Person toRemove) {
        requireAllNonNull(group, toRemove);
        int index = internalList.indexOf(group);

        if (index == -1) {
            throw new GroupNotFoundException();
        } else if (!group.getPersons().contains(toRemove)) {
            throw new PersonNotFoundException();
        }

        Set<Person> personSet = new HashSet<>();
        for (Person p : group.getPersons()) {
            if (!p.equals(toRemove)) {
                personSet.add(p);
            }
        }

        Group editedGroup = createEditedGroup(group, personSet, false);
        internalList.set(index, editedGroup);
    }

    /**
     * Adds persons to a group in the list.
     * The person must not already exist in the list.
     *
     * @param toAdd Contains group and persons needed for this operation.
     */
    public void addGroup(AddGroup toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        addPersons(toAdd);
    }

    /**
     * Adds persons to group in list.
     * The group must exist in the list.
     * The person must not already exist in the list.
     *
     * @param toAdd Contains group and persons needed for this operation.
     */
    public void addPersons(AddGroup toAdd) {
        requireNonNull(toAdd);
        Group target = toAdd.getGroup();
        Group editedGroup = createEditedGroup(target, toAdd.getPersonSet(), true);
        int index = internalList.indexOf(target);

        if (index == -1) {
            throw new GroupNotFoundException();
        }
        internalList.set(index, editedGroup);
    }

    /**
     * Creates a new edited group object to replace existing group.
     *
     * @param target Group to be replaced.
     * @param personSet Persons to be added to edited group based on {@code addPerson}.
     * @param addPerson Flag to add {@code personSet}.
     * @return Edited group.
     */
    public Group createEditedGroup(Group target, Set<Person> personSet, Boolean addPerson) {
        requireAllNonNull(target, personSet, addPerson);
        Set<Tag> editedGroupTagSet = new HashSet<>();
        editedGroupTagSet.addAll(target.getTags());
        Group editedGroup = new Group (new GroupName(target.getGroupName().groupName),
                new GroupLocation(target.getGroupLocation().groupLocation), editedGroupTagSet);
        if (addPerson) {
            editedGroup.addPersons(target.getPersons());
        }
        editedGroup.addPersons(personSet);

        return editedGroup;
    }

    /**
     * Replaces the contents of the internal list with {@code groups}.
     * {@code groups} must not contain duplicate groups.
     *
     * @param groups Groups to replace internal list with.
     */
    public void setGroups(List<Group> groups) {
        requireAllNonNull(groups);
        if (!groupsAreUnique(groups)) {
            throw new DuplicateGroupException();
        }

        internalList.setAll(groups);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     *
     * @return Unmodifiable list.
     */
    public ObservableList<Group> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * Returns iterator to internal list.
     *
     * @return Internal list iterator.
     */
    @Override
    public Iterator<Group> iterator() {
        return internalList.iterator();
    }

    /**
     * Returns true if both objects have the same fields.
     *
     * @param other Object to compare with.
     * @return Comparison result.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueGroupList // instanceof handles nulls
                && internalList.equals(((UniqueGroupList) other).internalList)); // state check
    }

    /**
     * Returns true if {@code groups} contains only unique groups.
     *
     * @param groups Groups to check for uniqueness.
     * @return Comparison result.
     */
    private boolean groupsAreUnique(List<Group> groups) {
        for (int i = 0; i < groups.size() - 1; i++) {
            for (int j = i + 1; j < groups.size(); j++) {
                if (groups.get(i).isSameGroup(groups.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
