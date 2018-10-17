package seedu.address.model.grade;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
/**
 * Represents a Test in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class Test {
    private final TestName testName;
    private final Marks marks;

    public Test(TestName testName, Marks marks) {
        requireAllNonNull(testName, marks);
        this.testName = testName;
        this.marks = marks;
    }

 public TestName getTestName() {
  return testName;
 }
 public Marks getMarks() {
  return marks;
 }
    /**
     * Every field must be present and not null.
     */
    public boolean isSameTest(Test otherTest) {
        if (otherTest == this) {
            return true;
        }
        return otherTest != null
                && otherTest.getTestName().equals(getTestName())
                && (otherTest.getMarks().equals(getMarks()));
    }
    /**
     * Returns true if both groups have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Test)) {
            return false;
        }

        Test otherTest = (Test) other;
        return otherTest.getTestName().equals(getTestName())
                && otherTest.getMarks().equals(getMarks());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(testName, marks);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTestName())
                .append(" Test Name: ")
                .append(getMarks())
                .append(" Marks: ");
        return builder.toString();
    }
}
