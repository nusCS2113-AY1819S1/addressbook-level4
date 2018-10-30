package seedu.recruit.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.recruit.logic.parser.Prefix;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.candidate.UniqueCandidateList;

/**
 * Wraps all data at the CandidateBook level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class CandidateBook implements ReadOnlyCandidateBook {

    private final UniqueCandidateList candidates;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        candidates = new UniqueCandidateList();
    }

    public CandidateBook() {}

    /**
     * Creates a CandidateBook using the Persons in the {@code toBeCopied}
     */
    public CandidateBook(ReadOnlyCandidateBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the candidate list with {@code candidates}.
     * {@code candidates} must not contain duplicate candidates.
     */
    public void setCandidates(List<Candidate> candidates) {
        this.candidates.setCandidates(candidates);
    }

    /**
     * Resets the existing data of this {@code CandidateBook} with {@code newData}.
     */
    public void resetData(ReadOnlyCandidateBook newData) {
        requireNonNull(newData);

        setCandidates(newData.getCandidateList());
    }

    //// candidate-level operations

    /**
     * Returns true if a candidate with the same identity as {@code candidate} exists in the recruit book.
     */
    public boolean hasPerson(Candidate candidate) {
        requireNonNull(candidate);
        return candidates.contains(candidate);
    }

    /**
     * Adds a candidate to the recruit book.
     * The candidate must not already exist in the recruit book.
     */
    public void addPerson(Candidate p) {
        candidates.add(p);
    }

    /**
     * Replaces the given candidate {@code target} in the list with {@code editedCandidate}.
     * {@code target} must exist in the recruit book.
     * The candidate identity of {@code editedCandidate} must not be the same as another existing candidate in the
     * recruit book.
     */
    public void updatePerson(Candidate target, Candidate editedCandidate) {
        requireNonNull(editedCandidate);

        candidates.setCandidate(target, editedCandidate);
    }

    /**
     *  Sorts the candidate list
     */
    public void sortCandidates(Prefix prefix) {
        String prefixString = prefix.toString();
        switch(prefixString) {
        case "n/":
            candidates.sortByName();
            break;
        case "x/":
            candidates.sortByAge();
            break;
        case "e/":
            candidates.sortByEmail();
            break;
        case "j/":
            candidates.sortByJob();
            break;
        case "h/":
            candidates.sortByEducation();
            break;
        case "s/":
            candidates.sortBySalary();
            break;
        default:
            candidates.sortInReverse();

        }

    }

    /**
     * Removes {@code key} from this {@code CandidateBook}.
     * {@code key} must exist in the recruit book.
     */
    public void removeCandidate(Candidate key) {
        candidates.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return candidates.asUnmodifiableObservableList().size() + " candidates";
        // TODO: refine later
    }

    @Override
    public ObservableList<Candidate> getCandidateList() {
        return candidates.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CandidateBook // instanceof handles nulls
                && candidates.equals(((CandidateBook) other).candidates));
    }

    @Override
    public int hashCode() {
        return candidates.hashCode();
    }
}
