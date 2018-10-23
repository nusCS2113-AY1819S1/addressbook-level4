package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ExpenditureTracker;
import seedu.address.model.expenditureinfo.Expenditure;

/**
 * A utility class containing a list of {@code Expenditure} objects to be used in tests.
 */
public class TypicalExpenditures {

    public static final Expenditure CHICKEN = new ExpenditureBuilder().withDescription("Chicken rice").withCategory("Food")
            .withDate("01-01-2018").withMoney("12").build();
    public static final Expenditure HM = new ExpenditureBuilder().withDescription("HM hoodie").withCategory("Clothing")
            .withDate("01-01-2018").withMoney("100").build();
    public static final Expenditure IPHONE = new ExpenditureBuilder().withDescription("iPhone").withCategory("Electronics")
            .withDate("03-02-2018").withMoney("2000").build();
    public static final Expenditure NIKE = new ExpenditureBuilder().withDescription("Nike shoes").withCategory("Shopping")
            .withDate("06-12-2018").withMoney("199").build();
    public static final Expenditure COKE = new ExpenditureBuilder().withDescription("Coke").withCategory("Drink")
            .withDate("05-21-2018").withMoney("1").build();
    public static final Expenditure AIRCON = new ExpenditureBuilder().withDescription("Air conditioner").withCategory("Furniture")
            .withDate("04-30-2017").withMoney("700").build();
    //public static final Expenditure SPEAKER = new ExpenditureBuilder().withDescription("Bose speaker").withCategory("Electronics")
    //        .withDate("09-17-2018").withMoney("400").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalExpenditures() {} // prevents instantiation

    /**
     * Returns an {@code ExpenditureTracker} with all the typical expenditures.
     */
    public static ExpenditureTracker getTypicalExpenditureTracker() {
        ExpenditureTracker et = new ExpenditureTracker();
        for (Expenditure expenditure : getTypicalExpenditures()) {
            et.addExpenditure(expenditure);
        }
        return et;
    }

    public static List<Expenditure> getTypicalExpenditures() {
        return new ArrayList<>(Arrays.asList(CHICKEN, HM, IPHONE, NIKE, COKE, AIRCON));
    }
}
