package seedu.address.testutil;

import seedu.address.model.statistic.Expense;
import seedu.address.model.statistic.Inventory;
import seedu.address.model.statistic.Revenue;
import seedu.address.model.statistic.Statistic;

/**
 * A utility class to help with building Statistic objects.
 */
public class StatisticBuilder {

    public static final String DEFAULT_INVENTORY = "0.00";
    public static final String DEFAULT_EXPENSE = "0.00";
    public static final String DEFAULT_REVENUE = "0.00";
    public static final String DEFAULT_YEAR = "2018";
    public static final String DEFAULT_MONTH = "12";


    private Revenue revenue;
    private Inventory inventory;
    private Expense expense;
    private int month;
    private int year;

    public StatisticBuilder() {
        inventory = new Inventory(DEFAULT_INVENTORY);
        expense = new Expense(DEFAULT_EXPENSE);
        revenue = new Revenue(DEFAULT_REVENUE);
        year = Integer.parseInt(DEFAULT_YEAR);
        month = Integer.parseInt(DEFAULT_MONTH);
    }


    /**
     * Initializes the StatisticBuilder with the data of {@code statisticToCopy}.
     */
    public StatisticBuilder(Statistic statisticToCopy) {
        inventory = statisticToCopy.getInventory();
        expense = statisticToCopy.getExpense();
        revenue = statisticToCopy.getRevenue();
        year = Integer.parseInt(statisticToCopy.getYear());
        month = Integer.parseInt(statisticToCopy.getMonth());

    }

    /**
     * Sets the {@code Inventory} of the {@code Statistic} that we are building.
     */
    public StatisticBuilder withInventory(String inventory) {
        this.inventory = new Inventory(inventory);
        return this;
    }


    /**
     * Sets the {@code Quantity} of the {@code Statistic} that we are building.
     */
    public StatisticBuilder withMonth(String month) {
        this.month = Integer.parseInt(month);
        return this;
    }

    /**
     * Sets the {@code Expense} of the {@code Statistic} that we are building.
     */
    public StatisticBuilder withExpense(String expense) {
        this.expense = new Expense(expense);
        return this;
    }

    /**
     * Sets the {@code Revenue} of the {@code Statistic} that we are building.
     */
    public StatisticBuilder withRevenue(String revenue) {
        this.revenue = new Revenue(revenue);
        return this;
    }

    /**
     * Sets the {@code Cost} of the {@code Statistic} that we are building
     */
    public StatisticBuilder withYear(String year) {
        this.year = Integer.parseInt(year);
        return this;
    }

    public Statistic build() {
        return new Statistic(inventory, expense, revenue, month, year);
    }

}
