package seedu.address.testutil;

import seedu.address.model.ledger.Account;
import seedu.address.model.ledger.DateLedger;
import seedu.address.model.ledger.Ledger;
import seedu.address.model.member.Address;
import seedu.address.model.member.Email;
import seedu.address.model.member.Major;
import seedu.address.model.member.Name;
import seedu.address.model.member.Person;
import seedu.address.model.member.Phone;
import seedu.address.model.member.Postalcode;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * A utility class to help with building Person objects.
 */
public class LedgerBuilder {

    public static final String DEFAULT_DATE = "10/10";
    public static final String DEFAULT_ACCOUNT = "1010.01";

    private DateLedger dateLedger;
    private Account account;

    public LedgerBuilder() {
        dateLedger = new DateLedger(DEFAULT_DATE);
        account = new Account(Double.parseDouble(DEFAULT_ACCOUNT));
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public LedgerBuilder(Ledger ledgerToCopy) {
        dateLedger = ledgerToCopy.getDateLedger();
        account = ledgerToCopy.getAccount();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public LedgerBuilder withDateLedger(String date) {
        this.dateLedger = new DateLedger(date);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public LedgerBuilder withAccount(Double account) {
        this.account = new Account(account);
        return this;
    }

    public Ledger build() {
        return new Ledger(dateLedger, new Account(0.0));
    }

}
