package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Event's contact in the event manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidComment(String)}
 */
public class Comments {

    public static final String MESSAGE_COMMENTS_CONSTRAINTS =
            "Comments should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the contact must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String COMMENT_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullComments;

    /**
     * Constructs a {@code Contact}.
     *
     * @param contact A valid contact.
     */
    public Comments(String comments) {
        requireNonNull(comments);
        checkArgument(isValidComment(comments), MESSAGE_COMMENTS_CONSTRAINTS);
        fullComments = comments;
    }

    /**
     * Returns true if a given string is a valid contact.
     */
    public static boolean isValidComment(String test) {
        return test.matches(COMMENT_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullComments;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Contact // instanceof handles nulls
                && fullComments.equals(((Contact) other).fullContactName)); // state check
    }

    @Override
    public int hashCode() {
        return fullComments.hashCode();
    }

}
