package seedu.address.testutil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.item.Item;
import seedu.address.model.item.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Item objects.
 */
public class ItemBuilder {

    public static final String DEFAULT_NAME = "Arduino";
    public static final Integer DEFAULT_QUANTITY = 20;
    public static final Integer DEFAULT_MIN_QUANTITY = 5;

    private Name name;
    private Integer quantity;
    private Integer minQuantity;
    private List<Integer> status;
    private Set<Tag> tags;

    public ItemBuilder() {
        name = new Name(DEFAULT_NAME);
        quantity = DEFAULT_QUANTITY;
        minQuantity = DEFAULT_MIN_QUANTITY;
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
     * Sets the {@code Address} of the {@code Item} that we are building.
     */
    public ItemBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Item} that we are building.
     */
    public ItemBuilder withMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Item} that we are building.
     */
    public ItemBuilder withStatus(List<Integer> status) {
        this.status = status;
        return this;
    }

    public Item build() {
        return new Item(name, quantity, minQuantity, status, tags);
    }

}
