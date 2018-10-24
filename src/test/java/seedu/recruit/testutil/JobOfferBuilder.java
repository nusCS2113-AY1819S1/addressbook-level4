package seedu.recruit.testutil;

import seedu.recruit.model.candidate.Education;
import seedu.recruit.model.candidate.Gender;
import seedu.recruit.model.company.CompanyName;
import seedu.recruit.model.joboffer.AgeRange;
import seedu.recruit.model.joboffer.Job;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.model.joboffer.Salary;

/**
 * A utility class to help with building JobOffer objects.
 */
public class JobOfferBuilder {


    public static final String DEFAULT_AGE_RANGE = "20-30";
    public static final String DEFAULT_COMPANY_NAME = "Mercedes Pte Ltd";
    public static final String DEFAULT_EDUCATION = "O level";
    public static final String DEFAULT_GENDER = "M";
    public static final String DEFAULT_JOB = "Sales Assistant";
    public static final String DEFAULT_SALARY = "1000";

    private AgeRange ageRange;
    private CompanyName companyName;
    private Education education;
    private Gender gender;
    private Job job;
    private Salary salary;

    public JobOfferBuilder() {
        ageRange = new AgeRange(DEFAULT_AGE_RANGE);
        companyName = new CompanyName(DEFAULT_COMPANY_NAME);
        education = new Education(DEFAULT_EDUCATION);
        gender = new Gender(DEFAULT_GENDER);
        job = new Job(DEFAULT_JOB);
        salary = new Salary(DEFAULT_SALARY);
    }

    /**
     * Initializes the JobOfferBuilder with the data of {@code jobOfferToCopy}.
     */

    public JobOfferBuilder(JobOffer jobOfferToCopy) {
        ageRange = jobOfferToCopy.getAgeRange();
        companyName = jobOfferToCopy.getCompanyName();
        education = jobOfferToCopy.getEducation();
        gender = jobOfferToCopy.getGender();
        job = jobOfferToCopy.getJob();
        salary = jobOfferToCopy.getSalary();
    }

    /**
     * Sets the {@code CompanyName} of the {@code JobOffer} that we are building.
     */
    public JobOfferBuilder withCompanyName(String companyName) {
        this.companyName = new CompanyName(companyName);
        return this;
    }

    /**
     * Sets the {@code AgeRange} of the {@code JobOffer} that we are building.
     */
    public JobOfferBuilder withAgeRange(String ageRange) {
        this.ageRange = new AgeRange(ageRange);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code JobOffer} that we are building.
     */
    public JobOfferBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code Job} of the {@code JobOffer} that we are building.
     */
    public JobOfferBuilder withJob(String job) {
        this.job = new Job(job);
        return this;
    }

    /**
     * Sets the {@code Education} of the {@code JobOffer} that we are building.
     */
    public JobOfferBuilder withEducation(String education) {
        this.education = new Education(education);
        return this;
    }

    /**
     * Sets the {@code Salary} of the {@code JobOffer} that we are building.
     */
    public JobOfferBuilder withSalary(String salary) {
        this.salary = new Salary(salary);
        return this;
    }

    public JobOffer build() {
        return new JobOffer(this.companyName, this.job, this.gender, this.ageRange, this.education, this.salary);
    }
}
