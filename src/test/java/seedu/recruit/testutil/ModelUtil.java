package seedu.recruit.testutil;

import static seedu.recruit.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE_RANGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.recruit.logic.commands.EditCandidateCommand.EditPersonDescriptor;
import seedu.recruit.logic.commands.EditCompanyCommand.EditCompanyDescriptor;
import seedu.recruit.logic.commands.EditJobDetailsCommand.EditJobOfferDescriptor;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.model.tag.Tag;

/**
 * A utility class for Candidate.
 */
public class ModelUtil {

    /**
     * Returns an add command string for adding the {@code candidate}.
     */
    public static String getAddCandidateCommand(Candidate candidate) {
        return getCandidateDetails(candidate);
    }

    public static String getAddJobCommand(JobOffer jobOffer) {
        return getJobDetails(jobOffer);
    }

    public static String getAddCompanyCommand(Company company) {
        return getCompanyDetails(company);
    }

    /**
     * Returns the part of command string for the given {@code candidate}'s details.
     */
    public static String getCandidateDetails(Candidate candidate) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + candidate.getName().fullName + " ");
        sb.append(PREFIX_GENDER + candidate.getGender().value + " ");
        sb.append(PREFIX_AGE + candidate.getAge().value + " ");
        sb.append(PREFIX_PHONE + candidate.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + candidate.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + candidate.getAddress().value + " ");
        sb.append(PREFIX_JOB + candidate.getJob().value + " ");
        sb.append(PREFIX_EDUCATION + candidate.getEducation().value + " ");
        sb.append(PREFIX_SALARY + candidate.getSalary().value + " ");
        candidate.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code company}'s details.
     */
    public static String getCompanyDetails(Company company) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_COMPANY_NAME + company.getCompanyName().value + " ");
        sb.append(PREFIX_PHONE + company.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + company.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + company.getAddress().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code job offer}'s details.
     */
    public static String getJobDetails(JobOffer jobOffer) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_COMPANY_NAME + jobOffer.getCompanyName().value + " ");
        sb.append(PREFIX_JOB + jobOffer.getJob().value + " ");
        sb.append(PREFIX_GENDER + jobOffer.getGender().value + " ");
        sb.append(PREFIX_AGE_RANGE + jobOffer.getAgeRange().value + " ");
        sb.append(PREFIX_EDUCATION + jobOffer.getEducation().value + " ");
        sb.append(PREFIX_SALARY + jobOffer.getSalary().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditCandidateDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getGender().ifPresent(gender -> sb.append(PREFIX_GENDER).append(gender.value).append(" "));
        descriptor.getAge().ifPresent(age -> sb.append(PREFIX_AGE).append(age.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getJob().ifPresent(job -> sb.append(PREFIX_JOB).append(job.value).append(" "));
        descriptor.getEducation().ifPresent(
            education -> sb.append(PREFIX_EDUCATION).append(education.value).append(" "));
        descriptor.getSalary().ifPresent(salary -> sb.append(PREFIX_SALARY).append(salary.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditCompanyDescriptor}'s details.
     */
    public static String getEditCompanyDescriptorDetails(EditCompanyDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_COMPANY_NAME).append(name.value).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditJobOfferDescriptor}'s details.
     */
    public static String getEditJobOfferDescriptorDetails(EditJobOfferDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getCompanyName().ifPresent(name -> sb.append(PREFIX_COMPANY_NAME).append(name.value).append(" "));
        descriptor.getJob().ifPresent(job -> sb.append(PREFIX_JOB).append(job.value).append(" "));
        descriptor.getGender().ifPresent(gender -> sb.append(PREFIX_GENDER).append(gender.value).append(" "));
        descriptor.getAgeRange().ifPresent(ageRange -> sb.append(PREFIX_AGE_RANGE).append(ageRange.value).append(" "));
        descriptor.getEducation().ifPresent(
            education -> sb.append(PREFIX_EDUCATION).append(education.value).append(" "));
        descriptor.getSalary().ifPresent(salary -> sb.append(PREFIX_SALARY).append(salary.value).append(" "));

        return sb.toString();
    }

}
