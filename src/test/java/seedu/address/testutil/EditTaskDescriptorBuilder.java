package seedu.address.testutil;

import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.model.task.ModuleCode;
import seedu.address.model.task.PriorityLevel;
import seedu.address.model.task.Task;

//@@author emobeany
/**
 * Utility class that helps to build EditTaskDescriptor objects.
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
     * returns {@code EditTaskDescriptor} with fields containing {@code task} details
     */
    public EditTaskDescriptorBuilder(Task task) {
        descriptor = new EditTaskDescriptor();
        descriptor.setTitle(task.getTitle());
        descriptor.setDescription(task.getDescription());
        descriptor.setModuleCode(task.getModuleCode());
        descriptor.setPriorityLevel(task.getPriorityLevel());
        descriptor.setExpectedNumOfHours(task.getExpectedNumOfHours());
    }

    /**
     * sets the {@code Title} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(title);
        return this;
    }

    /**
     * sets the {@code Description} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(description);
        return this;
    }

    /**
     * sets the {@code ModuleCode} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withModuleCode(ModuleCode moduleCode) {
        descriptor.setModuleCode(moduleCode);
        return this;
    }

    /**
     * sets the {@code PriorityLevel} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withPriorityLevel(PriorityLevel priorityLevel) {
        descriptor.setPriorityLevel(priorityLevel);
        return this;
    }

    /**
     * sets the {@code ExpectedNumOfHours} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withExpectedNumOfHours(Integer expectedNumOfHours) {
        descriptor.setExpectedNumOfHours(expectedNumOfHours);
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }
}
