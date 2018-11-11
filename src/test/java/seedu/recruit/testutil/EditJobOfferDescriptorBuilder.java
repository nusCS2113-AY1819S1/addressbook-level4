package seedu.recruit.testutil;

import seedu.recruit.logic.commands.EditJobDetailsCommand.EditJobOfferDescriptor;
import seedu.recruit.model.candidate.Education;
import seedu.recruit.model.candidate.Gender;
import seedu.recruit.model.company.CompanyName;
import seedu.recruit.model.joboffer.AgeRange;
import seedu.recruit.model.joboffer.Job;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.model.joboffer.Salary;


/**
 * A utility class to help with building EditJobOfferDescriptor objects.
 */
public class EditJobOfferDescriptorBuilder {

    private EditJobOfferDescriptor descriptor;

    public EditJobOfferDescriptorBuilder() {
        descriptor = new EditJobOfferDescriptor();
    }

    public EditJobOfferDescriptorBuilder(EditJobOfferDescriptor descriptor) {
        this.descriptor = new EditJobOfferDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditJobOfferDescriptor} with fields containing {@code jobOffer}'s details
     */
    public EditJobOfferDescriptorBuilder(JobOffer jobOffer) {
        descriptor = new EditJobOfferDescriptor();
        descriptor.setCompanyName(jobOffer.getCompanyName());
        descriptor.setJob(jobOffer.getJob());
        descriptor.setGender(jobOffer.getGender());
        descriptor.setAgeRange(jobOffer.getAgeRange());
        descriptor.setEducation(jobOffer.getEducation());
        descriptor.setSalary(jobOffer.getSalary());
    }

    /**
     * Sets the {@code Name} of the {@code EditJobOfferDescriptor} that we are building.
     */
    public EditJobOfferDescriptorBuilder withCompanyName(String companyName) {
        descriptor.setCompanyName(new CompanyName(companyName));
        return this;
    }

    /**
     * Sets the {@code Job} of the {@code EditJobOfferDescriptor} that we are building.
     */
    public EditJobOfferDescriptorBuilder withJob(String job) {
        descriptor.setJob(new Job(job));
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code EditJobOfferDescriptor} that we are building.
     */
    public EditJobOfferDescriptorBuilder withGender(String gender) {
        descriptor.setGender(new Gender(gender));
        return this;
    }

    /**
     * Sets the {@code Age} of the {@code EditJobOfferDescriptor} that we are building.
     */
    public EditJobOfferDescriptorBuilder withAgeRange(String ageRange) {
        descriptor.setAgeRange(new AgeRange(ageRange));
        return this;
    }

    /**
     * Sets the {@code Education} of the {@code EditJobOfferDescriptor} that we are building.
     */
    public EditJobOfferDescriptorBuilder withEducation(String education) {
        descriptor.setEducation(new Education(education));
        return this;
    }

    /**
     * Sets the {@code Salary} of the {@code EditJobOfferDescriptor} that we are building.
     */
    public EditJobOfferDescriptorBuilder withSalary(String salary) {
        descriptor.setSalary(new Salary(salary));
        return this;
    }

    public EditJobOfferDescriptor build() {
        return descriptor;
    }
}
