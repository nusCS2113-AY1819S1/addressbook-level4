//@@author kohjunkiat
package seedu.address.model.statistic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_JUNE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INVENTORY_JUNE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_JUNE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVENUE_JUNE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_JUNE;
import static seedu.address.testutil.TypicalStatistic.JAN;
import static seedu.address.testutil.TypicalStatistic.MAY;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.StatisticBuilder;

public class StatisticTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameStatistic() {
        // same object -> returns true
        assertTrue(JAN.isSameStatistic(JAN));

        // null -> returns false
        assertFalse(JAN.isSameStatistic(null));

        // different month and year -> returns false
        Statistic editedStatistic = new StatisticBuilder(JAN).withYear(VALID_YEAR_JUNE)
                .withMonth(VALID_MONTH_JUNE).build();
        assertFalse(JAN.isSameStatistic(editedStatistic));

        // different month -> returns false
        editedStatistic = new StatisticBuilder(JAN).withMonth(VALID_MONTH_JUNE).build();
        assertFalse(JAN.isSameStatistic(editedStatistic));

        // same month, same year , same inventory, different attributes -> returns true
        editedStatistic = new StatisticBuilder(JAN).withExpense(VALID_EXPENSE_JUNE)
                .withRevenue(VALID_REVENUE_JUNE).build();
        assertTrue(JAN.isSameStatistic(editedStatistic));

        // same month, same year, same revenue, different attributes -> returns true
        editedStatistic = new StatisticBuilder(JAN).withInventory(VALID_INVENTORY_JUNE)
                .withExpense(VALID_EXPENSE_JUNE).build();
        assertTrue(JAN.isSameStatistic(editedStatistic));

        // same month, same year, same expense, different attributes -> returns true
        editedStatistic = new StatisticBuilder(JAN).withRevenue(VALID_REVENUE_JUNE)
                .withInventory(VALID_INVENTORY_JUNE).build();
        assertTrue(JAN.isSameStatistic(editedStatistic));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Statistic janCopy = new StatisticBuilder(JAN).build();
        assertTrue(JAN.equals(janCopy));

        // same object -> returns true
        assertTrue(JAN.equals(JAN));



        // null -> returns false
        assertFalse(JAN.equals(null));

        // different type -> returns false
        assertFalse(JAN.equals(5));

        // different statistic -> returns false
        assertFalse(JAN.equals(MAY));

        // different inventory -> returns false
        Statistic editedStatistic = new StatisticBuilder(JAN).withInventory(VALID_INVENTORY_JUNE).build();
        assertFalse(JAN.equals(editedStatistic));

        // different revenue -> returns false
        editedStatistic = new StatisticBuilder(JAN).withRevenue(VALID_REVENUE_JUNE).build();
        assertFalse(JAN.equals(editedStatistic));

        // different expense -> returns false
        editedStatistic = new StatisticBuilder(JAN).withExpense(VALID_EXPENSE_JUNE).build();
        assertFalse(JAN.equals(editedStatistic));

        // different month -> returns false
        editedStatistic = new StatisticBuilder(JAN).withMonth(VALID_MONTH_JUNE).build();
        assertFalse(JAN.equals(editedStatistic));

        // different year -> returns false
        editedStatistic = new StatisticBuilder(JAN).withYear(VALID_YEAR_JUNE).build();
        assertFalse(JAN.equals(editedStatistic));
    }
}
