package seedu.address.model.drink.drinktestutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DRINK_COST_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRINK_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRINK_QUANTITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRINK_RETAIL_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRINK_TAG_SOFTDRINK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.InventoryList;
import seedu.address.model.drink.Drink;

/**
 * A utility class containing a list of {@code Drink} objects to be used in tests.
 */
public class TypicalDrinks {
    public static final Drink FNN_GRAPE = new DrinkBuilder().withName("FnN Grape")
            .withCostPrice("1.00")
            .withRetailPrice("5.00")
            .withQuantity("45")
            .withTags("softDrink").build();

    public static final Drink FNN_GRAPE_COPY = new DrinkBuilder().withName("FnN Grape")
            .withCostPrice("1.50")
            .withRetailPrice("5.20")
            .withQuantity("46")
            .withTags("softDrink").build();


    public static final Drink PEPSI = new DrinkBuilder().withName("Pepsi")
            .withCostPrice("1.20")
            .withRetailPrice("6.00")
            .withQuantity("40")
            .withTags("softDrink").build();

    public static final Drink GREEN_TEA = new DrinkBuilder().withName("Heaven and Earth Green Tea")
            .withCostPrice("0.80")
            .withRetailPrice("4.00")
            .withQuantity("90")
            .withTags("Tea").build();

    public static final Drink ICE_LEMON_TEA = new DrinkBuilder().withName("Pokka Ice Lemon Tea")
            .withCostPrice("1.3")
            .withRetailPrice("4.50")
            .withQuantity("20")
            .withTags("Tea").build();

    // Manually added - Drink's details found in {@code CommandTestUtil}
    public static final Drink COKE = new DrinkBuilder().withName(VALID_DRINK_NAME)
            .withCostPrice(VALID_DRINK_COST_PRICE)
            .withRetailPrice(VALID_DRINK_RETAIL_PRICE)
            .withQuantity(VALID_DRINK_QUANTITY)
            .withTags(VALID_DRINK_TAG_SOFTDRINK)
            .build();


    public static final String KEYWORD_MATCHING_TEA = "Tea"; // A keyword that matches Tea

    private TypicalDrinks() {
    } // prevents instantiation

    /**
     * Returns an {@code InventoryList} with all the typical drinks.
     */
    public static InventoryList getTypicalInventoryList() {
        InventoryList ab = new InventoryList();
        for (Drink drink : getTypicalDrinks()) {
            ab.addDrink(drink);
        }
        return ab;
    }

    public static List<Drink> getTypicalDrinks() {
        return new ArrayList<>(Arrays.asList(FNN_GRAPE, PEPSI, GREEN_TEA, ICE_LEMON_TEA));
    }
}
