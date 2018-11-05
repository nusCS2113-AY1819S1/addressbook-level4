package seedu.planner.model.summary;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import seedu.planner.commons.util.MoneyUtil;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Record;

/**
 * This class represents a summary in FinancialPlanner which records can be added to. Type T is the class of the
 * identifier that the summary is associated with
 */
public class Summary<Identifier> {

    private Identifier identifier;
    private MoneyFlow totalExpense;
    private MoneyFlow totalIncome;
    private MoneyFlow total;

    public Summary(Record record, Identifier identifier) {
        requireAllNonNull(record, identifier);
        this.identifier = identifier;
        MoneyFlow money = record.getMoneyFlow();
        if (isExpense(money)) {
            totalExpense = money;
            totalIncome = new MoneyFlow("+0");
        } else {
            totalIncome = money;
            totalExpense = new MoneyFlow("-0");
        }
        total = money;
    }

    public Summary(Identifier identifier, MoneyFlow totalExpense, MoneyFlow totalIncome, MoneyFlow total) {
        requireAllNonNull(identifier, totalExpense, totalIncome, total);
        this.identifier = identifier;
        this.totalExpense = totalExpense;
        this.totalIncome = totalIncome;
        this.total = total;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public MoneyFlow getTotalExpense() {
        return totalExpense;
    }

    public MoneyFlow getTotalIncome() {
        return totalIncome;
    }

    public MoneyFlow getTotal() {
        return total;
    }

    /**
     * Adds record into the summary object
     * @param record record to be added
     */
    public void add(Record record) {
        requireNonNull(record);
        MoneyFlow money = record.getMoneyFlow();
        if (isExpense(money)) {
            totalExpense = MoneyUtil.add(totalExpense, money);
        } else {
            totalIncome = MoneyUtil.add(totalIncome, money);
        }
        total = MoneyUtil.add(total, money);
    }

    private boolean isExpense(MoneyFlow money) {
        return money.toDouble() < 0;
    }

    @Override
    public boolean equals(Object other) {
        return this == other // short circuit if same object
                || (other instanceof Summary // instanceof handles nulls
                && identifier.equals(((Summary) other).identifier)
                && totalExpense.equals(((Summary) other).totalExpense)
                && totalIncome.equals(((Summary) other).totalIncome)
                && total.equals(((Summary) other).total));
    }

    @Override
    public String toString() {
        return identifier.getClass().getSimpleName() + ": " + identifier + "\n"
                + "Total Expense: " + totalExpense + "\n"
                + "Total Income: " + totalIncome + "\n"
                + "Total: " + total;
    }
}
