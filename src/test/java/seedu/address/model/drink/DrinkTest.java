package seedu.address.model.drink;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.drink.drinktestutil.DrinkBuilder;

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



        // same name, different attributes -> return true

        // null -> return false
    }

    @Test
    public void equals() {

    }

    @Test
    public void increaseQuantity_() {}


}
