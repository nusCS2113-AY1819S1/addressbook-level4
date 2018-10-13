package seedu.address.model.person;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * A supporting data structure (Treemap) that helps keep the contacts in the contact list sorted, increasing
 * usability.
 * The names are sorted in an alphabetical order, with uppercase letters always being in front of lowercase letters.
 * Therefore, a possible order of the list is "Alice, Bob, amy, andrew".
 */


public class UniquePersonListHelper {

    private final Map<String, Person> internalListHelper = new TreeMap<>();

    public Set<String> acquireAllNames() {
        return internalListHelper.keySet();
    }

    /**
     * Returns true if the ordered map contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        String toCheckName = nameFinder(toCheck);
        if (internalListHelper.get(toCheckName) == null) {
            return false;
        } else {
            Person tempPerson = internalListHelper.get(toCheckName);
            return toCheck.isSamePerson(tempPerson);
        }
    }

    /**
     * Adds a person to the sorted map.
     * The person must not already exist in the list.
     */
    public void add(Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        String toAddName = nameFinder(toAdd);
        internalListHelper.put(toAddName, toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the sorted map.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void edit(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        String editedPersonName = nameFinder(editedPerson);
        String targetName = nameFinder(target);
        if (internalListHelper.get(targetName) == null){
            throw new PersonNotFoundException();
        }
        if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }
        internalListHelper.remove(targetName);
        internalListHelper.put(editedPersonName, editedPerson);
    }

    /**
     * Returns the person in the contact list, given the name.
     */
    public Person get(String name) {
        return internalListHelper.get(name);
    }

    /**
     * Removes the equivalent person from the sorted map.
     * The person must exist in the sorted map.
     */
    public void remove(Person toRemove) {
        requireNonNull(toRemove);
        String toRemoveName = nameFinder(toRemove);
        if (internalListHelper.get(toRemoveName) == null) {
            throw new PersonNotFoundException();
        }
        internalListHelper.remove(toRemoveName);
    }

    /**
     * Removes all contacts currently in the address book.
     */
    public void removeAll() {
        internalListHelper.clear();
    }

    /**
     * Returns the name of the given person contact.
     */
    private String nameFinder(Person person){
        return person.getName().toString();
    }

}