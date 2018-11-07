package seedu.address.model.product;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ProductNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ProductName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ProductName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> ProductName.isValidName(null));

        // invalid name
        assertFalse(ProductName.isValidName("")); // empty string
        assertFalse(ProductName.isValidName(" ")); // spaces only
        assertFalse(ProductName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(ProductName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(ProductName.isValidName("peter jack")); // alphabets only
        assertTrue(ProductName.isValidName("12345")); // numbers only
        assertTrue(ProductName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(ProductName.isValidName("Capital Tan")); // with capital letters
        assertTrue(ProductName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
