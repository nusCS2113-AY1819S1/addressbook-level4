package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysItem;

import org.junit.Test;

import guitests.guihandles.ItemCardHandle;
import seedu.address.model.item.Item;
import seedu.address.testutil.ItemBuilder;

public class ItemCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Item itemWithNoTags = new ItemBuilder().withTags(new String[0]).build();
        ItemCard itemCard = new ItemCard(itemWithNoTags, 1);
        uiPartRule.setUiPart(itemCard);
        assertCardDisplay(itemCard, itemWithNoTags, 1);

        // with tags
        Item itemWithTags = new ItemBuilder().build();
        itemCard = new ItemCard(itemWithTags, 2);
        uiPartRule.setUiPart(itemCard);
        assertCardDisplay(itemCard, itemWithTags, 2);
    }

    @Test
    public void equals() {
        Item item = new ItemBuilder().build();
        ItemCard itemCard = new ItemCard(item, 0);

        // same item, same index -> returns true
        ItemCard copy = new ItemCard(item, 0);
        assertTrue(itemCard.equals(copy));

        // same object -> returns true
        assertTrue(itemCard.equals(itemCard));

        // null -> returns false
        assertFalse(itemCard.equals(null));

        // different types -> returns false
        assertFalse(itemCard.equals(0));

        // different item, same index -> returns false
        Item differentItem = new ItemBuilder().withName("differentName").build();
        assertFalse(itemCard.equals(new ItemCard(differentItem, 0)));

        // same item, different index -> returns false
        assertFalse(itemCard.equals(new ItemCard(item, 1)));
    }

    /**
     * Asserts that {@code itemCard} displays the details of {@code expectedItem} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ItemCard itemCard, Item expectedItem, int expectedId) {
        guiRobot.pauseForHuman();

        ItemCardHandle itemCardHandle = new ItemCardHandle(itemCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", itemCardHandle.getId());

        // verify item details are displayed correctly
        assertCardDisplaysItem(expectedItem, itemCardHandle);
    }
}
