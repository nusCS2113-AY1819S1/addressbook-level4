package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.distributor.DistributorName;
import seedu.address.model.product.Address;
import seedu.address.model.product.Name;
import seedu.address.model.product.Product;
import seedu.address.model.product.SerialNumber;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Product objects.
 */
public class ProductBuilder {

    public static final String DEFAULT_NAME = "Apple";
    public static final String DEFAULT_SERIAL_NUMBER = "85355255";
    public static final String DEFAULT_DIST = "Ah Huat";
    public static final String DEFAULT_INFO = "fruit";

    private Name name;
    private SerialNumber serialNumber;
    private DistributorName distname;
    private Address info;
    private Set<Tag> tags;

    public ProductBuilder() {
        name = new Name(DEFAULT_NAME);
        serialNumber = new SerialNumber(DEFAULT_SERIAL_NUMBER);
        distname = new DistributorName(DEFAULT_DIST);
        info = new Address(DEFAULT_INFO);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ProductBuilder with the data of {@code productToCopy}.
     */
    public ProductBuilder(Product productToCopy) {
        name = productToCopy.getName();
        serialNumber = productToCopy.getSerialNumber();
        distname = productToCopy.getDistributor();
        info = productToCopy.getProductInfo();
        tags = new HashSet<>(productToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Product} that we are building.
     */
    public ProductBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Product} that we are building.
     */
    public ProductBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Product} that we are building.
     */
    public ProductBuilder withInfo(String address) {
        this.info = new Address(address);
        return this;
    }

    /**
     * Sets the {@code SerialNumber} of the {@code Product} that we are building.
     */
    public ProductBuilder withSerialNumber(String serialNumber) {
        this.serialNumber = new SerialNumber(serialNumber);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Product} that we are building.
     */
    public ProductBuilder withDistributor(String email) {
        this.distname = new DistributorName(email);
        return this;
    }

    public Product build() {
        return new Product(name, serialNumber, distname, info, tags);
    }

}
