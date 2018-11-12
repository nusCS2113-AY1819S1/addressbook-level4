package seedu.divelog.testutil;

import seedu.divelog.logic.commands.EditCommand;
import seedu.divelog.logic.commands.EditCommand.EditDiveDescriptor;
import seedu.divelog.model.dive.DepthProfile;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.Location;
import seedu.divelog.model.dive.PressureGroup;
import seedu.divelog.model.dive.Time;

/**
 * A utility class to help with building EditDiveDescriptor objects.
 */
public class EditDiveDescriptorBuilder {

    private EditCommand.EditDiveDescriptor descriptor;

    public EditDiveDescriptorBuilder() {
        descriptor = new EditCommand.EditDiveDescriptor();
    }

    public EditDiveDescriptorBuilder(EditCommand.EditDiveDescriptor descriptor) {
        this.descriptor = new EditCommand.EditDiveDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditDiveDescriptor} with fields containing {@code diveSession}'s details
     */
    public EditDiveDescriptorBuilder(DiveSession dive) {
        descriptor = new EditDiveDescriptor();
        descriptor.setStart(dive.getStart());
        descriptor.setEnd(dive.getEnd());
        descriptor.setSafetyStop(dive.getSafetyStop());
        descriptor.setLocation(dive.getLocation());
        descriptor.setDepthProfile(dive.getDepthProfile());
        descriptor.setPressureGroupAtBeginning(dive.getPressureGroupAtBeginning());
        //descriptor.setPressureGroupAtEnd(dive.getPressureGroupAtEnd());
    }

    /**
     * Sets the start time of the {@code EditDiveDescriptor} that we are building.
     */
    public EditDiveDescriptorBuilder withStart(String time) {
        descriptor.setStart(new Time(time));
        return this;
    }

    /**
     * Sets the end time of the {@code EditDiveDescriptor} that we are building.
     */
    public EditDiveDescriptorBuilder withEnd(String time) {
        descriptor.setEnd(new Time(time));
        return this;
    }

    /**
     * Sets the safety stop time of the {@code EditDiveDescriptor} that we are building.
     */
    public EditDiveDescriptorBuilder withSafetyStop(String safetyStop) {
        descriptor.setEnd(new Time(safetyStop));
        return this;
    }

    /**
     * Sets the depth of the {@code EditDiveDescriptor} that we are building.
     */
    public EditDiveDescriptorBuilder withDepth(float depth) {
        descriptor.setDepthProfile(new DepthProfile(depth));
        return this;
    }

    /**
     * Sets the starting pressure group of the {@code EditDiveDescriptor} that we are building.
     */
    public EditDiveDescriptorBuilder withStartingPressureGroup(String pressureGroup) {
        descriptor.setPressureGroupAtBeginning(new PressureGroup(pressureGroup));
        return this;
    }

    /**
     * Sets the end pressure group of the {@code EditDiveDescriptor} that we are building.
     */
    public EditDiveDescriptorBuilder withEndingPressureGroup(String pressureGroup) {
        descriptor.setPressureGroupAtEnd(new PressureGroup(pressureGroup));
        return this;
    }

    /**
     * Sets the end location of the {@code EditDiveDescriptor} that we are building.
     */
    public EditDiveDescriptorBuilder withLocation(String location) {
        descriptor.setLocation(new Location(location));
        return this;
    }

    public EditCommand.EditDiveDescriptor build() {
        return descriptor;
    }
}
