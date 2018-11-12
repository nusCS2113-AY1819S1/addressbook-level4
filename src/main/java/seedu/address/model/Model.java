package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.NoEmployeeException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Leave> PREDICATE_SHOW_ALL_LEAVES = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);
    void resetData2(ReadOnlyLeaveList newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();
    ReadOnlyLeaveList getLeaveList();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);
    boolean hasLeave(Leave leave);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);
    void deleteLeave(Leave target);


    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);
    void addLeave (Leave leave);

    /**
     * Sorts players in address book by field in asc or desc order
     * @param field
     * @param order
     */
    void sortEmployee(String field, String order) throws NoEmployeeException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updatePerson(Person target, Person editedPerson);
    void updateLeave(Leave target, Leave editedLeave);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered Leave list */
    ObservableList<Leave> getFilteredLeaveList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered leave list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredLeaveList(Predicate<Leave> predicate);

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();
    void commitLeaveList();
}
