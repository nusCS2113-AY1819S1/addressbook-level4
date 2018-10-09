package seedu.recruit.storage;

import static org.junit.Assert.assertEquals;
import static seedu.recruit.storage.XmlAdaptedCandidate.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.recruit.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.recruit.commons.exceptions.IllegalValueException;
import seedu.recruit.model.candidate.Name;
import seedu.recruit.model.commons.Address;
import seedu.recruit.model.commons.Email;
import seedu.recruit.model.commons.Phone;
import seedu.recruit.testutil.Assert;

public class XmlAdaptedCandidateTest {
    private static final String INVALID_NAME = "R@chel";
    //private static final String INVALID_GENDER = "U";
    //private static final String INVALID_AGE = "123";
    private static final String INVALID_PHONE = "+651234";

    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_AGE = BENSON.getAge().toString();
    private static final String VALID_GENDER = BENSON.getGender().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_JOB = BENSON.getJob().toString();
    private static final String VALID_EDUCATION = BENSON.getEducation().toString();
    private static final String VALID_SALARY = BENSON.getSalary().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedCandidate person = new XmlAdaptedCandidate(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedCandidate person =
                new XmlAdaptedCandidate(INVALID_NAME, VALID_GENDER, VALID_AGE, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_JOB, VALID_EDUCATION, VALID_SALARY, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedCandidate person = new XmlAdaptedCandidate(null, VALID_GENDER, VALID_AGE, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_JOB, VALID_EDUCATION, VALID_SALARY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedCandidate person =
                new XmlAdaptedCandidate(VALID_NAME, VALID_GENDER, VALID_AGE, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_JOB, VALID_EDUCATION, VALID_SALARY, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedCandidate person = new XmlAdaptedCandidate(VALID_NAME, VALID_GENDER, VALID_AGE, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_JOB, VALID_EDUCATION, VALID_SALARY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedCandidate person =
                new XmlAdaptedCandidate(VALID_NAME, VALID_GENDER, VALID_AGE, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_JOB, VALID_EDUCATION, VALID_SALARY, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedCandidate person = new XmlAdaptedCandidate(VALID_NAME, VALID_GENDER, VALID_AGE, VALID_PHONE, null,
                VALID_ADDRESS, VALID_JOB, VALID_EDUCATION, VALID_SALARY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedCandidate person =
                new XmlAdaptedCandidate(VALID_NAME, VALID_GENDER, VALID_AGE, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_JOB, VALID_EDUCATION, VALID_SALARY, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedCandidate person = new XmlAdaptedCandidate(VALID_NAME, VALID_GENDER, VALID_AGE, VALID_PHONE,
                VALID_EMAIL, null, VALID_JOB, VALID_EDUCATION, VALID_SALARY, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedCandidate person =
                new XmlAdaptedCandidate(VALID_NAME, VALID_GENDER, VALID_AGE, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_JOB, VALID_EDUCATION, VALID_SALARY, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

}
