package seedu.address.analysis;

/**
 * Represents the possible analysis periods to be analysed.
 */
public enum AnalysisPeriodType {
    DAY() {
        @Override
        public TransactionPeriodPredicate getPeriodFilterPredicate() {
            return new TransactionInDayPredicate();
        }
    },

    WEEK() {
        @Override
        public TransactionPeriodPredicate getPeriodFilterPredicate() {
            return new TransactionInSevenDaysPredicate();
        }
    },

    MONTH {
        @Override
        public TransactionPeriodPredicate getPeriodFilterPredicate() {
            return new TransactionInThirtyDaysPredicate();
        }
    };


    public static final String WRONG_PERIOD_MESSAGE = "You have entered an invalid period";

    public abstract TransactionPeriodPredicate getPeriodFilterPredicate();

}
