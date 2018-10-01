package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.candidate.Candidate;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Candidate> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyCandidateBook newData);

    /** Returns the CandidateBook */
    ReadOnlyCandidateBook getAddressBook();

    /**
     * Returns true if a candidate with the same identity as {@code candidate} exists in the address book.
     */
    boolean hasPerson(Candidate candidate);

    /**
     * Deletes the given candidate.
     * The candidate must exist in the address book.
     */
    void deletePerson(Candidate target);

    /**
     * Adds the given candidate.
     * {@code candidate} must not already exist in the address book.
     */
    void addPerson(Candidate candidate);

    /**
     * Replaces the given candidate {@code target} with {@code editedCandidate}.
     * {@code target} must exist in the address book.
     * The candidate identity of {@code editedCandidate} must not be the same as another existing candidate in the
     * address book.
     */
    void updatePerson(Candidate target, Candidate editedCandidate);

    /** Returns an unmodifiable view of the filtered candidate list */
    ObservableList<Candidate> getFilteredPersonList();

    /**
     * Updates the filter of the filtered candidate list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Candidate> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();
}
