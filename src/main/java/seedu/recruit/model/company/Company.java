package seedu.recruit.model.company;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.recruit.model.commons.Address;
import seedu.recruit.model.commons.Email;
import seedu.recruit.model.commons.Phone;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.model.joboffer.UniqueJobList;
import seedu.recruit.model.tag.Tag;


/**
 * Represents each Company in the CompanyBook
 */

public class Company {

    // Identity fields
    private final CompanyName companyName;

    // Data fields
    private final Address address;
    private final Email email;
    private final Phone phone;
    private final Set<Tag> tags = new HashSet<>();

    // List of unique job offers
    private UniqueJobList jobOffers;

    /**
     * Every field must be present and not null.
     */

    public Company (CompanyName companyName, Address address, Email email, Phone phone, UniqueJobList jobOffers) {
        requireAllNonNull(companyName, address, email, phone);
        this.companyName = companyName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.jobOffers = jobOffers;
    }

    public Company (Company company) {
        requireNonNull(company);
        this.companyName = new CompanyName(company.getCompanyName().value);
        this.address = new Address(company.getAddress().value);
        this.email = new Email(company.getEmail().value);
        this.phone = new Phone(company.getPhone().value);
        this.jobOffers = new UniqueJobList();
        this.jobOffers.setJobOffers(company.getUniqueJobList());
    }

    public CompanyName getCompanyName() {
        return companyName;
    }

    public Address getAddress() { return address; }

    public Email getEmail() {
        return email;
    }

    public Phone getPhone() {
        return phone;
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public List<JobOffer> getJobOffers() {
        return Collections.unmodifiableList(jobOffers.getInternalList());
    }

    public UniqueJobList getUniqueJobList() {
        return jobOffers;
    }

    public void addJobOffer(JobOffer jobOffer) {
        jobOffers.add(jobOffer);
    }

    public void removeJobOffer(JobOffer jobOffer) {
        jobOffers.remove(jobOffer);
    }

    /**
     * Returns true if both companies is of the same name
     * This defines a weaker notion of equality between two companies.
     */
    public boolean isSameCompany(Company otherCompany) {
        if (otherCompany == this) {
            return true;
        }

        return otherCompany != null
                && otherCompany.getCompanyName().equals(getCompanyName());
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
        builder.append("Company Name:")
                .append(getCompanyName())
                .append(" Address: ")
                .append(getAddress())
                .append(" Email: ")
                .append(getEmail())
                .append(" Phone: ")
                .append(getPhone());

        return builder.toString();
    }

}
