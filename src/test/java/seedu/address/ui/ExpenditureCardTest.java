//@@author feijunzi
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysExpenditure;

import org.junit.Test;

import guitests.guihandles.ExpenditureCardHandle;
import seedu.address.model.expenditureinfo.Expenditure;
import seedu.address.testutil.ExpenditureBuilder;


public class ExpenditureCardTest extends GuiUnitTest {
    @Test
    public void equals() {
        Expenditure expenditure = new ExpenditureBuilder().build();
        ExpenditureCard expenditureCard = new ExpenditureCard(expenditure, 0);

        // same expenditure, same index -> returns true
        ExpenditureCard copy = new ExpenditureCard(expenditure, 0);
        assertTrue(expenditureCard.equals(copy));

        // same object -> returns true
        assertTrue(expenditureCard.equals(expenditureCard));

        // null -> returns false
        assertFalse(expenditureCard.equals(null));

        // different types -> returns false
        assertFalse(expenditureCard.equals(0));

        // different expenditure, same index -> returns false
        Expenditure differentExpenditure = new ExpenditureBuilder().withDescription("differentDescription").build();
        assertFalse(expenditureCard.equals(new ExpenditureCard(differentExpenditure, 0)));

        // same expenditure, different index -> returns false
        assertFalse(expenditureCard.equals(new ExpenditureCard(expenditure, 1)));
    }

    /**
     * Asserts that {@code expenditureCard} displays the details of {@code expectedExpenditure} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ExpenditureCard expenditureCard, Expenditure expectedExpenditure, int expectedId) {
        guiRobot.pauseForHuman();

        ExpenditureCardHandle expenditureCardHandle = new ExpenditureCardHandle(expenditureCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", expenditureCardHandle.getId());

        // verify expenditure details are displayed correctly
        assertCardDisplaysExpenditure(expectedExpenditure, expenditureCardHandle);
    }
}
