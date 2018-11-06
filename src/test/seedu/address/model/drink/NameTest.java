package model.drink;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.testutil.Assert;
import seedu.address.model.drink.Name;

public class NameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Name (null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("coke*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("pokka ice lemon tea")); // alphabets only
        assertTrue(Name.isValidName("100")); // numbers only
        assertTrue(Name.isValidName("100 plus")); // alphanumeric characters
        assertTrue(Name.isValidName("Caps Pokka")); // with capital letters
        assertTrue(Name.isValidName("Heaven and Earth Green Tea with Honey Lemon")); // long names
    }
}
