package seedu.address.model.company;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.commons.Address;
import seedu.address.model.commons.Email;
import seedu.address.model.commons.Phone;
import seedu.address.model.joboffer.UniqueJobList;


/**
 * Represents each Company in the JobBook
 */

public class Company {

    // Identity fields
    private final CompanyName companyName;
    private final Address address;

    // Data fields
    private final Email email;
    private final Phone phone;

    // List of unique job offers
    private UniqueJobList jobOffers;

    /**
     * Every field must be present and not null.
     */

    Company (CompanyName companyName, Address address, Email email, Phone phone, UniqueJobList jobOffers) {
        requireAllNonNull(companyName, address, email, phone);
        this.companyName = companyName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.jobOffers = jobOffers;
    }

    public CompanyName getCompanyName() {
        return companyName;
    }

    public Address getAddress() {
        return address;
    }

    public Email getEmail() {
        return email;
    }

    public Phone getPhone() {
        return phone;
    }

    /**
     * Returns true if both companies is of the same name and same address
     * This defines a weaker notion of equality between two companies.
     */
    public boolean isSameCompany(Company otherCompany) {
        if (otherCompany == this) {
            return true;
        }

        return otherCompany != null
                && otherCompany.getCompanyName().equals(getCompanyName())
                && otherCompany.getAddress().equals(getAddress());
    }

    /**
     * Returns true if both companies have the same identity and data fields.
     * This defines a stronger notion of equality between two companies.
     */


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Company)) {
            return false;
        }

        Company otherCompany = (Company) other;
        return otherCompany.getCompanyName().equals(getCompanyName())
                && otherCompany.getPhone().equals(getPhone())
                && otherCompany.getEmail().equals(getEmail())
                && otherCompany.getAddress().equals(getAddress());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(companyName, address, email, phone);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getCompanyName())
                .append(" Address: ")
                .append(getAddress())
                .append(" Email: ")
                .append(getEmail())
                .append(" Phone: ")
                .append(getPhone());

        return builder.toString();
    }

}
