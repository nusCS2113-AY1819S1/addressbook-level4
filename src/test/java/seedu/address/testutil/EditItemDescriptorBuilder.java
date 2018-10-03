package seedu.address.testutil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditItemDescriptor;
import seedu.address.model.item.Item;
import seedu.address.model.item.Name;
import seedu.address.model.item.NameContainsKeywordsPredicate;
import seedu.address.model.item.Quantity;
import seedu.address.model.item.UniqueItemList;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditItemDescriptor objects.
 */
public class EditItemDescriptorBuilder {

    private EditItemDescriptor descriptor;

    public EditItemDescriptorBuilder() {
        descriptor = new EditItemDescriptor();
    }

    public EditItemDescriptorBuilder(EditItemDescriptor descriptor) {
        this.descriptor = new EditItemDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditItemDescriptor} with fields containing {@code item}'s details
     */
    public EditItemDescriptorBuilder(Item item) {
        descriptor = new EditItemDescriptor();
        descriptor.setName(item.getName());
        descriptor.setQuantity(item.getQuantity());
        descriptor.setMinQuantity(item.getMinQuantity());
        descriptor.setStatus(item.getStatus());
        descriptor.setTags(item.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditItemDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code EditItemDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withQuantity(String quantity) {
        descriptor.setQuantity(new Quantity(quantity));
        return this;
    }

    /**
     * Sets the {@code minQuantity} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withMinQuantity(String minQuantity) {
        descriptor.setMinQuantity(new Quantity(minQuantity));
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code EditPersonDescriptor} that we are building.
     */

    /*
    public EditItemDescriptorBuilder withStatus(String status) {
        descriptor.setStatus(new List<Integer>(status));
        return this;
    }
    */


    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditItemDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditItemDescriptor build() {
        return descriptor;
    }
}
