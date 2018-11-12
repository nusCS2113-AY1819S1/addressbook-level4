package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expenses.ExpensesAmount;
import seedu.address.model.expenses.MedicalExpenses;
import seedu.address.model.expenses.MiscellaneousExpenses;
import seedu.address.model.expenses.TravelExpenses;
import seedu.address.model.person.Address;
import seedu.address.model.person.Bonus;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Salary;
import seedu.address.model.person.tag.Tag;
import seedu.address.model.recruitment.JobDescription;
import seedu.address.model.recruitment.Post;
import seedu.address.model.recruitment.WorkExp;
import seedu.address.model.schedule.Date;
import seedu.address.model.schedule.Type;
import seedu.address.model.schedule.Year;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_EMPLOYEEID = "00000a";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_POST = "123456";
    private static final String INVALID_JOB_DESCRIPTION = "123456";
    private static final String INVALID_WORK_EXP = "5a";
    private static final String INVALID_DATE = "01/0A/1995";
    private static final String INVALID_YEAR = "199A";
    private static final String INVALID_STATUS = "asd";
    private static final String INVALID_DATEOFBIRTH = "01/0A/1995";
    private static final String INVALID_DEPARTMENT = "1234";
    private static final String INVALID_POSITION = "1234";
    private static final String INVALID_SALARY = "asd";
    private static final String INVALID_BONUS = "asd";
    private static final String INVALID_EXPENSES_AMOUNT = "asd";
    private static final String INVALID_TRAVEL_EXPENSES = "asd";
    private static final String INVALID_MEDICAL_EXPENSES = "asd";
    private static final String INVALID_MISC_EXPENSES = "asd";

    private static final String VALID_EMPLOYEEID = "000000";
    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_POST = "IT Manager";
    private static final String VALID_JOB_DESCRIPTION = "Maintain servers";
    private static final String VALID_WORK_EXP = "5";
    private static final String VALID_DATE = "01/01/2019";
    private static final String VALID_YEAR = "2018";
    private static final String VALID_STATUS = "LEAVE";
    private static final String VALID_DATEOFBIRTH = "01/01/1995";
    private static final String VALID_DEPARTMENT = "Human Resource";
    private static final String VALID_POSITION = "Intern";
    private static final String VALID_SALARY = "1234";
    private static final String VALID_BONUS = "2468";
    private static final String VALID_EXPENSES_AMOUNT = "1234";
    private static final String VALID_TRAVEL_EXPENSES = "123";
    private static final String VALID_MEDICAL_EXPENSES = "123";
    private static final String VALID_MISC_EXPENSES = "123";

    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        ParserUtil parserUtil = new ParserUtil();
        thrown.expect(ParseException.class);
        thrown.expectMessage(parserUtil.MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseEmployeeId_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEmployeeId((String) null));
    }

    @Test
    public void parseEmployeeId_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseEmployeeId(INVALID_EMPLOYEEID));
    }

    @Test
    public void parseEmployeeId_validValueWithoutWhitespace_returnsEmployeeId() throws Exception {
        EmployeeId expectedEmployeeId = new EmployeeId(VALID_EMPLOYEEID);
        assertEquals(expectedEmployeeId, ParserUtil.parseEmployeeId(VALID_EMPLOYEEID));
    }

    @Test
    public void parseEmployeeId_validValueWithWhitespace_returnsTrimmedEmployeeId() throws Exception {
        String employeeIdWithWhitespace = WHITESPACE + VALID_EMPLOYEEID + WHITESPACE;
        EmployeeId expectedEmployeeId = new EmployeeId(VALID_EMPLOYEEID);
        assertEquals(expectedEmployeeId, ParserUtil.parseEmployeeId(employeeIdWithWhitespace));
    }

    @Test
    public void parsePost_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePost((String) null));
    }

    @Test
    public void parsePost_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePost(INVALID_POST));
    }

    @Test
    public void parsePost_validValueWithoutWhitespace_returnsPost() throws Exception {
        Post expectedPost = new Post(VALID_POST);
        assertEquals(expectedPost, ParserUtil.parsePost(VALID_POST));
    }

    @Test
    public void parsePost_validValueWithWhitespace_returnsTrimmedPost() throws Exception {
        String postWithWhitespace = WHITESPACE + VALID_POST + WHITESPACE;
        Post expectedPost = new Post(VALID_POST);
        assertEquals(expectedPost, ParserUtil.parsePost(postWithWhitespace));
    }

    @Test
    public void parseJobDescription_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseJobDescription((String) null));
    }

    @Test
    public void parseJobDescription_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseJobDescription(INVALID_JOB_DESCRIPTION));
    }

    @Test
    public void parseJobDescription_validValueWithoutWhitespace_returnsJobDescription() throws Exception {
        JobDescription expectedJobDescription = new JobDescription(VALID_JOB_DESCRIPTION);
        assertEquals(expectedJobDescription, ParserUtil.parseJobDescription(VALID_JOB_DESCRIPTION));
    }

    @Test
    public void parseJobDescription_validValueWithWhitespace_returnsTrimmedJobDescription() throws Exception {
        String jobDescriptionWithWhitespace = WHITESPACE + VALID_JOB_DESCRIPTION + WHITESPACE;
        JobDescription expectedJobDescription = new JobDescription(VALID_JOB_DESCRIPTION);
        assertEquals(expectedJobDescription, ParserUtil.parseJobDescription(jobDescriptionWithWhitespace));
    }

    @Test
    public void parseWorkExp_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseWorkExp((String) null));
    }

    @Test
    public void parseWorkExp_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseWorkExp(INVALID_WORK_EXP));
    }

    @Test
    public void parseWorkExp_validValueWithoutWhitespace_returnsWorkExp() throws Exception {
        WorkExp expectedWorkExp = new WorkExp(VALID_WORK_EXP);
        assertEquals(expectedWorkExp, ParserUtil.parseWorkExp(VALID_WORK_EXP));
    }

    @Test
    public void parseWorkExp_validValueWithWhitespace_returnsTrimmedWorkExp() throws Exception {
        String workExpWithWhitespace = WHITESPACE + VALID_WORK_EXP + WHITESPACE;
        WorkExp expectedWorkExp = new WorkExp(VALID_WORK_EXP);
        assertEquals(expectedWorkExp, ParserUtil.parseWorkExp(workExpWithWhitespace));
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDate((String) null));
    }

    @Test
    public void parseDate_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDate(INVALID_DATE));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(VALID_DATE));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(dateWithWhitespace));
    }

    @Test
    public void parseYear_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseYear((String) null));
    }

    @Test
    public void parseYear_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseYear(INVALID_YEAR));
    }

    @Test
    public void parseYear_validValueWithoutWhitespace_returnsYear() throws Exception {
        Year expectedYear = new Year(VALID_YEAR);
        assertEquals(expectedYear, ParserUtil.parseYear(VALID_YEAR));
    }

    @Test
    public void parseYear_validValueWithWhitespace_returnsTrimmedYear() throws Exception {
        String yearWithWhitespace = WHITESPACE + VALID_YEAR + WHITESPACE;
        Year expectedYear = new Year(VALID_YEAR);
        assertEquals(expectedYear, ParserUtil.parseYear(yearWithWhitespace));
    }

    @Test
    public void parseDates_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseDates(null);
    }

    @Test
    public void parseDates_collectionWithInvalidDates_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseDates(Arrays.asList(INVALID_DATE));
    }

    @Test
    public void parseDates_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseDates(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseDates_collectionWithValidDates_returnsDateSet() throws Exception {
        Set<Date> actualDateSet = ParserUtil.parseDates(Arrays.asList(VALID_DATE));
        Set<Date> expectedDateSet = new HashSet<>(Arrays.asList(new Date(VALID_DATE)));

        assertEquals(expectedDateSet, actualDateSet);
    }

    @Test
    public void parseStatus_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseStatus((String) null));
    }

    @Test
    public void parseStatus_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseStatus(INVALID_STATUS));
    }

    @Test
    public void parseStatus_validValueWithoutWhitespace_returnsStatus() throws Exception {
        Type expectedStatus = new Type(VALID_STATUS);
        assertEquals(expectedStatus, ParserUtil.parseStatus(VALID_STATUS));
    }

    @Test
    public void parseStatus_validValueWithWhitespace_returnsTrimmedStatus() throws Exception {
        String statusWithWhitespace = WHITESPACE + VALID_STATUS + WHITESPACE;
        Type expectedStatus = new Type(VALID_STATUS);
        assertEquals(expectedStatus, ParserUtil.parseStatus(statusWithWhitespace));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseDateOfBirth_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDateOfBirth((String) null));
    }

    @Test
    public void parseDateOfBirth_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDateOfBirth(INVALID_DATEOFBIRTH));
    }

    @Test
    public void parseDateOfBirth_validValueWithoutWhitespace_returnsDateOfBirth() throws Exception {
        DateOfBirth expectedDateOfBirth = new DateOfBirth(VALID_DATEOFBIRTH);
        assertEquals(expectedDateOfBirth, ParserUtil.parseDateOfBirth(VALID_DATEOFBIRTH));
    }

    @Test
    public void parseDateOfBirth_validValueWithWhitespace_returnsTrimmedDateOfBirth() throws Exception {
        String dateOfBirthWithWhitespace = WHITESPACE + VALID_DATEOFBIRTH + WHITESPACE;
        DateOfBirth expectedDateOfBirth = new DateOfBirth(VALID_DATEOFBIRTH);
        assertEquals(expectedDateOfBirth, ParserUtil.parseDateOfBirth(dateOfBirthWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseDepartment_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDepartment((String) null));
    }

    @Test
    public void parseDepartment_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDepartment(INVALID_DEPARTMENT));
    }

    @Test
    public void parseDepartment_validValueWithoutWhitespace_returnsDepartment() throws Exception {
        Department expectedDepartment = new Department(VALID_DEPARTMENT);
        assertEquals(expectedDepartment, ParserUtil.parseDepartment(VALID_DEPARTMENT));
    }

    @Test
    public void parseDepartment_validValueWithWhitespace_returnsTrimmedDepartment() throws Exception {
        String departmentWithWhitespace = WHITESPACE + VALID_DEPARTMENT + WHITESPACE;
        Department expectedDepartment = new Department(VALID_DEPARTMENT);
        assertEquals(expectedDepartment, ParserUtil.parseDepartment(departmentWithWhitespace));
    }

    @Test
    public void parsePosition_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePosition((String) null));
    }

    @Test
    public void parsePosition_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePosition(INVALID_POSITION));
    }

    @Test
    public void parsePosition_validValueWithoutWhitespace_returnsPosition() throws Exception {
        Position expectedPosition = new Position(VALID_POSITION);
        assertEquals(expectedPosition, ParserUtil.parsePosition(VALID_POSITION));
    }

    @Test
    public void parsePosition_validValueWithWhitespace_returnsTrimmedPosition() throws Exception {
        String positionWithWhitespace = WHITESPACE + VALID_POSITION + WHITESPACE;
        Position expectedPosition = new Position(VALID_POSITION);
        assertEquals(expectedPosition, ParserUtil.parsePosition(positionWithWhitespace));
    }

    @Test
    public void parseSalary_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseSalary((String) null));
    }

    @Test
    public void parseSalary_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseSalary(INVALID_SALARY));
    }

    @Test
    public void parseSalary_validValueWithoutWhitespace_returnsSalary() throws Exception {
        Salary expectedSalary = new Salary(VALID_SALARY);
        assertEquals(expectedSalary, ParserUtil.parseSalary(VALID_SALARY));
    }

    @Test
    public void parseSalary_validValueWithWhitespace_returnsTrimmedSalary() throws Exception {
        String salaryWithWhitespace = WHITESPACE + VALID_SALARY + WHITESPACE;
        Salary expectedSalary = new Salary(VALID_SALARY);
        assertEquals(expectedSalary, ParserUtil.parseSalary(salaryWithWhitespace));
    }

    @Test
    public void parseBonus_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseBonus((String) null));
    }

    @Test
    public void parseBonus_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseBonus(INVALID_BONUS));
    }

    @Test
    public void parseBonus_validValueWithoutWhitespace_returnsBonus() throws Exception {
        Bonus expectedBonus = new Bonus(VALID_BONUS);
        assertEquals(expectedBonus, ParserUtil.parseBonus(VALID_BONUS));
    }

    @Test
    public void parseBonus_validValueWithWhitespace_returnsTrimmedBonus() throws Exception {
        String bonusWithWhitespace = WHITESPACE + VALID_BONUS + WHITESPACE;
        Bonus expectedBonus = new Bonus(VALID_BONUS);
        assertEquals(expectedBonus, ParserUtil.parseBonus(bonusWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTag(null);
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTag(INVALID_TAG);
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseExpensesAmount_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseExpensesAmount((String) null));
    }

    @Test
    public void parseExpensesAmount_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseExpensesAmount(INVALID_EXPENSES_AMOUNT));
    }

    @Test
    public void parseExpensesAmount_validValueWithoutWhitespace_returnsExpensesAmount() throws Exception {
        ExpensesAmount expectedExpensesAmount = new ExpensesAmount(VALID_EXPENSES_AMOUNT);
        assertEquals(expectedExpensesAmount, ParserUtil.parseExpensesAmount(VALID_EXPENSES_AMOUNT));
    }

    @Test
    public void parseExpensesAmount_validValueWithWhitespace_returnsTrimmedExpensesAmount() throws Exception {
        String expensesAmountWithWhitespace = WHITESPACE + VALID_EXPENSES_AMOUNT + WHITESPACE;
        ExpensesAmount expectedExpensesAmount = new ExpensesAmount(VALID_EXPENSES_AMOUNT);
        assertEquals(expectedExpensesAmount, ParserUtil.parseExpensesAmount(expensesAmountWithWhitespace));
    }

    @Test
    public void parseTravelExpenses_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTravelExpenses((String) null));
    }

    @Test
    public void parseTravelExpenses_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseTravelExpenses(INVALID_TRAVEL_EXPENSES));
    }

    @Test
    public void parseTravelExpenses_validValueWithoutWhitespace_returnsTravelExpenses() throws Exception {
        TravelExpenses expectedTravelExpenses = new TravelExpenses(VALID_TRAVEL_EXPENSES);
        assertEquals(expectedTravelExpenses, ParserUtil.parseTravelExpenses(VALID_TRAVEL_EXPENSES));
    }

    @Test
    public void parseTravelExpenses_validValueWithWhitespace_returnsTrimmedTravelExpenses() throws Exception {
        String travelExpensesWithWhitespace = WHITESPACE + VALID_TRAVEL_EXPENSES + WHITESPACE;
        TravelExpenses expectedTravelExpenses = new TravelExpenses(VALID_TRAVEL_EXPENSES);
        assertEquals(expectedTravelExpenses, ParserUtil.parseTravelExpenses(travelExpensesWithWhitespace));
    }

    @Test
    public void parseMedicalExpenses_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseMedicalExpenses((String) null));
    }

    @Test
    public void parseMedicalExpenses_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseMedicalExpenses(INVALID_MEDICAL_EXPENSES));
    }

    @Test
    public void parseMedicalExpenses_validValueWithoutWhitespace_returnsMedicalExpenses() throws Exception {
        MedicalExpenses expectedMedicalExpenses = new MedicalExpenses(VALID_MEDICAL_EXPENSES);
        assertEquals(expectedMedicalExpenses, ParserUtil.parseMedicalExpenses(VALID_MEDICAL_EXPENSES));
    }

    @Test
    public void parseMedicalExpenses_validValueWithWhitespace_returnsTrimmedMedicalExpenses() throws Exception {
        String medicalExpensesWithWhitespace = WHITESPACE + VALID_MEDICAL_EXPENSES + WHITESPACE;
        MedicalExpenses expectedMedicalExpenses = new MedicalExpenses(VALID_MEDICAL_EXPENSES);
        assertEquals(expectedMedicalExpenses, ParserUtil.parseMedicalExpenses(medicalExpensesWithWhitespace));
    }

    @Test
    public void parseMiscellaneousExpenses_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseMiscellaneousExpenses((String) null));
    }

    @Test
    public void parseMiscellaneousExpenses_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseMiscellaneousExpenses(INVALID_MISC_EXPENSES));
    }

    @Test
    public void parseMiscellaneousExpenses_validValueWithoutWhitespace_returnsMiscellaneousExpenses() throws Exception {
        MiscellaneousExpenses expectedMiscellaneousExpenses = new MiscellaneousExpenses(VALID_MISC_EXPENSES);
        assertEquals(expectedMiscellaneousExpenses, ParserUtil.parseMiscellaneousExpenses(VALID_MISC_EXPENSES));
    }

    @Test
    public void parseMiscellaneousExpenses_validValueWithWhitespace_returnsTrimmedBonus() throws Exception {
        String miscExpensesWithWhitespace = WHITESPACE + VALID_MISC_EXPENSES + WHITESPACE;
        MiscellaneousExpenses expectedMiscellaneousExpenses = new MiscellaneousExpenses(VALID_MISC_EXPENSES);
        assertEquals(expectedMiscellaneousExpenses, ParserUtil.parseMiscellaneousExpenses(miscExpensesWithWhitespace));
    }
}
