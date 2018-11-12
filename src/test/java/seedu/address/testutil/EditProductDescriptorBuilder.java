package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditProductCommand;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.product.Name;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductInfo;
import seedu.address.model.product.SerialNumber;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditProductDescriptor objects.
 */
public class EditProductDescriptorBuilder {

    private EditProductCommand.EditProductDescriptor descriptor;

    public EditProductDescriptorBuilder() {
        descriptor = new EditProductCommand.EditProductDescriptor();
    }

    public EditProductDescriptorBuilder(EditProductCommand.EditProductDescriptor descriptor) {
        this.descriptor = new EditProductCommand.EditProductDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditProductDescriptor} with fields containing {@code product}'s details
     */
    public EditProductDescriptorBuilder(Product product) {
        descriptor = new EditProductCommand.EditProductDescriptor();
        descriptor.setName(product.getName());
        descriptor.setSerialNumber(product.getSerialNumber());
        descriptor.setEmail(product.getDistributor());
        descriptor.setProductInfo(product.getProductInfo());
        descriptor.setRemainingItems(product.getRemainingItems());
        descriptor.setTags(product.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditProductDescriptor} that we are building.
     */
    public EditProductDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code SerialNumber} of the {@code EditProductDescriptor} that we are building.
     */
    public EditProductDescriptorBuilder withPhone(String phone) {
        descriptor.setSerialNumber(new SerialNumber(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditProductDescriptor} that we are building.
     */
    public EditProductDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new DistributorName(email));
        return this;
    }

    /**
     * Sets the {@code ProductInfo} of the {@code EditProductDescriptor} that we are building.
     */
    public EditProductDescriptorBuilder withAddress(String address) {
        descriptor.setProductInfo(new ProductInfo(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditProductDescriptor}
     * that we are building.
     */
    public EditProductDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditProductCommand.EditProductDescriptor build() {
        return descriptor;
    }
}
