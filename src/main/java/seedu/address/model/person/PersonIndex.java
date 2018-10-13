package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an PersonIndex from person panel.
 * Guarantees: immutable; name is valid as declared in {@link #isValidPersonIndex(String)}
 */
public class PersonIndex {

    public static final String MESSAGE_PERSON_INDEX_CONSTRAINTS = "Index's should be numeric";
    public static final String PERSON_INDEX_VALIDATION_REGEX = "\\p{Digit}+";


    public final String personIndex;

    /**
     * Constructs a {@code PersonIndex}.
     *
     * @param personIndex A valid index.
     */
    public PersonIndex(String personIndex) {
        requireNonNull(personIndex);
        checkArgument(isValidPersonIndex(personIndex), MESSAGE_PERSON_INDEX_CONSTRAINTS);
        this.personIndex = personIndex;
    }

    /**
     * Returns true if a given string is a valid index.
     */
    public static boolean isValidPersonIndex(String test) {
        return test.matches(PERSON_INDEX_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonIndex // instanceof handles nulls
                && personIndex.equals(((PersonIndex) other).personIndex)); // state check
    }

    @Override
    public int hashCode() {
        return personIndex.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + personIndex + ']';
    }

}
