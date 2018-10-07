package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.joboffer.JobOffer;

/**
 * JobBook
 */

public interface ReadOnlyJobBook {

    /**
     * Returns an unmodifiable view of the job list.
     * This list will not contain any duplicate job offers.
     */
    ObservableList<JobOffer> getJobOfferList();

}
