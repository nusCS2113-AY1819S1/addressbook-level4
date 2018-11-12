package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.NoEmployeeException;
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

    //Reused from https://github.com/CS2103JAN2018-F14-B1/main/pull/57/files with minor modifications
    //@@author Woonhian
    /**
     * Sorts employees by name or department in ascending or descending order.
     */
    public void sortBy(String field, String order) throws NoEmployeeException {
        if (internalList.size() < 1) {
            throw new NoEmployeeException();
        }

        Comparator<Person> comparator;

        /**
         * Used to sort by name
         */
        Comparator<Person> nameComparator = new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.getName().fullName.compareToIgnoreCase(p2.getName().fullName);
            }
        };

        /**
         * Used to sort by department
         */
        Comparator<Person> departmentComparator = new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                String x1 = p1.getDepartment().fullDepartment;
                String x2 = p2.getDepartment().fullDepartment;
                int sComp = x1.compareToIgnoreCase(x2);

                if (sComp != 0) {
                    return sComp;
                }

                String x3 = p1.getName().fullName;
                String x4 = p2.getName().fullName;
                return x3.compareToIgnoreCase(x4);
            }
        };

        switch (field) {
        case "name":
            comparator = nameComparator;
            break;

        case "department":
            comparator = departmentComparator;
            break;

        default:
            throw new AssertionError("Invalid field parameter entered...\n");
        }

        switch (order) {
        case "asc":
            internalList.sort(comparator);
            break;

        case "desc":
            internalList.sort(Collections.reverseOrder(comparator));
            break;

        default:
            throw new AssertionError("Invalid order parameter entered...\n");
        }
    }

    //@@author
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
