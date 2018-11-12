package seedu.address.testutil;

import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.StartTime;


/**
 * A utility class to help with building EditEventDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventDescriptor descriptor) {
        this.descriptor = new EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@code event}'s details
     */
    public EditEventDescriptorBuilder (Event event) {
        descriptor = new EditEventDescriptor();
        descriptor.setDescription(event.getDescription());
        descriptor.setDate(event.getDate());
        descriptor.setEndTime(event.getEndTime());
        descriptor.setEventName(event.getEventName());
        descriptor.setLocation(event.getLocation());
        descriptor.setStartTime(event.getStartTime());
    }


    /**
     * Sets the {@code EventName} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEventName(String name) {
        descriptor.setEventName(new EventName(name));
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withLocation(String location) {
        descriptor.setLocation(new Location(location));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code EventDate} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDate(String date) {
        descriptor.setDate(new EventDate(date));
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withStartTime(String startTime) {
        descriptor.setStartTime(new StartTime(startTime));
        return this;
    }

    /**
     * Sets the {@code EndTime} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEndTime(String endTime) {
        descriptor.setEndTime(new EndTime(endTime));
        return this;
    }

    public EditEventCommand.EditEventDescriptor build() {
        return descriptor;
    }
}
