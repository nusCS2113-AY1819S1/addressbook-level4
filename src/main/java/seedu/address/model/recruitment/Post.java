package seedu.address.model.recruitment;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a RecruitmentPost's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Post {

    public static final String MESSAGE_POST_CONSTRAINTS =
            "Job Position accepts only characters. It should not include numbers or should not be blank. "
                    + "And the maximum length of the job position is 20 characters";


    /*
     * The first character of the recruitment post must not be a whitespace
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String POST_VALIDATION_REGEX = "[a-zA-Z ]{1,20}";


    public final String value;
    public Post(String post) {
        requireNonNull(post);
        checkArgument(isValidPost(post), MESSAGE_POST_CONSTRAINTS);
        value = post;
    }
    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidPost(String test) {
        return test.matches(POST_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Post // instanceof handles nulls
                && value.equals(((Post) other).value)); // state check
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
