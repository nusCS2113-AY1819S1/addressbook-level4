package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.login.UserId;
import seedu.address.model.login.UserPassword;

/**
 * JAXB-friendly version of the Account.
 */
public class XmlAccount {

    public static final String MISSING_ACCOUNT_FIELD_MESSAGE_FORMAT = "LoginDetail's %s field is missing!";

    @XmlElement(required = true)
    private String userId;
    @XmlElement(required = true)
    private String userPassword;

    /**
     * Constructs an XmlAccount.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAccount() {}

    /**
     * Constructs an {@code XmlAccount} with the given account details.
     */
    public XmlAccount(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    /**
     * Converts a given LoginDetails into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAccount
     */
    public XmlAccount(LoginDetails source) {
        userId = source.getUserId().fullUserId;
        userPassword = source.getUserPassword().fullUserPassword;
    }

    /**
     * Converts this jaxb-friendly account object into the model's LoginDetails object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the account
     */
    public LoginDetails toModelType() throws IllegalValueException {
        if (userId == null) {
            throw new IllegalValueException(String.format(MISSING_ACCOUNT_FIELD_MESSAGE_FORMAT,
                    UserId.class.getSimpleName()));
        }
        if (!UserId.isValidUserId(userId)) {
            throw new IllegalValueException(UserId.MESSAGE_USERID_CONSTRAINTS);
        }
        final UserId modelUserId = new UserId(userId);

        if (userPassword == null) {
            throw new IllegalValueException(String.format(MISSING_ACCOUNT_FIELD_MESSAGE_FORMAT,
                                                          UserPassword.class.getSimpleName()));
        }
        if (!UserPassword.isValidUserPassword(userPassword)) {
            throw new IllegalValueException(UserPassword.MESSAGE_USERPASSWORD_CONSTRAINTS);
        }
        final UserPassword modelUserPassword = new UserPassword(userPassword);

        return new LoginDetails(modelUserId, modelUserPassword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAccount)) {
            return false;
        }

        XmlAccount otherLoginDetails = (XmlAccount) other;
        return Objects.equals(userId, otherLoginDetails.userId)
                && Objects.equals(userPassword, otherLoginDetails.userPassword);
    }
}
