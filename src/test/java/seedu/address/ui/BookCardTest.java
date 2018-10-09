package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.book.Book;
import seedu.address.testutil.BookBuilder;

public class BookCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Book bookWithNoTags = new BookBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(bookWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, bookWithNoTags, 1);

        // with tags
        Book bookWithTags = new BookBuilder().build();
        personCard = new PersonCard(bookWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, bookWithTags, 2);
    }

    @Test
    public void equals() {
        Book book = new BookBuilder().build();
        PersonCard personCard = new PersonCard(book, 0);

        // same book, same index -> returns true
        PersonCard copy = new PersonCard(book, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different book, same index -> returns false
        Book differentBook = new BookBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentBook, 0)));

        // same book, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(book, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedBook} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, Book expectedBook, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify book details are displayed correctly
        assertCardDisplaysPerson(expectedBook, personCardHandle);
    }
}
