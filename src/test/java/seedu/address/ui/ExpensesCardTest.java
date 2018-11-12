package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysExpenses;

import org.junit.Test;

import guitests.guihandles.ExpensesCardHandle;
import seedu.address.model.expenses.Expenses;
import seedu.address.testutil.expenses.ExpensesBuilder;

public class ExpensesCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Expenses expenses = new ExpensesBuilder().build();
        ExpensesCard expensesCard = new ExpensesCard(expenses, 1);
        uiPartRule.setUiPart(expensesCard);
        assertCardDisplay(expensesCard, expenses, 1);

    }

    @Test
    public void equals() {
        Expenses expenses = new ExpensesBuilder().build();
        ExpensesCard expensesCard = new ExpensesCard(expenses, 0);

        // same expenses, same index -> returns true
        ExpensesCard copy = new ExpensesCard(expenses, 0);
        assertTrue(expensesCard.equals(copy));

        // same object -> returns true
        assertTrue(expensesCard.equals(expensesCard));

        // null -> returns false
        assertFalse(expensesCard.equals(null));

        // different expenses -> returns false
        assertFalse(expensesCard.equals(0));

        // different employee id, same index -> returns false
        Expenses differentExpenses = new ExpensesBuilder().withEmployeeId("000003").build();
        assertFalse(expensesCard.equals(new ExpensesCard(differentExpenses, 0)));

        // same person, different index -> returns false
        assertFalse(expenses.equals(new ExpensesCard(expenses, 1)));
    }

    /**
     * Asserts that {@code expensesCard} displays the details of {@code expectedExpenses} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ExpensesCard expensesCard, Expenses expectedExpenses, int expectedId) {
        guiRobot.pauseForHuman();

        ExpensesCardHandle expensesCardHandle = new ExpensesCardHandle(expensesCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", expensesCardHandle.getId());

        // verify expenses details are displayed correctly
        assertCardDisplaysExpenses(expectedExpenses, expensesCardHandle);
    }

}
