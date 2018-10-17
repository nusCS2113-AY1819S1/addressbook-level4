package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.LoginBook;
import seedu.address.model.ReadOnlyLoginBook;
import seedu.address.model.login.LoginDetails;

/**
 * An Immutable LoginBook that is serializable to XML format
 */
@XmlRootElement(name = "loginbook")
public class XmlSerializableLoginBook {

    public static final String MESSAGE_DUPLICATE_ACCOUNT = "LoginDetails list contains duplicate account(s).";
    @XmlElement
    private List<XmlAccount> accounts;

    /**
     * Creates an empty XmlSerializableLoginBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableLoginBook() {
        accounts = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableLoginBook(ReadOnlyLoginBook src) {
        this();
        accounts.addAll(src.getLoginDetailsList().stream().map(XmlAccount::new).collect(Collectors.toList()));
    }

    /**
     * Converts this loginbook into the model's {@code LoginBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAccount}.
     */
    public LoginBook toModelType() throws IllegalValueException {
        LoginBook loginBook = new LoginBook();
        for (XmlAccount l : accounts) {
            LoginDetails loginDetails = l.toModelType();
            if (loginBook.hasAccount(loginDetails)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ACCOUNT);
            }
            loginBook.createAccount(loginDetails);
        }
        return loginBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableLoginBook)) {
            return false;
        }
        return accounts.equals(((XmlSerializableLoginBook) other).accounts);
    }
}
