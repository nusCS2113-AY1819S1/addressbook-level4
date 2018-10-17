package seedu.address.model.grade;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class TestName {
    public static final String MESSAGE_TEST_NAME_CONSTRAINTS =
            " Test names should only contain alphanumeric characters and "
            + "certain characters such as '[', ']' and '-' and should it not be blank";

    public static final String TEST_NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public final String testName;

    /**
     * Constructs a {@code TestName}.
     *
     * @param testName A valid number.
     */
    public TestName(String testName) {
        requireNonNull(testName);
        checkArgument(isValidTestName(testName), MESSAGE_TEST_NAME_CONSTRAINTS);
        this.testName = testName;
    }

    /**
     * Returns true if a given string is a valid test name.
     */
    public static boolean isValidTestName(String test) {

        return test.matches(TEST_NAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return testName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TestName // instanceof handles nulls
                && testName.equals(((TestName) other).testName)); // state check
    }

    @Override
    public int hashCode() {
        return testName.hashCode();
    }
}
