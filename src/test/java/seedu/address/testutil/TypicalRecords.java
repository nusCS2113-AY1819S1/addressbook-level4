package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INCOME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INCOME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
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

    public static final Record ALICE = new RecordBuilder().withName("Alice Pauline")
            .withExpense("39.60").withIncome("40.60")
            .withDate("18-9-2012")
            .withTags("friends").build();
    public static final Record BENSON = new RecordBuilder().withName("Benson Meier")
            .withExpense("25.80")
            .withIncome("26.80").withDate("18-12-2012")
            .withTags("owesMoney", "friends").build();
    public static final Record CARL = new RecordBuilder().withName("Carl Kurz").withDate("10-12-2012")
            .withIncome("26.90").withExpense("25.90").build();
    public static final Record DANIEL = new RecordBuilder().withName("Daniel Meier").withDate("10-1-2012")
            .withIncome("31.50").withExpense("30.50").withTags("friends").build();
    public static final Record ELLE = new RecordBuilder().withName("Elle Meyer").withDate("10-1-2003")
            .withIncome("50.99").withExpense("49.99").build();
    public static final Record FIONA = new RecordBuilder().withName("Fiona Kunz").withDate("31-12-1996")
            .withIncome("26.89").withExpense("25.89").build();
    public static final Record GEORGE = new RecordBuilder().withName("George Best").withDate("23-10-2010")
            .withIncome("4.50").withExpense("3.50").build();

    // Manually added
    public static final Record HOON = new RecordBuilder().withName("Hoon Meier").withDate("31-4-2080")
            .withIncome("11.50").withExpense("11.50").build();
    public static final Record IDA = new RecordBuilder().withName("Ida Mueller").withDate("4-10-2030")
            .withIncome("12.30").withExpense("12.30").build();

    // Manually added - Record's details found in {@code CommandTestUtil}
    public static final Record AMY = new RecordBuilder().withName(VALID_NAME_AMY).withDate(VALID_DATE_AMY)
            .withIncome(VALID_INCOME_AMY).withExpense(VALID_EXPENSE_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Record BOB = new RecordBuilder().withName(VALID_NAME_BOB).withDate(VALID_DATE_BOB)
            .withIncome(VALID_INCOME_BOB).withExpense(VALID_EXPENSE_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

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
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
