package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniquePersonList implements Iterable<Person> {

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Person toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(persons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    //@@author chokxy
    /**
     * Functions with comparators to sort by categories
     */
    public static Comparator<Person> comparebyName() {
        return (Comparator.comparing(person -> person.getName().fullName));
    }

    public static Comparator<Person> comparebyPhone() {
        return (Comparator.comparing(person -> person.getPhone().value));
    }

    public static Comparator<Person> comparebyEmail() {
        return (Comparator.comparing(person -> person.getEmail().value));
    }

    public static Comparator<Person> comparebyNric() {
        return (Comparator.comparing(person -> person.getNric().value));
    }

    /**
     * Returns the sorted internalList with respective category.
     */
    public void sortPersons(String prefix, int order) {

        switch (prefix) {
        //Sort by Name
        case "n/":
            if (order == 1) {
                internalList.sort(comparebyName());
            } else {
                internalList.sort(comparebyName().reversed());
            }
            break;

        //Sort by Phone
        case "p/":
            if (order == 1) {
                internalList.sort(comparebyPhone());
            } else {
                internalList.sort(comparebyPhone().reversed());
            }
            break;

        // Sort by Email
        case "e/":
            if (order == 1) {
                internalList.sort(comparebyEmail());
            } else {
                internalList.sort(comparebyEmail().reversed());
            }
            break;

        // Sort by Nric
        case "ic/":
            if (order == 1) {
                internalList.sort(comparebyNric());
            } else {
                internalList.sort(comparebyNric().reversed());
            }
            break;

        default:
            break;
        }

    }

    /*public ObservableList<Person> sortedPersonList() {
        ObservableList<Person> sortedInternalList = internalList;
        sortedInternalList.sort(Comparator.comparing(person -> person.getName().fullName));

        return FXCollections.unmodifiableObservableList(sortedInternalList);
    }
*/
    //@@author

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<Person> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSamePerson(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
