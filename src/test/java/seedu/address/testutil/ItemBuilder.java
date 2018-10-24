package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.item.Item;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;
import seedu.address.model.item.Status;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Item objects.
 */
public class ItemBuilder {

    public static final String DEFAULT_NAME = "Atmega";
    public static final String DEFAULT_QUANTITY = "20";
    public static final String DEFAULT_MIN_QUANTITY = "5";

    private Name name;
    private Quantity quantity;
    private Quantity minQuantity;
    private Status status;
    private Set<Tag> tags;

    public ItemBuilder() {
        name = new Name(DEFAULT_NAME);
        quantity = new Quantity(DEFAULT_QUANTITY);
        minQuantity = new Quantity(DEFAULT_MIN_QUANTITY);
        status = new Status(Integer.parseInt(DEFAULT_QUANTITY), 0, 0);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ItemBuilder with the data of {@code itemToCopy}.
     */
    public ItemBuilder(Item itemToCopy) {
        name = itemToCopy.getName();
        quantity = itemToCopy.getQuantity();
        minQuantity = itemToCopy.getMinQuantity();
        status = itemToCopy.getStatus();
        tags = new HashSet<>(itemToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Item} that we are building.
     */
    public ItemBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Item} that we are building.
     */
    public ItemBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Item} that we are building.
     */
    public ItemBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        this.status = new Status(Integer.parseInt(quantity), 0, 0);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Item} that we are building.
     */
    public ItemBuilder withMinQuantity(String minQuantity) {
        this.minQuantity = new Quantity(minQuantity);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Item} that we are building.
     */
    public ItemBuilder withStatus(Status status) {
        this.status = status;
        return this;
    }

    public Item build() {
        return new Item(name, quantity, minQuantity, status, tags);
    }

}
