package seedu.address.model.expenditureinfo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CategoryTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Category(null));
    }

    @Test
    public void constructorInvalidCategoryThrowsIllegalArgumentException() {
        String invalidCategory = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Category(invalidCategory));
    }

    @Test
    public void isValidCategory() {
        // null category
        Assert.assertThrows(NullPointerException.class, () -> Category.isValidCategory(null));

        // invalid categories
        assertFalse(Category.isValidCategory("")); // empty string
        assertFalse(Category.isValidCategory(" ")); // spaces only

        // valid categories
        assertTrue(Category.isValidCategory("Chicken rice"));
        assertTrue(Category.isValidCategory("-")); // one character
        assertTrue(Category.isValidCategory("San Francisco sourdough bread, San Francisco, CA, USA")); // long category
    }
}
