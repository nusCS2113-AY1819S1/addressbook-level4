package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.grade.Grade;
import seedu.address.model.grade.Marks;
import seedu.address.model.grade.Test;
import seedu.address.model.grade.TestName;



/**
 * JAXB-friendly version of the test scores.
 */
public class XmlAdaptedTest {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Score's %s field is missing!";

    @XmlElement(required = true)
    private String testName;
    @XmlElement(required = true)
    private String marks;
    @XmlElement(required = true)
    private String grade;


    /**
     * Constructs an XmlAdaptedTest.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTest() {
    }

    /**
     * Constructs an {@code XmlAdaptedGroup} with the given group details.
     */
    public XmlAdaptedTest(String testname, String scores, String grade) {
        this.testName = testname;
        this.marks = scores;
        this.grade = grade;

    }

    /**
     * Converts a given group into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedGroup
     */
    public XmlAdaptedTest(Test source) {
        testName = source.getTestName().testName;
        marks = source.getMarks().value;
        grade = source.getGrade().value;
    }

    /**
     * Converts this jaxb-friendly adapted Test object into the model's Test object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted group
     */
    public Test toModelType() throws IllegalValueException {

        if (testName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TestName.class.getSimpleName()));
        }
        if (!TestName.isValidTestName(testName)) {
            throw new IllegalValueException(TestName.MESSAGE_TEST_NAME_CONSTRAINTS);
        }
        TestName modelTestName = new TestName(testName);

        if (marks == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Marks.class.getSimpleName()));
        }
        if (!Marks.isValidMarks(marks)) {
            throw new IllegalValueException(Marks.MESSAGE_MARKS_CONSTRAINTS);
        }
        Marks modelMarks = new Marks(marks);
        if (grade == null) {
            grade = " ";
        }
        if (!Grade.isValidGrade(grade)) {
            throw new IllegalValueException(Grade.MESSAGE_GRADE_CONSTRAINTS);
        }
        final Grade modelGrades = new Grade(grade);

        return new Test(modelTestName, modelMarks, modelGrades);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTest)) {
            return false;
        }

        XmlAdaptedTest otherTest = (XmlAdaptedTest) other;
        return Objects.equals(testName, otherTest.testName)
                && Objects.equals(marks, otherTest.marks);

    }
}

