package seedu.address.model.recruitment;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a RecruitmentPost's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class JobDescription {

    public static final String MESSAGE_JOB_DESCRIPTION_CONSTRAINTS =
            "Job description accepts only characters. It should not include numbers or should not be blank. "
                    + "For the purpose of using punctuation marks, it only allows comma, "
                    + "full stop and single right quote. The length of job description is from 1 to 200 characters.";


    /*
     * The first character of the recruitment post must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String JOB_DESCRIPTION_VALIDATION_REGEX = "[a-zA-Z ,.'’]{1,200}";


    public final String value;
    public JobDescription(String jobDescription) {
        requireNonNull(jobDescription);
        checkArgument(isValidJobDescription(jobDescription), MESSAGE_JOB_DESCRIPTION_CONSTRAINTS);
        value = jobDescription;
    }
    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidJobDescription(String test) {
        return test.matches(JOB_DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobDescription // instanceof handles nulls
                && value.equals(((JobDescription) other).value)); // state check
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
