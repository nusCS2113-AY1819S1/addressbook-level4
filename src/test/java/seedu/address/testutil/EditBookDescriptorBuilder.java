package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.model.book.*;
import seedu.address.model.book.Book;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditBookDescriptor objects.
 */
public class EditBookDescriptorBuilder {

    private EditCommand.EditBookDescriptor descriptor;

    public EditBookDescriptorBuilder() {
        descriptor = new EditCommand.EditBookDescriptor();
    }

    public EditBookDescriptorBuilder(EditCommand.EditBookDescriptor descriptor) {
        this.descriptor = new EditCommand.EditBookDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditBookDescriptor} with fields containing {@code book}'s details
     */
    public EditBookDescriptorBuilder(Book book) {
        descriptor = new EditCommand.EditBookDescriptor();
        descriptor.setName(book.getName());
        descriptor.setIsbn(book.getIsbn());
        descriptor.setPrice(book.getPrice());
        descriptor.setQuantity(book.getQuantity());
        descriptor.setTags(book.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditBookDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Isbn} of the {@code EditBookDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withPhone(String phone) {
        descriptor.setIsbn(new Isbn(phone));
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code EditBookDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withEmail(String email) {
        descriptor.setPrice(new Price(email));
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code EditBookDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withAddress(String address) {
        descriptor.setQuantity(new Quantity(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditBookDescriptor}
     * that we are building.
     */
    public EditBookDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditBookDescriptor build() {
        return descriptor;
    }
}
