package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.ledger.Ledger;
import seedu.address.model.member.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalLedgers {

    public static final Ledger DAY1 = new LedgerBuilder().withDateLedger("10/10").build();
    public static final Ledger DAY2 = new LedgerBuilder().withDateLedger("32/11").build();
    public static final Ledger DAY3 = new LedgerBuilder().withDateLedger("29/02").build();


    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalLedgers() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Ledger ledger : getTypicalLedgers()) {
            ab.addLedger(ledger);
        }
        return ab;
    }

    public static List<Ledger> getTypicalLedgers() {
        return new ArrayList<>(Arrays.asList(DAY1, DAY2, DAY3));
    }
}
