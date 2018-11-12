//@@author kohjunkiat
package seedu.address.model.statistic;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;
public class InventoryTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Inventory(null));
    }

    @Test
    public void constructor_invalidInventory_throwsIllegalArgumentException() {
        String invalidInventory = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Inventory(invalidInventory));
    }

    @Test
    public void isValidInventory() {
        // null price
        Assert.assertThrows(NullPointerException.class, () -> Inventory.isValidInventory(null));

        // blank price
        assertFalse(Inventory.isValidInventory("")); // empty string
        assertFalse(Inventory.isValidInventory(" ")); // spaces only


        // invalid parts
        assertFalse(Inventory.isValidInventory("6.012")); // too many decimals
        assertFalse(Inventory.isValidInventory("-3")); // no negative integers
        assertFalse(Inventory.isValidInventory("a1.2")); // alphabet in string

        // valid revenue
        assertTrue(Inventory.isValidInventory("5")); // No decimals
        assertTrue(Inventory.isValidInventory("0.12")); //2 decimal place
        assertTrue(Inventory.isValidInventory("1123.10")); // Only decimals
    }
}
