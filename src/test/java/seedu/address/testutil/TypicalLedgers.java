package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.ledger.Ledger;

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
