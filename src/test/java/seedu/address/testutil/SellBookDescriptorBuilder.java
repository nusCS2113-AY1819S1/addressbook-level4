//@@author kennethcsj
package seedu.address.testutil;

import seedu.address.logic.commands.SellCommand;
import seedu.address.model.book.Book;
import seedu.address.model.book.Quantity;

/**
 * A utility class to help with building SellBookDescriptor objects.
 */
public class SellBookDescriptorBuilder {

    private SellCommand.SellBookDescriptor descriptor;

    public SellBookDescriptorBuilder() {
        descriptor = new SellCommand.SellBookDescriptor();
    }

    public SellBookDescriptorBuilder(SellCommand.SellBookDescriptor descriptor) {
        this.descriptor = new SellCommand.SellBookDescriptor(descriptor);
    }

    public SellBookDescriptorBuilder(Book book) {
        descriptor = new SellCommand.SellBookDescriptor();
        descriptor.setQuantity(book.getQuantity());
    }

    /**
     * Sets the {@code Quantity} of the {@code EditBookDescriptor} that we are building.
     */
    public SellBookDescriptorBuilder withQuantity(String quantity) {
        descriptor.setQuantity(new Quantity(quantity));
        return this;
    }

    public SellCommand.SellBookDescriptor build() {
        return descriptor;
    }
}
