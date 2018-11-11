package seedu.planner.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the financial planner.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric,"
            + " less than 20 characters long. \nIt should consist only 1 word. Whitespace is not allowed.";
    public static final String MESSAGE_TAG_NUM_CONSTRAINTS = "Number of tags should not exceed %d.";
    public static final String TAG_VALIDATION_REGEX = "\\p{Alnum}{1,20}";
    public static final int NUM_MAX_TAGS = 2;

    public final String tagName;

    /**
     * Constructs a {@code Tag} after converting it to lowercase.
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        tagName = tagName.toLowerCase();
        checkArgument(isValidTagName(tagName), MESSAGE_TAG_CONSTRAINTS);
        this.tagName = tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(TAG_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && tagName.equals(((Tag) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
