package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.candidate.Candidate;

/**
 * Unmodifiable view of a CandidateBook
 */
public interface ReadOnlyCandidateBook {

    /**
     * Returns an unmodifiable view of the candidate list.
     * This list will not contain any duplicate candidates.
     */
    ObservableList<Candidate> getCandidatelist();

}
