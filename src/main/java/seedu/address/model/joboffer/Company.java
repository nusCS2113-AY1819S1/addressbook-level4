package seedu.address.model.joboffer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the company offering the job in the job book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCompany(String)}
 */

public class Company {
    public static final String COMPANY_VALIDATION_REGEX = "[\\p{Alpha}]+";

    public static final String MESSAGE_COMPANY_CONSTRAINTS =
            "Company should only contain alphabetical characters and should not be blank ";

    public final String value;

    public Company(String companyInput) {
        requireNonNull(companyInput);
        checkArgument(isValidCompany(companyInput), MESSAGE_COMPANY_CONSTRAINTS);
        value = companyInput;
    }

    /**
     * Returns true if a given string is a valid company name.
     */
    public static boolean isValidCompany(String test) {
        return test.matches(COMPANY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Company // instanceof handles nulls
                && value.equals(((Company) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
