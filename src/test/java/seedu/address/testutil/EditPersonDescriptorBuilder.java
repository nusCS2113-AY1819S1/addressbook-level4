package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditBookDescriptor;
import seedu.address.model.book.*;
import seedu.address.model.book.Book;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditBookDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditCommand.EditBookDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditBookDescriptor();
    }

    public EditPersonDescriptorBuilder(EditBookDescriptor descriptor) {
        this.descriptor = new EditCommand.EditBookDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditBookDescriptor} with fields containing {@code book}'s details
     */
    public EditPersonDescriptorBuilder(Book book) {
        descriptor = new EditBookDescriptor();
        descriptor.setName(book.getName());
        descriptor.setIsbn(book.getISBN());
        descriptor.setPrice(book.getPrice());
        descriptor.setQuantity(book.getQuantity());
        descriptor.setTags(book.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditBookDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code ISBN} of the {@code EditBookDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setIsbn(new ISBN(phone));
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code EditBookDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setPrice(new Price(email));
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code EditBookDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setQuantity(new Quantity(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditBookDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditBookDescriptor build() {
        return descriptor;
    }
}
