package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.EnumSet;

//@@author jitwei98
/**
 * Represents filetype available for import/export.
 * Guarantees: immutable; is valid as declared in {@link #isValidFiletype(String)}
 */
public class Filetype {

    public static final String MESSAGE_FILETYPE_CONSTRAINTS =
            "Filetype can take either \"csv\" or \"vcf\", and it should not be blank";

    /*
     * The first character of the filetype must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String FILETYPE_VALIDATION_REGEX = "[^\\s].*";

    /**
     * Filetypes that can be used to export contacts.
     */
    public enum Extension {
        csv, vcf
    }

    private final String value;

    /**
     * Constructs an {@code Filetype}.
     *
     * @param filetype A valid filetype.
     */
    public Filetype(String filetype) {
        requireNonNull(filetype);
        checkArgument(isValidFiletype(filetype), MESSAGE_FILETYPE_CONSTRAINTS);
        value = filetype;
    }

    /**
     * Returns true if a given string is a valid filetype.
     */
    public static boolean isValidFiletype(String test) {
        return test.matches(FILETYPE_VALIDATION_REGEX) && isValidExtension(test);
    }

    /**
     * Returns true if a given string matches any of the valid Extension.
     */
    public static boolean isValidExtension(String extension) {
        return contains(Extension.class, extension);
    }

    // Reused from
    // http://www.java2s.com/Tutorials/Java/Data_Type_How_to/String/
    // Check_if_enum_contains_a_given_string.html with minor modifications

    /**
     * Returns true if an {@code enumClass} contains a specific {@code value}.
     * @param enumClass
     * @param value
     */
    private static <E extends Enum<E>> boolean contains(Class<E> enumClass,
                                                       String value) {
        try {
            return EnumSet.allOf(enumClass)
                    .contains(Enum.valueOf(enumClass, value));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Filetype // instanceof handles nulls
                && value.equals(((Filetype) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
