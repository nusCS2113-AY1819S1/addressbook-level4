package seedu.recruit.model;

import javafx.collections.ObservableList;
import seedu.recruit.model.candidate.Candidate;

/**
 * Unmodifiable view of a CandidateBook
 */
public interface ReadOnlyCandidateBook {

    /**
     * Returns an unmodifiable view of the candidate list.
     * This list will not contain any duplicate candidates.
     */
    ObservableList<Candidate> getCandidateList();

}
