package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONEYFLOW_EXPENSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONEYFLOW_INCOME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.record.Record;

/**
 * A utility class containing a list of {@code Record} objects to be used in tests.
 */
public class TypicalRecords {

    public static final Record INDO = new RecordBuilder().withName("Indo")
            .withMoneyFlow("-5.6").withDate("18-9-2012").withTags("friends").build();
    public static final Record CAIFAN = new RecordBuilder().withName("caifan")
            .withMoneyFlow("-3.80").withDate("18-12-2012").withTags("owesMoney", "friends").build();
    public static final Record WORK = new RecordBuilder().withName("Income from work").withDate("10-12-2012")
            .withMoneyFlow("+60.0").build();
    public static final Record ZT = new RecordBuilder().withName("Payment from ZT").withDate("10-1-2012")
            .withMoneyFlow("+5.90").withTags("friends").build();
    public static final Record MALA = new RecordBuilder().withName("Payment for mala").withDate("10-1-2003")
            .withMoneyFlow("-10.50").build();
    public static final Record CHICKENRICE = new RecordBuilder().withName("Payment for chicken rice")
            .withDate("31-12-1996").withMoneyFlow("-0.90").build();
    public static final Record RANDOM = new RecordBuilder().withName("Random income").withDate("23-10-2010")
            .withMoneyFlow("+14.50").build();

    // Manually added
    public static final Record BURSARY = new RecordBuilder().withName("Income from bursary").withDate("31-4-2080")
            .withMoneyFlow("+11.50").build();
    public static final Record IDA = new RecordBuilder().withName("Payment to Ida").withDate("4-10-2030")
            .withMoneyFlow("-12.30").withTags("work").build();

    // Manually added - Record's details found in {@code CommandTestUtil}
    public static final Record AMY = new RecordBuilder().withName(VALID_NAME_AMY).withDate(VALID_DATE_AMY)
            .withMoneyFlow(VALID_MONEYFLOW_INCOME_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Record BOB = new RecordBuilder().withName(VALID_NAME_BOB).withDate(VALID_DATE_BOB)
            .withMoneyFlow(VALID_MONEYFLOW_EXPENSE_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_BURSARY = "Income"; // A keyword that matches MEIER

    private TypicalRecords() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical records.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Record record : getTypicalRecords()) {
            ab.addRecord(record);
        }
        return ab;
    }

    public static List<Record> getTypicalRecords() {
        return new ArrayList<>(Arrays.asList(INDO, CAIFAN, WORK, ZT, MALA, CHICKENRICE, RANDOM));
    }
}
