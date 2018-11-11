//@@author kohjunkiat
package seedu.address.testutil;

import seedu.address.logic.commands.StockCommand;
import seedu.address.model.book.Book;
import seedu.address.model.book.Quantity;

/**
 * A utility class to help with building StockBookDescriptor objects.
 */
public class StockBookDescriptorBuilder {
    private StockCommand.StockBookDescriptor descriptor;

    public StockBookDescriptorBuilder() {
        descriptor = new StockCommand.StockBookDescriptor();
    }

    public StockBookDescriptorBuilder(StockCommand.StockBookDescriptor descriptor) {
        this.descriptor = new StockCommand.StockBookDescriptor(descriptor);
    }

    public StockBookDescriptorBuilder(Book book) {
        descriptor = new StockCommand.StockBookDescriptor();
        descriptor.setQuantity(book.getQuantity());
    }

    /**
     * Sets the {@code Quantity} of the {@code EditBookDescriptor} that we are building.
     */
    public StockBookDescriptorBuilder withQuantity(String quantity) {
        descriptor.setQuantity(new Quantity(quantity));
        return this;
    }

    public StockCommand.StockBookDescriptor build() {
        return descriptor;
    }
}
