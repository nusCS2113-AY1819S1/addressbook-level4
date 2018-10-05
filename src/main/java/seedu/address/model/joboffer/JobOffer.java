package seedu.address.model.joboffer;

import java.util.Objects;

/**
 * Represents a job offer in the job book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class JobOffer {

    private final Company company;
    private final Job job;

    public JobOffer(String company, String job) {
        this.company = new Company (company);
        this.job = new Job (job);
    }

    public Company getCompany() {
        return company;
    }

    public Job getJob() {
        return job;
    }

    /**
     * Returns true if both job offers are of the same company and offer the same job
     */
    public boolean isSameJobOffer(JobOffer otherJobOffer) {
        if (otherJobOffer == this) {
            return true;
        }

        return otherJobOffer != null
                && otherJobOffer.getCompany().equals(getCompany())
                && otherJobOffer.getJob().equals(getJob());
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, job);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Company: ")
                .append(getCompany())
                .append(" Job: ")
                .append(getJob());
        return builder.toString();
    }
}
