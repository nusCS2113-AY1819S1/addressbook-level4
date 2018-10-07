package seedu.address.model.joboffer;

import java.util.Objects;

import seedu.address.model.candidate.Education;
import seedu.address.model.candidate.Gender;

/**
 * Represents a job offer in the job book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class JobOffer {

    // Job Identity fields
    private final Company company;
    private final Job job;
    private final Gender gender;

    // Data fields
    private final AgeRange ageRange;
    private final Education education;
    private final Salary salary;

    public JobOffer(Company company, Job job, Gender gender, AgeRange ageRange, Education education, Salary salary) {
        this.company = company;
        this.job = job;
        this.ageRange = ageRange;
        this.education = education;
        this.salary = salary;
        this.gender = gender;
    }

    public Company getCompany() {
        return company;
    }

    public Job getJob() {
        return job;
    }

    public AgeRange getAgeRange() {
        return ageRange;
    }

    public Education getEducation() {
        return education;
    }

    public Salary getSalary() {
        return salary;
    }

    public Gender getGender() {
        return gender;
    }

    /**
     * Returns true if both job offers have the same job identity fields
     */
    public boolean isSameJobOffer(JobOffer otherJobOffer) {
        if (otherJobOffer == this) {
            return true;
        }

        return otherJobOffer != null
                && otherJobOffer.getCompany().equals(getCompany())
                && otherJobOffer.getJob().equals(getJob())
                && otherJobOffer.getGender().equals(getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, job, gender, ageRange, education, salary);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Company: ")
                .append(getCompany())
                .append(" Job: ")
                .append(getJob())
                .append(" Gender: ")
                .append(getGender())
                .append(" Age range: ")
                .append(getAgeRange())
                .append(" Education: ")
                .append(getEducation())
                .append(" Salary: ")
                .append(getSalary());


        return builder.toString();
    }
}
