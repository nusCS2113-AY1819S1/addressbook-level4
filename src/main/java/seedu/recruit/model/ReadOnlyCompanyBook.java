package seedu.recruit.model;

import javafx.collections.ObservableList;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * CompanyBook
 */

public interface ReadOnlyCompanyBook {

    /**
     * Returns an unmodifiable view of the company list.
     * This list will not contain any duplicate companies.
     */
    ObservableList<Company> getCompanyList();

    ObservableList<JobOffer> getCompanyJobList();
}
