package seedu.address.testutil;

import seedu.address.logic.commands.EditApptCommand.EditApptDescriptor;
import seedu.address.model.appt.ApptDateTime;
import seedu.address.model.appt.ApptDrName;
import seedu.address.model.appt.ApptInfo;
import seedu.address.model.appt.ApptVenue;

//@@author brandonccm1996
/**
 * A utility class to help with building EditApptDescriptor objects
 */
public class EditApptDescriptorBuilder {

    private EditApptDescriptor descriptor;

    public EditApptDescriptorBuilder() {
        descriptor = new EditApptDescriptor();
    }

    /**
     * Set the {@code apptStart} of the {@code EditApptDescriptor}
     */
    public EditApptDescriptorBuilder withStartTime(String startTime) {
        descriptor.setStart(new ApptDateTime(startTime));
        return this;
    }

    /**
     * Set the {@code apptEnd} of the {@code EditApptDescriptor}
     */
    public EditApptDescriptorBuilder withEndTime(String endTime) {
        descriptor.setEnd(new ApptDateTime(endTime));
        return this;
    }

    /**
     * Set the {@code apptVenue} of the {@code EditApptDescriptor}
     */
    public EditApptDescriptorBuilder withVenue(String venue) {
        descriptor.setVenue(new ApptVenue(venue));
        return this;
    }

    /**
     * Set the {@code apptInfo} of the {@code EditApptDescriptor}
     */
    public EditApptDescriptorBuilder withInfo(String info) {
        descriptor.setInfo(new ApptInfo(info));
        return this;
    }

    /**
     * Set the {@code apptDrName} of the {@code EditApptDescriptor}
     */
    public EditApptDescriptorBuilder withDrName(String drName) {
        descriptor.setDrName(new ApptDrName(drName));
        return this;
    }

    public EditApptDescriptor build() {
        return descriptor;
    }
}
