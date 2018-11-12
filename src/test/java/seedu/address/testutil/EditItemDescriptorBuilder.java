package seedu.address.testutil;

import seedu.address.logic.commands.item.EditItemCommand;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemLocation;
import seedu.address.model.item.ItemName;
import seedu.address.model.item.ItemQuantity;

/**
 * A utility class to help with building EditItemDescriptor objects.
 */
public class EditItemDescriptorBuilder {

    private EditItemCommand.EditItemDescriptor descriptor;

    public EditItemDescriptorBuilder() {
        descriptor = new EditItemCommand.EditItemDescriptor();
    }

    public EditItemDescriptorBuilder(EditItemCommand.EditItemDescriptor descriptor) {
        this.descriptor = new EditItemCommand.EditItemDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditItemDescriptor} with fields containing {@code item}'s details
     */
    public EditItemDescriptorBuilder(Item item) {
        descriptor = new EditItemCommand.EditItemDescriptor();
        descriptor.setItemName(item.getItemName());
        descriptor.setItemQuantity(item.getItemQuantity());
        descriptor.setItemLocation(item.getItemLocation());
    }

    /**
     * Sets the {@code ItemName} of the {@code EditItemDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withItemName(String itemName) {
        descriptor.setItemName(new ItemName(itemName));
        return this;
    }

    /**
     * Sets the {@code ItemQuantity} of the {@code EditItemDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withItemQuantity(String itemQuantity) {
        descriptor.setItemQuantity(new ItemQuantity(itemQuantity));
        return this;
    }

    /**
     * Sets the {@code ItemLocation} of the {@code EditItemDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withItemLocation(String itemLocation) {
        descriptor.setItemLocation(new ItemLocation(itemLocation));
        return this;
    }

    public EditItemCommand.EditItemDescriptor build() {
        return descriptor;
    }
}
