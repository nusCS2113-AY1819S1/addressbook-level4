package seedu.address.model.distributor;
//package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Distributor's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class DistributorPhone {


    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Distributor phone numbers should only contain numbers, and it should be at least 8 digits long";
    public static final String PHONE_VALIDATION_REGEX = "\\d{8,}";
    public final String value;

    /**
     * Constructs a {@code DistributorPhone}.
     *
     * @param distphone A valid phone number.
     */
    public DistributorPhone(String distphone) {
        //requireNonNull(distphone);
        checkArgument(isValidPhone(distphone), MESSAGE_PHONE_CONSTRAINTS);
        value = distphone;
    }

    /**
     * Returns true if a given string is a valid distributor phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PHONE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.distributor.DistributorPhone // instanceof handles nulls
                && value.equals(((seedu.address.model.distributor.DistributorPhone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
