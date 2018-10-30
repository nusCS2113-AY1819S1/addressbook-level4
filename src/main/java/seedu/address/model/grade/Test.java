package seedu.address.model.grade;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.index.Index;

/**
 * Represents a Test in the address book.
 *
 * Guarantees: details are present and not null, field values are validate
 * d, immutable.
 */

public class Test {
    public final TestName testName;
    public final Marks marks;
    public final Grade grade;
    final ArrayList<Test> testAdded = new ArrayList<>();

    public Test(TestName testName, Marks marks, Grade grade) {
        requireAllNonNull(testName, marks, grade);
        this.testName = testName;
        this.marks = marks;
        this.grade = grade;
    }


    public TestName getTestName() {
        return testName;
    }
    public Marks getMarks() {
        return marks;
    }
    public Grade getGrade() {
        return grade;
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
                && otherTest.getMarks().equals(getMarks());
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
                && otherTest.getMarks().equals(getMarks())
                && otherTest.getGrade().equals(getGrade());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(testName, marks, grade);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTestName())
                .append(" Test Name: ")
                .append(getMarks())
                .append(" Marks: ")
                .append(getGrade())
                .append(" Grade: ");
        return builder.toString();
    }
}
