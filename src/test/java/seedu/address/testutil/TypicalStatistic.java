//@@author kohjunkiat
package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_JUNE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_MAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INVENTORY_JUNE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INVENTORY_MAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_JUNE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_MAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVENUE_JUNE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVENUE_MAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_JUNE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_MAY;

import seedu.address.model.statistic.Statistic;

/**
 * A utility class containing a list of {@code Statistic} objects to be used in tests.
 */
public class TypicalStatistic {
    public static final Statistic JAN = new StatisticBuilder()
            .withInventory("10.10")
            .withExpense("9.90")
            .withRevenue("73.76")
            .withMonth("7")
            .withYear("2016").build();
    public static final Statistic FEB = new StatisticBuilder()
            .withInventory("90.00")
            .withExpense("97.00")
            .withRevenue("548.98")
            .withMonth("10")
            .withYear("2018").build();

    // Manually added - Statistic's details found in {@code CommandTestUtil}
    public static final Statistic MAY = new StatisticBuilder()
            .withInventory(VALID_INVENTORY_MAY)
            .withExpense(VALID_EXPENSE_MAY)
            .withRevenue(VALID_REVENUE_MAY)
            .withMonth(VALID_MONTH_MAY)
            .withYear(VALID_YEAR_MAY).build();
    public static final Statistic JUNE = new StatisticBuilder()
            .withInventory(VALID_INVENTORY_JUNE)
            .withExpense(VALID_EXPENSE_JUNE)
            .withRevenue(VALID_REVENUE_JUNE)
            .withMonth(VALID_MONTH_JUNE)
            .withYear(VALID_YEAR_JUNE).build();
}
