//@@author kohjunkiat
package seedu.address.model.statistic;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Represents a Statistic in a month.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Statistic {
    public static final String STARTING_FIGURE = "0.00";
    // Data fields
    private volatile Revenue revenue;
    private volatile Inventory inventory;
    private volatile Expense expense;
    private int month;
    private int year;

    /**
     * Constructor for Json Jackson
     */
    public Statistic() {
        super();
    }
    /**
     * Every field must be present and not null.
     */
    public Statistic(int month, int year) {
        this.revenue = new Revenue(STARTING_FIGURE);
        this.inventory = new Inventory(STARTING_FIGURE);
        this.expense = new Expense((STARTING_FIGURE));
        this.month = month;
        this.year = year;
    }

    public Statistic(Inventory inventory, Expense expense, Revenue revenue, int month, int year) {
        this.revenue = revenue;
        this.inventory = inventory;
        this.expense = expense;
        this.month = month;
        this.year = year;
    }

    public String getMonth() {
        return Integer.toString(month);
    }

    public String getYear() {
        return Integer.toString(year);
    }

    public Revenue getRevenue() {
        return revenue;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Expense getExpense() {
        return expense;
    }

    /**
     * Increases the Revenue
     * @param price selling price of book
     * @param amount number of books sold
     */

    public void sell(String price, String cost, String
            amount) {
        revenue.increase(price, amount);
        inventory.decrease(cost, amount);
        expense.increase(cost, amount);
    }

    /**
     * Returns true if both statistic of the same month and year
     * have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameStatistic(Statistic otherStatistic) {
        if (otherStatistic == this) {
            return true;
        }

        return otherStatistic != null
                && otherStatistic.getMonth().equals(getMonth())
                && otherStatistic.getYear().equals(getYear())
                && (otherStatistic.getRevenue().equals(getRevenue())
                || otherStatistic.getInventory().equals(getInventory())
                || otherStatistic.getExpense().equals(getExpense()));
    }

    /**
     * Returns true if both stats have the same identity and data fields.
     * This defines a stronger notion of equality between two stats.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Statistic)) {
            return false;
        }

        Statistic otherStatistic = (Statistic) other;
        return otherStatistic.getMonth().equals(getMonth())
                && otherStatistic.getYear().equals(getYear())
                && otherStatistic.getExpense().equals(getExpense())
                && otherStatistic.getInventory().equals(getInventory())
                && otherStatistic.getRevenue().equals(getRevenue());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(month, year, revenue);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Month: ")
                .append(getMonth())
                .append(" Year: ")
                .append(getYear())
                .append(" Revenue: ")
                .append(revenue.toString())
                .append(" Expense: ")
                .append(expense.toString())
                .append(" Inventory: ")
                .append(inventory.toString());
        return builder.toString();
    }

}
