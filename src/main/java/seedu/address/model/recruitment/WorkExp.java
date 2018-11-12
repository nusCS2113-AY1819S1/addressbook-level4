package seedu.address.model.recruitment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a RecruitmentPost's minimal working experience in the address book.
 * Guarantees: immutable; is always valid
 */
public class WorkExp {

    public static final String MESSAGE_WORK_EXP_CONSTRAINTS =
            "Working Experience should only contain integers with length of at least 1 digit long"
                    + "And the range of the working experience is from 0 to 30";
    public static final String EMPLOYEE_WORK_EXP_VALIDATION_REGEX = "^(0?[0-9]|[12][0-9]|30)";

    public final String workExp;

    /**
     * Constructs a {@code WorkExp}.
     *
     * @param workExp A valid Working Experience.
     */
    public WorkExp(String workExp) {
        requireNonNull(workExp);
        checkArgument(isValidWorkExp(workExp), MESSAGE_WORK_EXP_CONSTRAINTS);
        this.workExp = workExp;
    }

    /**
     * Returns true if a given string is a valid Working Experience.
     */
    public static boolean isValidWorkExp(String test) {
        return test.matches(EMPLOYEE_WORK_EXP_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return workExp;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WorkExp // instanceof handles nulls
                && workExp.equals(((WorkExp) other).workExp)); // state check
    }

    @Override
    public int hashCode() {
        return workExp.hashCode();
    }

}
