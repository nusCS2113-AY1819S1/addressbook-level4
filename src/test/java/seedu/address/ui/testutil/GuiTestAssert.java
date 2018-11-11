package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.ProductCardHandle;
import guitests.guihandles.ProductListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.product.Product;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(ProductCardHandle expectedCard, ProductCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getProductInfo(), actualCard.getProductInfo());
        assertEquals(expectedCard.getDistributor(), actualCard.getDistributor());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getSerialNumber(), actualCard.getSerialNumber());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedProduct}.
     */
    public static void assertCardDisplaysProduct(Product expectedProduct, ProductCardHandle actualCard) {
        assertEquals(expectedProduct.getName().fullName, actualCard.getName());
        assertEquals(expectedProduct.getSerialNumber().value, actualCard.getSerialNumber());
        assertEquals(expectedProduct.getRemainingItems().value, actualCard.getRemainingItems());
        assertEquals(expectedProduct.getProductInfo().value, actualCard.getProductInfo());
        assertEquals(expectedProduct.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ProductListPanelHandle personListPanelHandle, Product... persons) {
        for (int i = 0; i < persons.length; i++) {
            personListPanelHandle.navigateToCard(i);
            assertCardDisplaysProduct(persons[i], personListPanelHandle.getProductCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ProductListPanelHandle personListPanelHandle, List<Product> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new Product[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(ProductListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}