package seedu.address.analysis;

import java.util.function.Predicate;

import seedu.address.model.transaction.Transaction;


/**
 * Represents the possible analysis periods to be analysed.
 */
public enum AnalysisPeriodType {
    DAY() {
        @Override
        public Predicate<Transaction> getPeriodFilterPredicate() {
            return new TransactionInDayPredicate();
        }
    },

    WEEK() {
        @Override
        public Predicate<Transaction> getPeriodFilterPredicate() {
            return new TransactionInSevenDaysPredicate();
        }
    },

    MONTH {
        @Override
        public Predicate<Transaction> getPeriodFilterPredicate() {
            return new TransactionInThirtyDaysPredicate();
        }
    };


    public static final String WRONG_PERIOD_MESSAGE = "You have entered an invalid period";

    public abstract Predicate<Transaction> getPeriodFilterPredicate();

}
