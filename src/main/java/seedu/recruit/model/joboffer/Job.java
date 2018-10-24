package seedu.recruit.model.joboffer;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.commons.util.AppUtil.checkArgument;

/**
 * Represents a Candidate's desired job/job offered in the candidate book/CompanyBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidJob(String)}
 */

public class Job {
    public static final String JOB_VALIDATION_REGEX = "[\\p{Alpha}\\p{Blank}]+";

    public static final String MESSAGE_JOB_CONSTRAINTS =
            "Desired job should only contain alphabetical characters and should not be blank ";

    public final String value;

    public Job(String jobInput) {
        requireNonNull(jobInput);
        checkArgument(isValidJob(jobInput), MESSAGE_JOB_CONSTRAINTS);
        value = jobInput;
    }

    /**
     * Returns true if a given string is a valid job.
     */
    public static boolean isValidJob(String test) {
        return test.matches(JOB_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Job // instanceof handles nulls
                && value.equals(((Job) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
