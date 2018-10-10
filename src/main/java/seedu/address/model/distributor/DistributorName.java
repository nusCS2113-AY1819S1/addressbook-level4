package seedu.address.model.distributor;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Distributor's name in the Inventarie.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class DistributorName {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Distributor names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullDistName;

    /**
     * Constructs a {@code DistributorName}.
     *
     * @param distname A valid distname.
     */
    public DistributorName(String distname) {
        requireNonNull(distname);
        checkArgument(isValidName(distname), MESSAGE_NAME_CONSTRAINTS);
        fullDistName = distname;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullDistName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.distributor.DistributorName // instanceof handles nulls
                && fullDistName.equals(((seedu.address.model.distributor.DistributorName) other).fullDistName)); // state check
    }

    @Override
    public int hashCode() {
        return fullDistName.hashCode();
    }

}

