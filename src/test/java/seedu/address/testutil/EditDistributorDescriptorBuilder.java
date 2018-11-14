package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditDistributorCommand.EditDistributorDescriptor;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.distributor.DistributorPhone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditDistributorDescriptor objects.
 */
public class EditDistributorDescriptorBuilder {

    private EditDistributorDescriptor descriptor;

    public EditDistributorDescriptorBuilder() {
        descriptor = new EditDistributorDescriptor();
    }

    public EditDistributorDescriptorBuilder(EditDistributorDescriptor descriptor) {
        this.descriptor = new EditDistributorDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditDistributorDescriptor} with fields containing {@code product}'s details
     */
    public EditDistributorDescriptorBuilder(Distributor distributor) {
        descriptor = new EditDistributorDescriptor();
        descriptor.setDistName(distributor.getDistName());
        descriptor.setDistPhone(distributor.getDistPhone());
        descriptor.setProds(distributor.getDistProds());
        descriptor.setTags(distributor.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditDistributorDescriptor} that we are building.
     */
    public EditDistributorDescriptorBuilder withName(String name) {
        descriptor.setDistName(new DistributorName(name));
        return this;
    }

    /**
     * Sets the {@code SerialNumber} of the {@code EditDistributorDescriptor} that we are building.
     */
    public EditDistributorDescriptorBuilder withPhone(String phone) {
        descriptor.setDistPhone(new DistributorPhone(phone));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditDistributorDescriptor}
     * that we are building.
     */
    public EditDistributorDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditDistributorDescriptor build() {
        return descriptor;
    }
}
