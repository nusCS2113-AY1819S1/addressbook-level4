package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.company.Company;

/**
 * CompanyBook
 */

public interface ReadOnlyCompanyBook {

    /**
     * Returns an unmodifiable view of the company list.
     * This list will not contain any duplicate companies.
     */
    ObservableList<Company> getCompanyList();

}
