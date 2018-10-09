package seedu.recruit.model.candidate;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.recruit.model.candidate.exceptions.DuplicatePersonException;
import seedu.recruit.model.candidate.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A candidate is considered unique by comparing using {@code Candidate#isSamePerson(Candidate)}. As such, adding and
 * updating of persons uses Candidate#isSamePerson(Candidate) for equality so as to ensure that the candidate being
 * added or updated is unique in terms of identity in the UniqueCandidateList. However, the removal of a candidate uses
 * Candidate#equals(Object) so as to ensure that the candidate with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Candidate#isSamePerson(Candidate)
 */
public class UniqueCandidateList implements Iterable<Candidate> {

    private final ObservableList<Candidate> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent candidate as the given argument.
     */
    public boolean contains(Candidate toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a candidate to the list.
     * The candidate must not already exist in the list.
     */
    public void add(Candidate toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the candidate {@code target} in the list with {@code editedCandidate}.
     * {@code target} must exist in the list.
     * The candidate identity of {@code editedCandidate} must not be the same as another existing candidate in the list.
     */
    public void setPerson(Candidate target, Candidate editedCandidate) {
        requireAllNonNull(target, editedCandidate);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedCandidate) && contains(editedCandidate)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedCandidate);
    }

    /**
     * Removes the equivalent candidate from the list.
     * The candidate must exist in the list.
     */
    public void remove(Candidate toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniqueCandidateList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code candidates}.
     * {@code candidates} must not contain duplicate candidates.
     */
    public void setPersons(List<Candidate> candidates) {
        requireAllNonNull(candidates);
        if (!personsAreUnique(candidates)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(candidates);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Candidate> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Candidate> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueCandidateList // instanceof handles nulls
                        && internalList.equals(((UniqueCandidateList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code candidates} contains only unique candidates.
     */
    private boolean personsAreUnique(List<Candidate> candidates) {
        for (int i = 0; i < candidates.size() - 1; i++) {
            for (int j = i + 1; j < candidates.size(); j++) {
                if (candidates.get(i).isSamePerson(candidates.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
