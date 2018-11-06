package model.drink;

import static logic.CommandTestUtil.VALID_DRINK_COST_PRICE;
import static logic.CommandTestUtil.VALID_DRINK_NAME_COCA_COLA;
import static logic.CommandTestUtil.VALID_DRINK_QUANTITY;
import static logic.CommandTestUtil.VALID_DRINK_RETAIL_PRICE;
import static logic.CommandTestUtil.VALID_DRINK_TAG_SOFTDRINK;
import static logic.CommandTestUtil.VALID_DRINK_TAG_TEA;
import static model.testutil.TypicalDrinks.FNN_GRAPE;
import static model.testutil.TypicalDrinks.FNN_GRAPE_COPY;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import model.testutil.DrinkBuilder;
import seedu.address.model.drink.Drink;

public class DrinkTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Drink drink = new DrinkBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        drink.getTags().remove(0);
    }


    @Test
    public void isSameDrink() {
        // same object -> return true
        assertTrue(FNN_GRAPE.isSameDrink(FNN_GRAPE));

        // same name, different attributes -> return true
        assertTrue(FNN_GRAPE.isSameDrink(FNN_GRAPE_COPY));

        // null -> return false
        assertFalse(FNN_GRAPE.isSameDrink(null));
    }

    @Test
    public void equals() {
        // same object -> return true
        assertTrue(FNN_GRAPE.equals(FNN_GRAPE));

        // same values -> return true
        Drink grapeTrueCopy = new DrinkBuilder(FNN_GRAPE).build();
        assertTrue(FNN_GRAPE.equals(grapeTrueCopy));

        // not a drink object (different type) -> return false
        assertFalse(FNN_GRAPE.equals(3));

        // null -> return false
        assertFalse(FNN_GRAPE.equals(null));

        // different name, else same attributes -> return false
        Drink editedGrape = new DrinkBuilder(FNN_GRAPE).withName(VALID_DRINK_NAME_COCA_COLA).build();
        assertFalse(FNN_GRAPE.equals(editedGrape));

        // different retail price, else same attributes -> return false
        editedGrape = new DrinkBuilder(FNN_GRAPE).withRetailPrice(VALID_DRINK_RETAIL_PRICE).build();
        assertFalse(FNN_GRAPE.equals(editedGrape));

        // different cost price, else same attributes -> return false
        editedGrape = new DrinkBuilder(FNN_GRAPE).withCostPrice(VALID_DRINK_COST_PRICE).build();
        assertFalse(FNN_GRAPE.equals(editedGrape));

        // different quantity, else same attributes -> return false
        editedGrape = new DrinkBuilder(FNN_GRAPE).withQuantity(VALID_DRINK_QUANTITY).build();
        assertFalse(FNN_GRAPE.equals(editedGrape));

        // different tags, else same attributes -> return false
        editedGrape = new DrinkBuilder(FNN_GRAPE).withTags(VALID_DRINK_TAG_TEA, VALID_DRINK_TAG_SOFTDRINK).build();
        assertFalse(FNN_GRAPE.equals(editedGrape));
    }

    //@Test
    //public void increaseQuantity_


}
