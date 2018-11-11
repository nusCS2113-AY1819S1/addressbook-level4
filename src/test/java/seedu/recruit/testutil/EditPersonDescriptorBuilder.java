package seedu.recruit.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.recruit.logic.commands.EditCandidateCommand.EditPersonDescriptor;
import seedu.recruit.model.candidate.Age;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.candidate.Education;
import seedu.recruit.model.candidate.Gender;
import seedu.recruit.model.candidate.Name;
import seedu.recruit.model.commons.Address;
import seedu.recruit.model.commons.Email;
import seedu.recruit.model.commons.Phone;
import seedu.recruit.model.joboffer.Job;
import seedu.recruit.model.joboffer.Salary;
import seedu.recruit.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code candidate}'s details
     */
    public EditPersonDescriptorBuilder(Candidate candidate) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(candidate.getName());
        descriptor.setPhone(candidate.getPhone());
        descriptor.setEmail(candidate.getEmail());
        descriptor.setGender(candidate.getGender());
        descriptor.setAge(candidate.getAge());
        descriptor.setAddress(candidate.getAddress());
        descriptor.setJob(candidate.getJob());
        descriptor.setEducation(candidate.getEducation());
        descriptor.setSalary(candidate.getSalary());
        descriptor.setTags(candidate.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withGender(String gender) {
        descriptor.setGender(new Gender(gender));
        return this;
    }

    /**
     * Sets the {@code Age} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAge(String age) {
        descriptor.setAge(new Age(age));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Job} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withJob(String job) {
        descriptor.setJob(new Job(job));
        return this;
    }

    /**
     * Sets the {@code Education} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEducation(String education) {
        descriptor.setEducation(new Education(education));
        return this;
    }

    /**
     * Sets the {@code Salary} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withSalary(String salary) {
        descriptor.setSalary(new Salary(salary));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
