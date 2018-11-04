package seedu.planner.testutil;

import static seedu.planner.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_MONEYFLOW_EXPENSE_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_MONEYFLOW_INCOME_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.Record;

/**
 * A utility class containing a list of {@code Record} objects to be used in tests.
 */
public class TypicalRecords {

    public static final Record INDO = new RecordBuilder().withName("Indo")
            .withMoneyFlow("-5.6").withDate("28-2-2018").withTags("friends").build();
    public static final Record RANDOM = new RecordBuilder().withName("Random income")
            .withMoneyFlow("+14.50").withDate("31-03-2018").build();
    public static final Record CAIFAN = new RecordBuilder().withName("caifan")
            .withMoneyFlow("-3.80").withDate("26-4-2018").withTags("owesMoney", "friends").build();
    public static final Record WORK = new RecordBuilder().withName("Income from work")
            .withMoneyFlow("+60.0").withDate("26-4-2018").build();
    public static final Record KOREAN = new RecordBuilder().withName("Korean food")
            .withMoneyFlow("-4.50").withDate("25-5-2018").withTags("Terrace").build();
    public static final Record JAP = new RecordBuilder().withName("Japanese food")
            .withMoneyFlow("-5.40").withDate("25-5-2018").withTags("Deck").build();
    public static final Record ZT = new RecordBuilder().withName("Payment from ZT")
            .withMoneyFlow("+5.90").withDate("25-5-2018").withTags("friends").build();
    public static final Record MALA = new RecordBuilder().withName("Payment for mala")
            .withMoneyFlow("-10.50").withDate("26-5-2018").build();
    public static final Record CHICKENRICE = new RecordBuilder().withName("chicken rice")
            .withMoneyFlow("-0.90").withDate("27-5-2018").build();

    // Manually added
    public static final Record FRIEND = new RecordBuilder().withName("Payment from friend")
            .withMoneyFlow("+20.00").withDate("1-4-2018").withTags("friends").build();
    public static final Record CLASSMATE = new RecordBuilder().withName("PaymentToBob")
            .withMoneyFlow("-199.47").withDate("10-4-2018").withTags("friends").build();
    public static final Record PART_TIME = new RecordBuilder().withName("Parttime")
            .withMoneyFlow("+250.00").withDate("15-4-2018").build();
    public static final Record AA = new RecordBuilder().withName("AA")
            .withMoneyFlow("-235.96").withDate("17-4-2018").withTags("friends").build();
    public static final Record BB = new RecordBuilder().withName("BB")
            .withMoneyFlow("-149.18").withDate("23-4-2018").withTags("BBBB").build();
    public static final Record BURSARY = new RecordBuilder().withName("Income from bursary")
            .withMoneyFlow("+11.50").withDate("26-9-2018").withTags("School").build();
    public static final Record IDA = new RecordBuilder().withName("PaymenttoIda")
            .withMoneyFlow("-12.30").withDate("27-9-2018").withTags("work").build();
    public static final Record INCOME_A = new RecordBuilder().withName("IncomeA")
            .withMoneyFlow("+100").withDate("26-11-2018").withTags("incomeA").build();

    // Manually added - Record's details found in {@code CommandTestUtil}
    public static final Record AMY = new RecordBuilder().withName(VALID_NAME_AMY).withDate(VALID_DATE_AMY)
            .withMoneyFlow(VALID_MONEYFLOW_INCOME_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Record BOB = new RecordBuilder().withName(VALID_NAME_BOB).withDate(VALID_DATE_BOB)
            .withMoneyFlow(VALID_MONEYFLOW_EXPENSE_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_BURSARY = "Income"; // A keyword that matches MEIER

    public static final Date TYPICAL_START_DATE = new Date("25-9-2018");
    public static final Date TYPICAL_END_DATE = new Date("26-9-2018");

    public static final Date OUT_OF_BOUND_DATE = new Date ("30-03-2018");

    private TypicalRecords() {} // prevents instantiation


    /**
     * Returns an {@code FinancialPlanner} with all the typical records.
     */
    public static FinancialPlanner getTypicalFinancialPlanner() {
        FinancialPlanner ab = new FinancialPlanner();
        for (Record record : getTypicalRecords()) {
            ab.addRecord(record);
        }
        return ab;
    }

    public static List<Record> getTypicalRecords() {
        return new ArrayList<>(Arrays.asList(INDO, CAIFAN, WORK, ZT, MALA, CHICKENRICE, RANDOM, JAP, KOREAN));
    }
}
