package seedu.address.model.product;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.distributor.DistributorName;
import seedu.address.model.tag.Tag;

/**
 * Represents a Product in the productInfo book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Product {

    // Identity fields
    private final Name name;
    private final SerialNumber serialNumber;
    private final DistributorName distname;
    private final RemainingItems remainingItems;

    // Data fields
    private final ProductInfo productInfo;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Product(Name name, SerialNumber serialNumber, DistributorName distname,
                   ProductInfo productInfo, RemainingItems remainingItems, Set<Tag> tags) {
        requireAllNonNull(name, serialNumber, distname, productInfo, remainingItems, tags);
        this.name = name;
        this.serialNumber = serialNumber;
        this.distname = distname;
        this.productInfo = productInfo;
        this.remainingItems = remainingItems;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public SerialNumber getSerialNumber() {
        return serialNumber;
    }

    public DistributorName getDistributor() {
        return distname;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public RemainingItems getRemainingItems() {
        return remainingItems;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both products of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two products.
     */
    public boolean isSameProduct(Product otherProduct) {
        if (otherProduct.getSerialNumber() == this.getSerialNumber()) {
            return true;
        }

        return otherProduct != null
                && otherProduct.getSerialNumber().equals(getSerialNumber());
    }

    /**
     * Returns true if both products have the same identity and data fields.
     * This defines a stronger notion of equality between two products.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Product)) {
            return false;
        }

        Product otherProduct = (Product) other;
        return otherProduct.getName().equals(getName())
                && otherProduct.getSerialNumber().equals(getSerialNumber())
                && otherProduct.getDistributor().equals(getDistributor())
                && otherProduct.getProductInfo().equals(getProductInfo())
                && otherProduct.getRemainingItems().equals(getRemainingItems())
                && otherProduct.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, serialNumber, distname, productInfo, tags, remainingItems);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("\nSerial number: ")
                .append(getSerialNumber())
                .append("\nDistributor: ")
                .append(getDistributor())
                .append("\nProduct info: ")
                .append(getProductInfo())
                .append ("\nRemaining products: ")
                .append(getRemainingItems())
                .append("\nTags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
