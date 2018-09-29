package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.candidate.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

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

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the candidate list with {@code candidates}.
     * {@code candidates} must not contain duplicate candidates.
     */
    public void setPersons(List<Candidate> candidates) {
        this.persons.setPersons(candidates);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getCandidatelist());
    }

    //// candidate-level operations

    /**
     * Returns true if a candidate with the same identity as {@code candidate} exists in the address book.
     */
    public boolean hasPerson(Candidate candidate) {
        requireNonNull(candidate);
        return persons.contains(candidate);
    }

    /**
     * Adds a candidate to the address book.
     * The candidate must not already exist in the address book.
     */
    public void addPerson(Candidate p) {
        persons.add(p);
    }

    /**
     * Replaces the given candidate {@code target} in the list with {@code editedCandidate}.
     * {@code target} must exist in the address book.
     * The candidate identity of {@code editedCandidate} must not be the same as another existing candidate in the
     * address book.
     */
    public void updatePerson(Candidate target, Candidate editedCandidate) {
        requireNonNull(editedCandidate);

        persons.setPerson(target, editedCandidate);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Candidate key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Candidate> getCandidatelist() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
