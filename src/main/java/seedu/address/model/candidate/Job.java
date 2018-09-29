package seedu.address.model.candidate;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Candidate's desired job in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidJob(String)}
 */

public class Job {
    public static final String JOB_VALIDATION_REGEX = "[\\p{Alpha}]+";

    public static final String MESSAGE_JOB_CONSTRAINTS =
            "Desired job should only contain alphabetical and should not be blank ";

    public final String job;

    public Job(String jobInput) {
        requireNonNull(jobInput);
        checkArgument(isValidJob(jobInput), MESSAGE_JOB_CONSTRAINTS);
        job = jobInput;
    }

    /**
     * Returns true if a given string is a valid job.
     */
    public static boolean isValidJob(String test) {
        return test.matches(JOB_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Job // instanceof handles nulls
                && job.equals(((Job) other).job)); // state check
    }

    @Override
    public int hashCode() {
        return job.hashCode();
    }

}
