package seedu.address.model.grade;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.person.Name;

/**
 * Represents an object that has person name and test name and marks in the address book.
 *
 * Guarantees: details are present and not null, field values are validate
 * d, immutable.
 */

public class PersonTest {

    private final Name personName;
    private final TestName testName;
    private final Marks marks;

    public PersonTest(Name personName, TestName testName, Marks marks) {
        requireAllNonNull(personName, testName, marks);
        this.personName = personName;
        this.testName = testName;
        this.marks = marks;
    }

    public Name getName() {
        return personName;
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
    public boolean isSamePersonTest(PersonTest otherTest) {
        if (otherTest == this) {
            return true;
        }
        return otherTest != null
                && otherTest.getName().equals(getName())
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

        if (!(other instanceof PersonTest)) {
            return false;
        }

        PersonTest otherTest = (PersonTest) other;
        return otherTest.getName().equals(getName())
                && otherTest.getTestName().equals(getTestName())
                && otherTest.getMarks().equals(getMarks());

    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(personName, testName, marks);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Person Name: ")
                .append(getTestName())
                .append(" Test Name: ")
                .append(getMarks())
                .append(" Marks: ");
        return builder.toString();
    }
}
