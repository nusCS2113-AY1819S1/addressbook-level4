package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.ItemCardHandle;
import guitests.guihandles.ItemListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.item.Item;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(ItemCardHandle expectedCard, ItemCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedItem}.
     */
    public static void assertCardDisplaysItem(Item expectedItem, ItemCardHandle actualCard) {
        assertEquals(expectedItem.getName().fullName, actualCard.getName());
        assertEquals(expectedItem.getPhone().value, actualCard.getPhone());
        assertEquals(expectedItem.getEmail().value, actualCard.getEmail());
        assertEquals(expectedItem.getAddress().value, actualCard.getAddress());
        assertEquals(expectedItem.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code itemListPanelHandle} displays the details of {@code items} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ItemListPanelHandle itemListPanelHandle, Item... items) {
        for (int i = 0; i < items.length; i++) {
            itemListPanelHandle.navigateToCard(i);
            assertCardDisplaysItem(items[i], itemListPanelHandle.getItemCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code itemListPanelHandle} displays the details of {@code items} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ItemListPanelHandle itemListPanelHandle, List<Item> items) {
        assertListMatching(itemListPanelHandle, items.toArray(new Item[0]));
    }

    /**
     * Asserts the size of the list in {@code itemListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(ItemListPanelHandle itemListPanelHandle, int size) {
        int numberOfPeople = itemListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
