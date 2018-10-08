package seedu.recruit.model.joboffer;

import java.util.Objects;

import seedu.recruit.model.candidate.Education;
import seedu.recruit.model.candidate.Gender;
import seedu.recruit.model.company.CompanyName;

/**
 * Represents a job offer in the CompanyBook.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class JobOffer {

    // Job Identity fields
    private final CompanyName companyName;
    private final Job job;
    private final Gender gender;
    private final Salary salary;

    // Data fields
    private final AgeRange ageRange;
    private final Education education;

    public JobOffer(CompanyName companyName, Job job, Gender gender, AgeRange ageRange, Education education,
                    Salary salary) {
        this.companyName = companyName;
        this.job = job;
        this.ageRange = ageRange;
        this.education = education;
        this.salary = salary;
        this.gender = gender;
    }

    public CompanyName getCompanyName() {
        return companyName;
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
                && otherJobOffer.getCompanyName().equals(getCompanyName())
                && otherJobOffer.getJob().equals(getJob())
                && otherJobOffer.getGender().equals(getGender())
                && otherJobOffer.getSalary().equals(getSalary());
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, job, gender, ageRange, education, salary);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Company name: ")
                .append(getCompanyName())
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
