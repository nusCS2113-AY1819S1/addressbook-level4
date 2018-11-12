package seedu.address.testutil.recruitment;

import seedu.address.logic.commands.EditRecruitmentPostCommand.EditPostDescriptor;
import seedu.address.model.recruitment.JobDescription;
import seedu.address.model.recruitment.Post;
import seedu.address.model.recruitment.Recruitment;
import seedu.address.model.recruitment.WorkExp;

/**
 * A utility class to help with building EditPostDescriptor objects.
 */
public class EditPostDescriptorBuilder {

    private EditPostDescriptor descriptor;

    public EditPostDescriptorBuilder() {
        descriptor = new EditPostDescriptor();
    }

    public EditPostDescriptorBuilder(EditPostDescriptor descriptor) {
        this.descriptor = new EditPostDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPostDescriptor} with fields containing {@code recruitment}'s details
     */
    public EditPostDescriptorBuilder(Recruitment recruitment) {
        descriptor = new EditPostDescriptor();
        descriptor.setPost(recruitment.getPost());
        descriptor.setWorkExp(recruitment.getWorkExp());
        descriptor.setJobDescription(recruitment.getJobDescription());
    }

    /**
     * Sets the {@code post} of the {@code EditPostDescriptor} that we are building.
     */
    public EditPostDescriptorBuilder withPost(String post) {
        descriptor.setPost(new Post(post));
        return this;
    }

    /**
     * Sets the {@code workExp} of the {@code EditPostDescriptor} that we are building.
     */
    public EditPostDescriptorBuilder withWorkExp(String workExp) {
        descriptor.setWorkExp(new WorkExp(workExp));
        return this;
    }

    /**
     * Sets the {@code JobDescription} of the {@code EditPostDescriptor} that we are building.
     */
    public EditPostDescriptorBuilder withJobDescription(String jobDescription) {
        descriptor.setJobDescription(new JobDescription(jobDescription));
        return this;
    }

    public EditPostDescriptor build() {
        return descriptor;
    }

}

