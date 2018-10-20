package seedu.planner.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.Limit;
import seedu.planner.model.record.MoneyFlow;


/**
 * A utility class containing a list of {@code Limit} objects to be used in tests.
 */
public class TypicalLimits {

    public static final Limit LIMIT_100 = new LimitBuilder().withDateStart("01-02-2018").withDateEnd("5-02-2018")
            .withMoneyFlow("-100").build();
    public static final Limit LIMIT_500 = new LimitBuilder().withDateStart("01-02-2018").withDateEnd("5-02-2018")
            .withMoneyFlow("-500").build();
    public static final Limit LIMIT_5000 = new LimitBuilder().withDateStart("01-02-2018").withDateEnd("5-02-2018")
            .withMoneyFlow("-5000").build();
    public static final Limit LIMIT_WEEKS_RANGE = new LimitBuilder().withDateStart("01-02-2018")
            .withDateEnd("07-02-2018").withMoneyFlow("-100").build();
    public static final Limit LIMIT_DATE_START_DIFF = new LimitBuilder().withDateStart("09-01-2018")
            .withDateEnd("05-02-2018").withMoneyFlow("-100").build();
    public static final Limit LIMIT_DATE_END_DIFF = new LimitBuilder().withDateStart("01-02-2018")
            .withDateEnd("01-07-2019").withMoneyFlow("-100").build();
    public static final Limit LIMIT_ALL_DIFFERENT = new LimitBuilder().withDateStart("01-01-2017")
            .withDateEnd("21-05-2019").withMoneyFlow("-367").build();


    // Manually added


    // Manually added - Record's details found in {@code CommandTestUtil}


    public static final MoneyFlow TYPICAL_LIMIT_MONEY_101 = new MoneyFlow("-101");
    public static final MoneyFlow TYPICAL_LIMIT_MONEY_100 = new MoneyFlow("-100");
    public static final MoneyFlow TYPICAL_LIMIT_EXCEEDED = new MoneyFlow("-999999");
    public static final MoneyFlow TYPICAL_LIMIT_NOT_EXCEEDED = new MoneyFlow("-1");
    public static final MoneyFlow TYPICAL_LIMIT_MONEY_EARNED = new MoneyFlow("+100");
    public static final Date TYPICAL_START_DATE = new Date("01-02-2018");
    public static final Date TYPICAL_WITHIN_DATE = new Date("03-02-2018");
    public static final Date TYPICAL_END_DATE = new Date("05-02-2018");

    public static final Date TYPICAL_NOT_INSIDE_DATE = new Date ("30-03-2028");

    private TypicalLimits() {} // prevents instantiation

    /**
     * Returns an {@code FinancialPlanner} with all the typical records.
     */
    public static FinancialPlanner getTypicalFinancialPlanner() {
        FinancialPlanner ab = new FinancialPlanner();
        for (Limit limit : getTypicalLimits()) {
            ab.addLimit(limit);
        }
        return ab;
    }

    public static List<Limit> getTypicalLimits() {
        return new ArrayList<>(Arrays.asList(LIMIT_100, LIMIT_500, LIMIT_5000, LIMIT_DATE_START_DIFF, LIMIT_WEEKS_RANGE,
                LIMIT_DATE_END_DIFF, LIMIT_ALL_DIFFERENT));
    }
}
