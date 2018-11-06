package seedu.address.model.drink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRINK_COST_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRINK_NAME_COCA_COLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRINK_QUANTITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRINK_RETAIL_PRICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DRINK_TAG_SOFTDRINK;
import static seedu.address.model.testutil.TypicalDrinks.GREEN_TEA;
import static seedu.address.model.testutil.TypicalDrinks.PEPSI;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.drink.exceptions.DrinkNotFoundException;
import seedu.address.model.drink.exceptions.DuplicateDrinkException;
import seedu.address.model.testutil.DrinkBuilder;

public class UniqueDrinkListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueDrinkList uniqueDrinkList = new UniqueDrinkList();

    @Test
    public void contains_nullDrink_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDrinkList.contains(null);
    }

    @Test
    public void contains_drinkNotInList_returnsFalse() {
        assertFalse(uniqueDrinkList.contains(PEPSI));
    }

    @Test
    public void contains_drinkInList_returnsTrue() {
        uniqueDrinkList.add(PEPSI);
        assertTrue(uniqueDrinkList.contains(PEPSI));
    }

    @Test
    public void contains_drinkWithSameIdentityFieldsInList_returnsTrue() {
        uniqueDrinkList.add(PEPSI);
        Drink editedPepsi = new DrinkBuilder(PEPSI).withQuantity(VALID_DRINK_QUANTITY)
                .withRetailPrice(VALID_DRINK_RETAIL_PRICE)
                .withCostPrice(VALID_DRINK_COST_PRICE)
                .withTags(VALID_DRINK_TAG_SOFTDRINK)
                .build();
        assertTrue(uniqueDrinkList.contains(editedPepsi));
    }

    @Test
    public void add_nullDrink_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDrinkList.add(null);
    }

    @Test
    public void add_duplicateDrink_throwsDuplicateDrinkException() {
        uniqueDrinkList.add(PEPSI);
        thrown.expect(DuplicateDrinkException.class);
        uniqueDrinkList.add(PEPSI);
    }


    @Test
    public void remove_nullDrink_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDrinkList.remove(null);
    }

    @Test
    public void remove_drinkDoesNotExist_throwsDrinkNotFoundException() {
        thrown.expect(DrinkNotFoundException.class);
        uniqueDrinkList.remove(PEPSI);
    }

    @Test
    public void remove_existingDrink_removesDrink() {
        uniqueDrinkList.add(PEPSI);
        uniqueDrinkList.remove(PEPSI);
        UniqueDrinkList expectedUniqueDrinkList = new UniqueDrinkList();
        assertEquals(expectedUniqueDrinkList, uniqueDrinkList);
    }

    @Test
    public void setDrinks_nullUniqueDrinkList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDrinkList.setDrinks((UniqueDrinkList) null);
    }

    @Test
    public void setDrink_uniqueDrinkList_replacesOwnListWithProvidedUniqueDrinkList() {
        uniqueDrinkList.add(PEPSI);
        UniqueDrinkList uniqueDrinkList = new UniqueDrinkList();
        uniqueDrinkList.add(GREEN_TEA);
        this.uniqueDrinkList.setDrinks(uniqueDrinkList);
        assertEquals(uniqueDrinkList, this.uniqueDrinkList);
    }

    @Test
    public void setDrinks_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDrinkList.setDrinks((List<Drink>) null);
    }

    @Test
    public void setDrinks_list_replacesOwnListWithProvidedList() {
        uniqueDrinkList.add(PEPSI);
        List<Drink> drinkList = Collections.singletonList(GREEN_TEA);
        uniqueDrinkList.setDrinks(drinkList);
        UniqueDrinkList expectedUniqueDrinkList = new UniqueDrinkList();
        expectedUniqueDrinkList.add(GREEN_TEA);
        assertEquals(expectedUniqueDrinkList, uniqueDrinkList);
    }

    @Test
    public void setDrinks_listWithDuplicateDrinks_throwsDuplicateDrinkException() {
        List<Drink> listWithDuplicateDrinks = Arrays.asList(PEPSI, PEPSI);
        thrown.expect(DuplicateDrinkException.class);
        uniqueDrinkList.setDrinks(listWithDuplicateDrinks);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueDrinkList.asUnmodifiableObservableList().remove(0);
    }

    @Test
    public void findByName_drinkInList_returnDrinkRef() {
        Drink drinkToFind = PEPSI;
        uniqueDrinkList.add(drinkToFind);
        uniqueDrinkList.add(GREEN_TEA);
        Drink drinkToFindCopy = new DrinkBuilder(drinkToFind).build();
        assertEquals(drinkToFind, uniqueDrinkList.findByName(drinkToFindCopy));
    }

    @Test
    public void findByName_drinkNotInList_throwsDrinkNotFoundException() {
        Drink drinkToFind = PEPSI;
        uniqueDrinkList.add(drinkToFind);
        uniqueDrinkList.add(GREEN_TEA);
        Drink differentDrink = new DrinkBuilder(PEPSI).withName(VALID_DRINK_NAME_COCA_COLA).build();
        thrown.expect(DrinkNotFoundException.class);
        uniqueDrinkList.findByName(differentDrink);
    }
}
