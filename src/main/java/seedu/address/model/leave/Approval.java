package seedu.address.model.leave;

import static java.util.Objects.requireNonNull;

//@@author Hafizuddin-NUS
/**
 * Represents a Leave's approval in the leave list.
 * Guarantees: immutable; is valid as declared in
 */
public class Approval {

    public static final String MESSAGE_APPROVAL_CONSTRAINTS = "Incorrect Approval format(PENDING, APPROVED, REJECTED).";
    public static final String APPROVAL_PENDING_VALIDATION_REGEX = "PENDING";
    public static final String APPROVAL_APPROVED_VALIDATION_REGEX = "APPROVED";
    public static final String APPROVAL_REJECTED_VALIDATION_REGEX = "REJECTED";

    public final String status;

    /**
     * Constructs an {@code Approval}.
     *
     * @param status A valid status.
     */
    public Approval(String status) {
        requireNonNull(status);
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Approval // instanceof handles nulls
                && status.equals(((Approval) other).status)); // state check
    }

    /**
     * Returns true if a given string is a valid approval.
     */
    public static boolean isValidApproval(String test) {
        return test.matches(APPROVAL_PENDING_VALIDATION_REGEX) || test.matches(APPROVAL_APPROVED_VALIDATION_REGEX)
                || test.matches(APPROVAL_REJECTED_VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }


}
