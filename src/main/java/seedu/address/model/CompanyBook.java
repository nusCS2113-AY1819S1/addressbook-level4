package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.company.Company;
import seedu.address.model.company.UniqueCompanyList;



/**
 * Wraps all data at the CompanyBook level
 * Duplicates are not allowed (by .isSameCompany comparison)
 */

public class CompanyBook implements ReadOnlyCompanyBook {
    private final UniqueCompanyList companyList;

    {
        companyList = new UniqueCompanyList();
    }

    public CompanyBook() {}

    /**
     * Creates a CompanyBook using the companies in the {@code toBeCopied}
     */
    public CompanyBook(ReadOnlyCompanyBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the company list with {@code companyList}.
     * {@code companyList} must not contain duplicate companies.
     */
    public void setCompanyList(List<Company> companyList) {
        this.companyList.setCompanyList(companyList);
    }

    /**
     * Resets the existing data of this {@code CompanyBook} with {@code newData}.
     */
    public void resetData(ReadOnlyCompanyBook newData) {
        requireNonNull(newData);

        setCompanyList(newData.getCompanyList());
    }

    //// company -level operations

    /**
     * Returns true if a company with the same identity as {@code company} exists in the address book.
     */
    public boolean hasCompany(Company company) {
        requireNonNull(company);
        return companyList.contains(company);
    }

    /**
     * Adds a company to the address book.
     * The company must not already exist in the address book.
     */
    public void addCompany(Company p) {
        companyList.add(p);
    }

    /**
     * Replaces the given company {@code target} in the list with {@code editedCompany}.
     * {@code target} must exist in the address book.
     * The company identity of {@code editedCompany} must not be the same as another existing company in the
     * CompanyBook.
     */
    public void updateCompany(Company target, Company editedCompany) {
        requireNonNull(editedCompany);

        companyList.setCompany(target, editedCompany);
    }

    /**
     * Removes {@code key} from this {@code CompanyBook}.
     * {@code key} must exist in the CompanyBook.
     */
    public void removeCompany(Company key) {
        companyList.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return companyList.asUnmodifiableObservableList().size() + " companyList";
        // TODO: refine later
    }

    @Override
    public ObservableList<Company> getCompanyList() {
        return companyList.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompanyBook // instanceof handles nulls
                && companyList.equals(((CompanyBook) other).companyList));
    }

    @Override
    public int hashCode() {
        return companyList.hashCode();
    }

}
