package seedu.address.testutil;

import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskModule;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.TaskPriority;

/**
 * A utility class to help with building EditTaskDescriptor objects.
 */
public class EditTaskDescriptorBuilder {

    private EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditTaskDescriptor();
    }

    public EditTaskDescriptorBuilder(EditTaskDescriptor descriptor) {
        this.descriptor = new EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code task}'s details
     */
    public EditTaskDescriptorBuilder(Task task) {
        descriptor = new EditTaskDescriptor();
        descriptor.setName(task.getName());
        descriptor.setModule(task.getModule());
        descriptor.setDate(task.getDate());
        descriptor.setPriority(task.getPriority());
    }

    /**
     * Sets the {@code TaskName} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withName(String name) {
        descriptor.setName(new TaskName(name));
        return this;
    }

    /**
     * Sets the {@code TaskModule} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withModule(String module) {
        descriptor.setModule(new TaskModule(module));
        return this;
    }

    /**
     * Sets the {@code TaskDate} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDate(String date) {
        descriptor.setDate(new TaskDate(date));
        return this;
    }

    /**
     * Sets the {@code TaskPriority} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withPriority(String priority) {
        descriptor.setPriority(new TaskPriority(priority));
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }
}
