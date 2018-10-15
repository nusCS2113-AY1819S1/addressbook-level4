package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A list of groups that enforces uniqueness between its elements and does not allow nulls.
 * A group is considered unique by comparing using {@code Group#isSameGroup(Group)}. As such, adding and updating of
 * groups uses Group#isSameGroup(Group) for equality so as to ensure that the group being added or updated is
 * unique in terms of all fields except its persons in the UniqueGroupList. However, the removal of a Group uses Group#equals(Object) so
 * as to ensure that the group with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Group#isSameGroup(Group)
 */
public class UniqueGroupList implements Iterable<Group> {

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the internal list contains an equivalent group as the given argument.
     */
    public boolean contains(Group toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameGroup);
    }

    /**
     * Returns true if a specific group in the internal list contains at least one same person as the persons in the
     * given argument.
     */
    public boolean contains(AddGroup toCheck) {
        requireNonNull(toCheck);
        for (Group g : internalList){
            if (g.isSameGroup(toCheck.getGroup())){
                return contains(g,toCheck);
            }
        }
        return false;
    }

    /**
     * Returns true if the group in the given argument contains least one
     * same person as the persons in the given argument.
     */
    public boolean contains(Group group, AddGroup toCheck) {
        requireAllNonNull(group, toCheck);
        for (Person p : group.getPersons()){
            for (Person p2 : toCheck.getPersonSet()){
                if(p.equals(p2)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Creates a group in the list.
     * The group must not already exist in the list.
     */
    public void createGroup(Group toCreate) {
        requireNonNull(toCreate);
        if (contains(toCreate)) {
            throw new DuplicateGroupException();
        }
        internalList.add(toCreate);
    }

    /**
     * Adds persons to a group in the list.
     * The person must not already exist in the list.
     */
    public void addGroup(AddGroup toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        addPersons(toAdd);
    }

    /**
     * Adds persons to group in list
     */
    public void addPersons(AddGroup toAdd) {
        requireNonNull(toAdd);
        for (Group g : internalList){
            if (g.isSameGroup(toAdd.getGroup())){
                for (Person p : toAdd.getPersonSet()){
                    g.addPersons(p);
                }
            }
        }
    }

    /**
     * Replaces the contents of this list with {@code groups}.
     * {@code groups} must not contain duplicate groups.
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
     */
    public ObservableList<Group> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Group> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueGroupList // instanceof handles nulls
                && internalList.equals(((UniqueGroupList) other).internalList)); // state check
    }

    /**
     * Returns true if {@code groups} contains only unique groups.
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
