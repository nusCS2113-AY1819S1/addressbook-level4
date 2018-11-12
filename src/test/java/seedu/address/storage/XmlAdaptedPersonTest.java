package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATEOFBIRTH_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPLOYEEID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALARY_AMY;
import static seedu.address.storage.addressbook.XmlAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Salary;
import seedu.address.storage.addressbook.XmlAdaptedPerson;
import seedu.address.storage.addressbook.XmlAdaptedTag;
import seedu.address.testutil.Assert;
import seedu.address.testutil.PersonBuilder;

public class XmlAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_DEPARTMENT = "HR3";
    private static final String INVALID_POSITION = "Intern1";
    private static final String INVALID_EMPLOYEEID = "12345a";
    private static final String INVALID_SALARY = "7a";
    private static final String INVALID_DATEOFBIRTH = "12/03/9999";

    private static final String VALID_EMPLOYEEID = BENSON.getEmployeeId().toString();
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_DATEOFBIRTH = BENSON.getDateOfBirth().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_DEPARTMENT = BENSON.getDepartment().toString();
    private static final String VALID_DEPARTMENT_FOR_TEST = "Finance";
    private static final String VALID_POSITION = BENSON.getPosition().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_SALARY = BENSON.getSalary().toString();
    private static final String VALID_BONUS = BENSON.getBonus().toString();
    private static final String VALID_TAG = "owesMoney";
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    private XmlAdaptedPerson benson = new XmlAdaptedPerson(BENSON);
    private XmlAdaptedTag bensonTag = new XmlAdaptedTag(VALID_TAG);

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedPerson person = new XmlAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidEmployeeId_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(INVALID_EMPLOYEEID, VALID_NAME, VALID_DATEOFBIRTH, VALID_PHONE, VALID_EMAIL,
                        VALID_DEPARTMENT, VALID_POSITION, VALID_ADDRESS, VALID_SALARY, VALID_BONUS, VALID_TAGS);
        String expectedMessage = EmployeeId.MESSAGE_EMPLOYEEID_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmployeeId_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(null, VALID_NAME, VALID_DATEOFBIRTH, VALID_PHONE,
                VALID_EMAIL, VALID_DEPARTMENT, VALID_POSITION, VALID_ADDRESS, VALID_SALARY, VALID_BONUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EmployeeId.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_EMPLOYEEID, INVALID_NAME, VALID_DATEOFBIRTH, VALID_PHONE, VALID_EMAIL,
                        VALID_DEPARTMENT, VALID_POSITION, VALID_ADDRESS, VALID_SALARY, VALID_BONUS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_EMPLOYEEID, null, VALID_DATEOFBIRTH, VALID_PHONE,
                VALID_EMAIL, VALID_DEPARTMENT, VALID_POSITION, VALID_ADDRESS, VALID_SALARY, VALID_BONUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDateOfBirth_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_EMPLOYEEID, VALID_NAME, INVALID_DATEOFBIRTH, VALID_PHONE,
                VALID_EMAIL, VALID_DEPARTMENT, VALID_POSITION, VALID_ADDRESS, VALID_SALARY, VALID_BONUS, VALID_TAGS);
        String expectedMessage = DateOfBirth.MESSAGE_DATEOFBIRTH_CONSTRAINTS_DEFAULT;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDateOfBirth_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_EMPLOYEEID, VALID_NAME, null, VALID_PHONE,
                VALID_EMAIL, VALID_DEPARTMENT, VALID_POSITION, VALID_ADDRESS, VALID_SALARY, VALID_BONUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateOfBirth.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_EMPLOYEEID, VALID_NAME, VALID_DATEOFBIRTH, INVALID_PHONE, VALID_EMAIL,
                        VALID_DEPARTMENT, VALID_POSITION, VALID_ADDRESS, VALID_SALARY, VALID_BONUS, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_EMPLOYEEID, VALID_NAME, VALID_DATEOFBIRTH, null,
                VALID_EMAIL, VALID_DEPARTMENT, VALID_POSITION, VALID_ADDRESS, VALID_SALARY, VALID_BONUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_EMPLOYEEID, VALID_NAME, VALID_DATEOFBIRTH, VALID_PHONE, INVALID_EMAIL,
                        VALID_DEPARTMENT, VALID_POSITION, VALID_ADDRESS, VALID_SALARY, VALID_BONUS, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_EMPLOYEEID, VALID_NAME, VALID_DATEOFBIRTH, VALID_PHONE,
                null, VALID_DEPARTMENT, VALID_POSITION, VALID_ADDRESS, VALID_SALARY, VALID_BONUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDepartment_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_EMPLOYEEID, VALID_NAME, VALID_DATEOFBIRTH, VALID_PHONE, VALID_EMAIL,
                        INVALID_DEPARTMENT, VALID_POSITION, VALID_ADDRESS, VALID_SALARY, VALID_BONUS, VALID_TAGS);
        String expectedMessage = Department.MESSAGE_DEPARTMENT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDepartment_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_EMPLOYEEID, VALID_NAME, VALID_DATEOFBIRTH, VALID_PHONE,
                VALID_EMAIL, null, VALID_POSITION, VALID_ADDRESS, VALID_SALARY, VALID_BONUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Department.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPosition_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_EMPLOYEEID, VALID_NAME, VALID_DATEOFBIRTH, VALID_PHONE, VALID_EMAIL,
                        VALID_DEPARTMENT, INVALID_POSITION, VALID_ADDRESS, VALID_SALARY, VALID_BONUS, VALID_TAGS);
        String expectedMessage = Position.MESSAGE_POSITION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPosition_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_EMPLOYEEID, VALID_NAME, VALID_DATEOFBIRTH, VALID_PHONE,
                VALID_EMAIL, VALID_DEPARTMENT, null, VALID_ADDRESS, VALID_SALARY, VALID_BONUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Position.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_EMPLOYEEID, VALID_NAME, VALID_DATEOFBIRTH, VALID_PHONE, VALID_EMAIL,
                        VALID_DEPARTMENT, VALID_POSITION, INVALID_ADDRESS, VALID_SALARY, VALID_BONUS, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_EMPLOYEEID, VALID_NAME, VALID_DATEOFBIRTH, VALID_PHONE,
                VALID_EMAIL, VALID_DEPARTMENT, VALID_POSITION, null, VALID_SALARY, VALID_BONUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSalary_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_EMPLOYEEID, VALID_NAME, VALID_DATEOFBIRTH, VALID_PHONE, VALID_EMAIL,
                        VALID_DEPARTMENT, VALID_POSITION, VALID_ADDRESS, INVALID_SALARY, VALID_BONUS, VALID_TAGS);
        String expectedMessage = Salary.MESSAGE_SALARY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSalary_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_EMPLOYEEID, VALID_NAME, VALID_DATEOFBIRTH, VALID_PHONE,
                VALID_EMAIL, VALID_DEPARTMENT, VALID_POSITION, VALID_ADDRESS, null, VALID_BONUS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Salary.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_EMPLOYEEID, VALID_NAME, VALID_DATEOFBIRTH, VALID_PHONE, VALID_EMAIL,
                        VALID_DEPARTMENT, VALID_POSITION, VALID_ADDRESS, VALID_SALARY, VALID_BONUS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void equals_samePerson_true() {
        // same object -> returns true
        assertTrue(benson.equals(benson));
    }

    @Test
    public void equals_sameTags_true() {
        // same object -> returns true
        assertTrue(bensonTag.equals(bensonTag));
    }

    @Test
    public void equals_null_false() {
        // (XmlAdaptedPerson) null object -> returns false
        XmlAdaptedPerson person = new XmlAdaptedPerson(BENSON);
        assertFalse(benson.equals(null));
        assertFalse(person.equals(null));

        // (XmlAdaptedTag) null object -> returns false
        XmlAdaptedTag tag = new XmlAdaptedTag(VALID_TAG);
        assertFalse(bensonTag.equals(null));
        assertFalse(tag.equals(null));
    }

    @Test
    public void equals_differentEmployeeId_false() {
        // different employee id -> returns false
        Person editedBenson = new PersonBuilder(BENSON).withEmployeeId(VALID_EMPLOYEEID_AMY).build();
        XmlAdaptedPerson editedBensonPerson = new XmlAdaptedPerson(editedBenson);
        assertFalse(benson.equals(editedBensonPerson));
    }

    @Test
    public void equals_differentName_false() {
        // different name -> returns false
        Person editedBenson = new PersonBuilder(BENSON).withName(VALID_NAME_AMY).build();
        XmlAdaptedPerson editedBensonPerson = new XmlAdaptedPerson(editedBenson);
        assertFalse(benson.equals(editedBensonPerson));
    }

    @Test
    public void equals_differentDateOfBirth_false() {
        // different date of birth -> returns false
        Person editedBenson = new PersonBuilder(BENSON).withDateOfBirth(VALID_DATEOFBIRTH_AMY).build();
        XmlAdaptedPerson editedBensonPerson = new XmlAdaptedPerson(editedBenson);
        assertFalse(benson.equals(editedBensonPerson));
    }

    @Test
    public void equals_differentPhone_false() {
        // different phone -> returns false
        Person editedBenson = new PersonBuilder(BENSON).withPhone(VALID_PHONE_AMY).build();
        XmlAdaptedPerson editedBensonPerson = new XmlAdaptedPerson(editedBenson);
        assertFalse(benson.equals(editedBensonPerson));
    }

    @Test
    public void equals_differentEmail_false() {
        // different phone -> returns false
        Person editedBenson = new PersonBuilder(BENSON).withEmail(VALID_EMAIL_AMY).build();
        XmlAdaptedPerson editedBensonPerson = new XmlAdaptedPerson(editedBenson);
        assertFalse(benson.equals(editedBensonPerson));
    }

    @Test
    public void equals_differentDepartment_false() {
        // different phone -> returns false
        Person editedBenson = new PersonBuilder(BENSON).withDepartment(VALID_DEPARTMENT_FOR_TEST).build();
        XmlAdaptedPerson editedBensonPerson = new XmlAdaptedPerson(editedBenson);
        assertFalse(benson.equals(editedBensonPerson));
    }

    @Test
    public void equals_differentPosition_false() {
        // different phone -> returns false
        Person editedBenson = new PersonBuilder(BENSON).withPosition(VALID_POSITION_AMY).build();
        XmlAdaptedPerson editedBensonPerson = new XmlAdaptedPerson(editedBenson);
        assertFalse(benson.equals(editedBensonPerson));
    }

    @Test
    public void equals_differentAddress_false() {
        // different phone -> returns false
        Person editedBenson = new PersonBuilder(BENSON).withAddress(VALID_ADDRESS_AMY).build();
        XmlAdaptedPerson editedBensonPerson = new XmlAdaptedPerson(editedBenson);
        assertFalse(benson.equals(editedBensonPerson));
    }

    @Test
    public void equals_differentSalary_false() {
        // different phone -> returns false
        Person editedBenson = new PersonBuilder(BENSON).withSalary(VALID_SALARY_AMY).build();
        XmlAdaptedPerson editedBensonPerson = new XmlAdaptedPerson(editedBenson);
        assertFalse(benson.equals(editedBensonPerson));
    }

}
