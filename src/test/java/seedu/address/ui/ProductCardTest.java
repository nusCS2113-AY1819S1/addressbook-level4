package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.Product;
import seedu.address.testutil.PersonBuilder;

public class ProductCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Product productWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        ProductCard productCard = new ProductCard(productWithNoTags, 1);
        uiPartRule.setUiPart(productCard);
        assertCardDisplay(productCard, productWithNoTags, 1);

        // with tags
        Product productWithTags = new PersonBuilder().build();
        productCard = new ProductCard(productWithTags, 2);
        uiPartRule.setUiPart(productCard);
        assertCardDisplay(productCard, productWithTags, 2);
    }

    @Test
    public void equals() {
        Product product = new PersonBuilder().build();
        ProductCard productCard = new ProductCard(product, 0);

        // same product, same index -> returns true
        ProductCard copy = new ProductCard(product, 0);
        assertTrue(productCard.equals(copy));

        // same object -> returns true
        assertTrue(productCard.equals(productCard));

        // null -> returns false
        assertFalse(productCard.equals(null));

        // different types -> returns false
        assertFalse(productCard.equals(0));

        // different product, same index -> returns false
        Product differentProduct = new PersonBuilder().withName("differentName").build();
        assertFalse(productCard.equals(new ProductCard(differentProduct, 0)));

        // same product, different index -> returns false
        assertFalse(productCard.equals(new ProductCard(product, 1)));
    }

    /* Asserts that {@code personCard} displays the details of {@code expectedProduct} correctly and matches
     * {@code expectedId}.
     */

    private void assertCardDisplay(ProductCard productCard, Product expectedProduct, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(productCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify product details are displayed correctly
        assertCardDisplaysPerson(expectedProduct, personCardHandle);
    }
}
