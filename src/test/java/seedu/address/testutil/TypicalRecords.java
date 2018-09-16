package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
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
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withDate("18-9-2012")
            .withTags("friends").build();
    public static final Record BENSON = new RecordBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withDate("18-12-2012")
            .withTags("owesMoney", "friends").build();
    public static final Record CARL = new RecordBuilder().withName("Carl Kurz").withDate("10-12-2012")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Record DANIEL = new RecordBuilder().withName("Daniel Meier").withDate("10-1-2012")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Record ELLE = new RecordBuilder().withName("Elle Meyer").withDate("10-1-2003")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Record FIONA = new RecordBuilder().withName("Fiona Kunz").withDate("31-12-1996")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Record GEORGE = new RecordBuilder().withName("George Best").withDate("23-10-2010")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Record HOON = new RecordBuilder().withName("Hoon Meier").withDate("31-4-2080")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Record IDA = new RecordBuilder().withName("Ida Mueller").withDate("4-10-2030")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Record's details found in {@code CommandTestUtil}
    public static final Record AMY = new RecordBuilder().withName(VALID_NAME_AMY).withDate(VALID_DATE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Record BOB = new RecordBuilder().withName(VALID_NAME_BOB).withDate(VALID_DATE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
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
