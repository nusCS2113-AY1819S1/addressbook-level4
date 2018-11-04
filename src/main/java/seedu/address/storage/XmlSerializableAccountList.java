package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AccountList;
import seedu.address.model.ReadOnlyAccountList;
import seedu.address.model.account.Account;

/**
 * An Immutable AccountList that is serializable to XML format
 */
@XmlRootElement(name = "accountlist")
public class XmlSerializableAccountList {
    public static final String MESSAGE_DUPLICATE_ACCOUNT = "Account list contains duplicate account(s).";

    @XmlElement
    private List<XmlAdaptedAccount> accounts;

    /**
     * Creates an empty XmlSerializableAccountList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAccountList() {
        accounts = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAccountList(ReadOnlyAccountList src) {
        this();
        accounts.addAll(src.getAccountList().stream().map(XmlAdaptedAccount::new).collect(Collectors.toList()));
    }

    /**
     * Converts account list into the model's {@code AccountList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedAccount}.
     */
    public AccountList toModelType() throws IllegalValueException {
        AccountList accountList = new AccountList();
        for (XmlAdaptedAccount p : accounts) {
            Account account = p.toModelType();
            if (accountList.hasAccount(account)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ACCOUNT);
            }
            accountList.addAccount(account);
        }
        return accountList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAccountList)) {
            return false;
        }
        return accounts.equals(((XmlSerializableAccountList) other).accounts);
    }
}
