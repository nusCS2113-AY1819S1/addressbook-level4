package seedu.planner.model.summary;

import java.util.Set;

import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Record;
import seedu.planner.model.tag.Tag;
//@@author tenvinc
/**
 * This class represents a in memory model of the statistic of a category. It contains totalIncome and totalExpenses.
 */
public class CategoryStatistic {

    private Set<Tag> tags;
    private Double totalIncome = 0.0;
    private Double totalExpense = 0.0;

    public CategoryStatistic(Record record) {
        tags = record.getTags();
        if (record.getMoneyFlow().toDouble() == 0) {
            throw new IllegalStateException(MoneyFlow.MESSAGE_MONEY_FLOW_CONSTRAINTS);
        }
        if (isExpense(record)) {
            totalExpense = Math.abs(record.getMoneyFlow().toDouble());
        } else {
            totalIncome = Math.abs(record.getMoneyFlow().toDouble());
        }
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    /** Adds {@code MoneyFlow} of record to {@code CategoryStatistic} */
    public void add(Record record) {
        assert(record.getTags().equals(tags));
        if (isExpense(record)) {
            totalExpense += Math.abs(record.getMoneyFlow().toDouble());
        } else {
            totalIncome += Math.abs(record.getMoneyFlow().toDouble());
        }
    }

    private boolean isExpense(Record record) {
        return record.getMoneyFlow().toDouble() < 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CategoryStatistic // instanceof handles nulls
                && tags.equals(((CategoryStatistic) other).tags)
                && totalIncome.equals(((CategoryStatistic) other).totalIncome)
                && totalExpense.equals(((CategoryStatistic) other).totalExpense));
    }
}

