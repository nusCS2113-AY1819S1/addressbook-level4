package seedu.address.model.drink;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;

public class DrinkBuilder {
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_COST_PRICE = "1.00";
    public static final String DEFAULT_RETAIL_PRICE = "5.00";
    public static final String DEFAULT_QUANTITY = "10";

    private Name name;
    private Price costPrice;
    private Price retailPrice;
    private UniqueBatchList uniqueBatchList;
    private Quantity quantity;
    private Set<Tag> tags;

    public DrinkBuilder() {
        name = new Name(DEFAULT_NAME);
        costPrice = new Price(DEFAULT_COST_PRICE);
        retailPrice = new Price(DEFAULT_RETAIL_PRICE);
        quantity = new Quantity(DEFAULT_QUANTITY);
        tags = new HashSet<>();
    }

    /**
     * Initializes the DrinkBuilder with the data of {@code drinkToCopy}.
     */
    public DrinkBuilder(Drink drinkToCopy) {
        name = drinkToCopy.getName();
        costPrice = drinkToCopy.getCostPrice();
        retailPrice = drinkToCopy.getRetailPrice();
        quantity = drinkToCopy.getQuantity();
        tags = new HashSet<>(drinkToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Drink} that we are building.
     */
    public DrinkBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

//    /**
//     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
//     */
//    public DrinkBuilder withTags(String... tags) {
//        this.tags = SampleDataUtil.getTagSet(tags);
//        return this;
//    }

    /**
     * Sets the {@code costPrice} of the {@code Drink} that we are building.
     */
    public DrinkBuilder withCostPrice(String price) {
        this.costPrice = new Price(price);
        return this;
    }

    /**
     * Sets the {@code retailPrice} of the {@code Drink} that we are building.
     */
    public DrinkBuilder withRetailPrice(String price) {
        this.retailPrice = new Price(price);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public DrinkBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    public Drink build() {
        return new Drink(name, costPrice, retailPrice, quantity, tags);
    }


}
