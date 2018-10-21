package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ExpenditureTracker;
import seedu.address.model.expenditureinfo.Expenditure;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalExpenditures {

    public static final Expenditure CHICKEN = new ExpenditureBuilder().withCategory("Chicken rice")
            .withDate("01-01-2018").withMoney("12").build();
    public static final Expenditure HM = new ExpenditureBuilder().withCategory("HM hoodie")
            .withDate("01-01-2018").withMoney("100").build();
    public static final Expenditure IPHONE = new ExpenditureBuilder().withCategory("iPhone")
            .withDate("03-02-2018").withMoney("2000").build();
    public static final Expenditure NIKE = new ExpenditureBuilder().withCategory("Nike shoes")
            .withDate("06-12-2018").withMoney("199").build();
    public static final Expenditure COKE = new ExpenditureBuilder().withCategory("Coke")
            .withDate("05-21-2018").withMoney("1").build();
    public static final Expenditure AIRCON = new ExpenditureBuilder().withCategory("Air conditioner")
            .withDate("04-30-2017").withMoney("700").build();
    public static final Expenditure SPEAKER = new ExpenditureBuilder().withCategory("Bose speaker")
            .withDate("09-17-2018").withMoney("400").build();

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
        return new ArrayList<>(Arrays.asList(CHICKEN, HM, IPHONE, NIKE, COKE, AIRCON, SPEAKER));
    }
}
