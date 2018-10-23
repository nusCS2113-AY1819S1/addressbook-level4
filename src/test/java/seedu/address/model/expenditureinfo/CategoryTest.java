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
        assertFalse(Category.isValidCategory("Chicken rice")); // do not have this category
        assertFalse(Category.isValidCategory("-")); // one character

        // valid categories
        assertTrue(Category.isValidCategory("Food")); // specified category
    }
}
