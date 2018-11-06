package model;

import static logic.CommandTestUtil.VALID_DRINK_COST_PRICE;
import static model.testutil.TypicalDrinks.GREEN_TEA;
import static model.testutil.TypicalDrinks.PEPSI;
import static model.testutil.TypicalDrinks.getTypicalInventoryList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.testutil.DrinkBuilder;
import seedu.address.model.InventoryList;
import seedu.address.model.ReadOnlyInventoryList;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.exceptions.DuplicateDrinkException;


public class InventoryListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final InventoryList inventoryList = new InventoryList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), inventoryList.getDrinkList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        inventoryList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyInventoryList_replacesData() {
        InventoryList newData = getTypicalInventoryList();
        inventoryList.resetData(newData);
        assertEquals(newData, inventoryList);
    }

    @Test
    public void resetData_withDuplicateDrinks_throwsDuplicateDrinkException() {
        // Two drinks with the same identity field (name)
        Drink editedPepsi = new DrinkBuilder (PEPSI).withCostPrice(VALID_DRINK_COST_PRICE).build();
        List<Drink> newDrinks = Arrays.asList(PEPSI, editedPepsi);
        InventoryListStub newData = new InventoryListStub(newDrinks);

        thrown.expect(DuplicateDrinkException.class);
        inventoryList.resetData(newData);
    }

    @Test
    public void hasDrink_nullDrink_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        inventoryList.hasDrink(null);
    }

    @Test
    public void hasDrink_drinkNotInInventoryList_returnsFalse() {
        assertFalse(inventoryList.hasDrink(PEPSI));
    }

    @Test
    public void hasDrink_drinkInInventoryList_returnsTrue() {
        inventoryList.addDrink(PEPSI);
        assertTrue(inventoryList.hasDrink(PEPSI));
    }

    @Test
    public void hasDrink_drinkithSameIdentityFieldsInInventoryList_returnsTrue() {
        inventoryList.addDrink(PEPSI);
        Drink editedPepsi = new DrinkBuilder(GREEN_TEA).withName("Pepsi").build();
        assertTrue(inventoryList.hasDrink(editedPepsi));
    }

    @Test
    public void getDrinkList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        inventoryList.getDrinkList().remove(0);
    }


    /**
     * A stub ReadOnlyInventoryList whose drink list can violate interface constraints.
     */
    private static class InventoryListStub implements ReadOnlyInventoryList {
        private final ObservableList<Drink> drinks = FXCollections.observableArrayList();

        InventoryListStub(Collection<Drink> drinks) {
            this.drinks.setAll(drinks);
        }

        @Override
        public ObservableList<Drink> getDrinkList() {
            return drinks;
        }
    }
}
