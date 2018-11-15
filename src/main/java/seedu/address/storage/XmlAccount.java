package seedu.address.storage;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.login.UserId;
import seedu.address.model.login.UserPassword;
import seedu.address.model.login.UserRole;

/**
 * JAXB-friendly version of the Account.
 */
public class XmlAccount {

    public static final String MISSING_ACCOUNT_FIELD_MESSAGE_FORMAT = "LoginDetail's %s field is missing!";

    @XmlElement(required = true)
    private String userId;
    @XmlElement(required = true)
    private String userPassword;
    @XmlElement(required = true)
    private String userRole;

    /**
     * Constructs an XmlAccount.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAccount() {}

    /**
     * Constructs an {@code XmlAccount} with the given account details.
     */
    public XmlAccount(String userId, String userPassword, String userRole) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }

    /**
     * Converts a given LoginDetails into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAccount
     */
    public XmlAccount(LoginDetails source) {
        userId = source.getUserId().fullUserId;
        userPassword = source.getUserPassword().fullUserPassword;
        userRole = source.getUserRole().fullUserRole;

    }

    /**
     * Converts this jaxb-friendly account object into the model's LoginDetails object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the account
     */
    public LoginDetails toModelType() throws IllegalValueException, UnsupportedEncodingException {
        UserId modelUserId;
        UserPassword modelUserPassword;
        UserRole modelUserRole;
        if (userId == null) {
            throw new IllegalValueException(String.format(MISSING_ACCOUNT_FIELD_MESSAGE_FORMAT,
                    UserId.class.getSimpleName()));
        }
        modelUserId = new UserId(userId);

        if (userPassword == null) {
            throw new IllegalValueException(String.format(MISSING_ACCOUNT_FIELD_MESSAGE_FORMAT,
                                                          UserPassword.class.getSimpleName()));
        }
        modelUserPassword = new UserPassword(userPassword);
        if (userRole == null) {
            throw new IllegalValueException(String.format(MISSING_ACCOUNT_FIELD_MESSAGE_FORMAT,
                    UserRole.class.getSimpleName()));
        }
        modelUserRole = new UserRole(userRole);

        return new LoginDetails(modelUserId, modelUserPassword, modelUserRole);
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
                && Objects.equals(userPassword, otherLoginDetails.userPassword)
                && Objects.equals(userRole, otherLoginDetails.userRole);
    }
}
