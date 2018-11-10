package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.UpdateTaskCommand.UpdateTaskDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Body;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;

/**
 * A utility class to help with building UpdateTaskDescriptor objects.
 */
public class UpdateTaskDescriptorBuilder {

    private UpdateTaskDescriptor descriptor;

    public UpdateTaskDescriptorBuilder() {
        descriptor = new UpdateTaskDescriptor();
    }

    public UpdateTaskDescriptorBuilder(UpdateTaskDescriptor descriptor) {
        this.descriptor = new UpdateTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code UpdateTaskDescriptor} with fields containing {@code task}'s details
     */
    public UpdateTaskDescriptorBuilder(Task task) {
        descriptor = new UpdateTaskDescriptor();
        descriptor.setTaskName(task.getTaskName());
        descriptor.setBody(task.getBody());
        descriptor.setStartDateTime(task.getStartDateTime());
        descriptor.setEndDateTime(task.getEndDateTime());
        descriptor.setPriority(task.getPriority());
        descriptor.setTags(task.getTags());
    }

    /**
     * Sets the {@code TaskName} of the {@code UpdateTaskDescriptor} that we are building.
     */
    public UpdateTaskDescriptorBuilder withTaskName(String taskName) {
        descriptor.setTaskName(new TaskName(taskName));
        return this;
    }

    /**
     * Sets the {@code Body} of the {@code UpdateTaskDescriptor} that we are building.
     */
    public UpdateTaskDescriptorBuilder withBody(String body) {
        descriptor.setBody(new Body(body));
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code UpdateTaskDescriptor} that we are building.
     */
    public UpdateTaskDescriptorBuilder withStartDateTime(String startDateTime) {
        descriptor.setStartDateTime(new DateTime(startDateTime));
        return this;
    }
    /**
     * Sets the {@code DateTime} of the {@code UpdateTaskDescriptor} that we are building.
     */
    public UpdateTaskDescriptorBuilder withEndDateTime(String endDateTime) {
        descriptor.setStartDateTime(new DateTime(endDateTime));
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code UpdateTaskDescriptor} that we are building.
     */
    public UpdateTaskDescriptorBuilder withPriority(String priority) {
        descriptor.setPriority(new Priority(priority));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public UpdateTaskDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public UpdateTaskDescriptor build() {
        return descriptor;
    }
}
