package seedu.address.testutil;

import seedu.address.logic.commands.EditLeaveCommand.EditLeaveDescriptor;
import seedu.address.model.leave.Approval;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.EmployeeId;
import seedu.address.model.leave.Leave;
import seedu.address.model.prioritylevel.PriorityLevel;

//@@author Hafizuddin-NUS
/**
 * A utility class to help with building EditLeaveDescriptor objects.
 */
public class EditLeaveDescriptorBuilder {

    private EditLeaveDescriptor descriptor;

    public EditLeaveDescriptorBuilder() {
        descriptor = new EditLeaveDescriptor();
    }

    public EditLeaveDescriptorBuilder(EditLeaveDescriptor descriptor) {
        this.descriptor = new EditLeaveDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditLeaveDescriptor} with fields containing {@code leave}'s details
     */
    public EditLeaveDescriptorBuilder(Leave leave) {
        descriptor = new EditLeaveDescriptor();
        descriptor.setApproval(leave.getApproval());
        descriptor.setNric(leave.getEmployeeId());
        descriptor.setDate(leave.getDate());
        descriptor.setPriorityLevel(leave.getPriorityLevel());
    }

    /**
     * Sets the {@code Approval} of the {@code EditLeaveDescriptor} that we are building.
     */
    public EditLeaveDescriptorBuilder withApproval(String approval) {
        descriptor.setApproval(new Approval(approval));
        return this;
    }

    /**
     * Sets the {@code EmployeeId} of the {@code EditLeaveDescriptor} that we are building.
     */
    public EditLeaveDescriptorBuilder withNric(String nric) {
        descriptor.setNric(new EmployeeId(nric));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditLeaveDescriptor} that we are building.
     */
    public EditLeaveDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Sets the {@code PriorityLevel} of the {@code EditLeaveDescriptor} that we are building.
     */
    public EditLeaveDescriptorBuilder withPriorityLevel(int priorityLevel) {
        descriptor.setPriorityLevel(new PriorityLevel(priorityLevel));
        return this;
    }

    public EditLeaveDescriptor build() {
        return descriptor;
    }
}
