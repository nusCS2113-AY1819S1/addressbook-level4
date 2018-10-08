package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysGroup;

import org.junit.Test;

import guitests.guihandles.GroupCardHandle;
import seedu.address.model.group.Group;
import seedu.address.testutil.GroupBuilder;

public class GroupCardTest extends GuiUnitTest {
    @Test
    public void display() {
        // no tags
        Group groupWithNoTags = new GroupBuilder().withTags(new String[0]).build();
        GroupCard groupCard = new GroupCard(groupWithNoTags, 1);
        uiPartRule.setUiPart(groupCard);
        assertCardDisplay(groupCard, groupWithNoTags, 1);

        // with tags
        Group groupWithTags = new GroupBuilder().build();
        groupCard = new GroupCard(groupWithNoTags, 2);
        uiPartRule.setUiPart(groupCard);
        assertCardDisplay(groupCard, groupWithTags, 2);
    }

    @Test
    public void equals() {
        Group group = new GroupBuilder().build();
        GroupCard groupCard = new GroupCard(group, 0);

        // same group, same index -> returns true
        GroupCard copy = new GroupCard(group, 0);
        assertTrue(groupCard.equals(copy));

        // same object -> returns true
        assertTrue(groupCard.equals(groupCard));

        // null -> returns false
        assertFalse(groupCard.equals(null));

        // different types -> returns false
        assertFalse(groupCard.equals(0));

        // different group, same index -> returns false
        Group differentGroup = new GroupBuilder().withGroupName("differentName").build();
        assertFalse(groupCard.equals(new GroupCard(differentGroup, 0)));

        // same group, different index -> returns false
        assertFalse(groupCard.equals(new GroupCard(group, 1)));
    }

    /**
     * Asserts that {@code groupCard} displays the details of {@code expectedGroup} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(GroupCard groupCard, Group expectedGroup, int expectedId) {
        guiRobot.pauseForHuman();

        GroupCardHandle groupCardHandle = new GroupCardHandle(groupCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", groupCardHandle.getId());

        // verify group details are displayed correctly
        assertCardDisplaysGroup(expectedGroup, groupCardHandle);
    }

}
