package seedu.address.model.person.addinfo;

import static java.util.Objects.requireNonNull;

/**
 * Represents a person's occupation in Health Book.
 */
public class Occupation {
    public static final String MESSAGE_OCCUPATION_CONSTRAINTS = "Occupation should not contain invalid characters"
            + " and whitespaces.";

    private static final String OCCUPATION_VALIDATION_REGEX = "[a-zA-Z]+";

    public final String value;

    public Occupation(String occupation) {
        requireNonNull(occupation);
        value = occupation;
    }

    public static boolean isValidOccupation(String test) {
        return test.matches(OCCUPATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Occupation && value.equals(((Occupation) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
