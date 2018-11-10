package seedu.recruit.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.recruit.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.recruit.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX_ARGS;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_THIRD;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.candidate.Age;
import seedu.recruit.model.candidate.Education;
import seedu.recruit.model.candidate.Gender;
import seedu.recruit.model.candidate.Name;
import seedu.recruit.model.commons.Address;
import seedu.recruit.model.commons.Email;
import seedu.recruit.model.commons.Phone;
import seedu.recruit.model.company.CompanyName;
import seedu.recruit.model.joboffer.AgeRange;
import seedu.recruit.model.joboffer.Job;
import seedu.recruit.model.joboffer.Salary;
import seedu.recruit.model.tag.Tag;
import seedu.recruit.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_GENDER = "U";
    private static final String INVALID_AGE = "A";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_JOB = "hI!";
    private static final String INVALID_EDUCATION = "BACHELORS";
    private static final String INVALID_SALARY = "10000a";
    private static final String INVALID_AGE_RANGE = "16-17-18";
    private static final String INVALID_TAG = "#friend";


    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_GENDER = "M";
    private static final String VALID_AGE = "32";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_JOB = "Cashier";
    private static final String VALID_EDUCATION = "OLEVELS";
    private static final String VALID_SALARY = "10000";
    private static final String VALID_COMPANY_NAME = "Fish & Co.";
    private static final String VALID_AGE_RANGE = "20-32";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";


    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();



    @Test
    public void parseIndexString_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndexStringToInteger("-1");
    }

    @Test
    public void parseIndexString_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
    }
    @Test
    public void parseIndexRange_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX_ARGS);
        ParserUtil.parseIndexRange("1", "2", "3");
    }

    @Test
    public void parseIndexRange_validInput_success() throws Exception {
        Set<Index> expectedSet = new HashSet<>();
        expectedSet.add(INDEX_FIRST);
        expectedSet.add(INDEX_SECOND);
        expectedSet.add(INDEX_THIRD);
        assertEquals(expectedSet, ParserUtil.parseIndexRange("1", "3"));
    }

    @Test
    public void parseIndexSet_validInput_success() throws Exception {
        Set<Index> expectedSet = new HashSet<>();
        expectedSet.add(INDEX_FIRST);
        expectedSet.add(INDEX_SECOND);
        expectedSet.add(INDEX_THIRD);
        assertEquals(expectedSet, ParserUtil.parseIndexSet("1-2", "3"));
    }

    @Test
    public void parseIndexSet_invalidInput_throwsParseException() throws Exception {
        Set<Index> expectedSet = new HashSet<>();
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX_ARGS);
        assertEquals(expectedSet, ParserUtil.parseIndexSet("1-2-3", "5"));
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
    public void parseGender_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseGender((String) null));
    }

    @Test
    public void parseGender_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseGender(INVALID_GENDER));
    }

    @Test
    public void parseGender_validValueWithoutWhitespace_returnsGender() throws Exception {
        Gender expectedGender = new Gender(VALID_GENDER);
        assertEquals(expectedGender, ParserUtil.parseGender(VALID_GENDER));
    }

    @Test
    public void parseGender_validValueWithWhitespace_returnsTrimmedGender() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_GENDER + WHITESPACE;
        Gender expectedGender = new Gender(VALID_GENDER);
        assertEquals(expectedGender, ParserUtil.parseGender(nameWithWhitespace));
    }

    @Test
    public void parseAge_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAge((String) null));
    }

    @Test
    public void parseAge_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseAge(INVALID_AGE));
    }

    @Test
    public void parseAge_validValueWithoutWhitespace_returnsAge() throws Exception {
        Age expectedAge = new Age(VALID_AGE);
        assertEquals(expectedAge, ParserUtil.parseAge(VALID_AGE));
    }

    @Test
    public void parseAge_validValueWithWhitespace_returnsTrimmedAge() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_AGE + WHITESPACE;
        Age expectedAge = new Age(VALID_AGE);
        assertEquals(expectedAge, ParserUtil.parseAge(nameWithWhitespace));
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
    public void parseJob_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseJob((String) null));
    }

    @Test
    public void parseJob_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseJob(INVALID_JOB));
    }

    @Test
    public void parseJob_validValueWithoutWhitespace_returnsJob() throws Exception {
        Job expectedJob = new Job(VALID_JOB);
        assertEquals(expectedJob, ParserUtil.parseJob(VALID_JOB));
    }

    @Test
    public void parseJob_validValueWithWhitespace_returnsTrimmedJob() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_JOB + WHITESPACE;
        Job expectedJob = new Job(VALID_JOB);
        assertEquals(expectedJob, ParserUtil.parseJob(nameWithWhitespace));
    }

    @Test
    public void parseEducation_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEducation((String) null));
    }

    @Test
    public void parseEducation_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseEducation(INVALID_EDUCATION));
    }

    @Test
    public void parseEducation_validValueWithoutWhitespace_returnsEducation() throws Exception {
        Education expectedEducation = new Education(VALID_EDUCATION);
        assertEquals(expectedEducation, ParserUtil.parseEducation(VALID_EDUCATION));
    }

    @Test
    public void parseEducation_validValueWithWhitespace_returnsTrimmedEducation() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_EDUCATION + WHITESPACE;
        Education expectedEducation = new Education(VALID_EDUCATION);
        assertEquals(expectedEducation, ParserUtil.parseEducation(nameWithWhitespace));
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
        String nameWithWhitespace = WHITESPACE + VALID_SALARY + WHITESPACE;
        Salary expectedSalary = new Salary(VALID_SALARY);
        assertEquals(expectedSalary, ParserUtil.parseSalary(nameWithWhitespace));
    }

    @Test
    public void parseCompanyName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseCompanyName((String) null));
    }

    @Test
    public void parseCompanyName_validValueWithoutWhitespace_returnsCompanyName() throws Exception {
        CompanyName expectedCompanyName = new CompanyName(VALID_COMPANY_NAME);
        assertEquals(expectedCompanyName, ParserUtil.parseCompanyName(VALID_COMPANY_NAME));
    }

    @Test
    public void parseCompanyName_validValueWithWhitespace_returnsTrimmedCompanyName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_COMPANY_NAME + WHITESPACE;
        CompanyName expectedCompanyName = new CompanyName(VALID_COMPANY_NAME);
        assertEquals(expectedCompanyName, ParserUtil.parseCompanyName(nameWithWhitespace));
    }

    @Test
    public void parseAgeRange_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAgeRange((String) null));
    }

    @Test
    public void parseAgeRange_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseAgeRange(INVALID_AGE_RANGE));
    }

    @Test
    public void parseAgeRange_validValueWithoutWhitespace_returnsAgeRange() throws Exception {
        AgeRange expectedAgeRange = new AgeRange(VALID_AGE_RANGE);
        assertEquals(expectedAgeRange, ParserUtil.parseAgeRange(VALID_AGE_RANGE));
    }

    @Test
    public void parseAgeRange_validValueWithWhitespace_returnsTrimmedAgeRange() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_AGE_RANGE + WHITESPACE;
        AgeRange expectedAgeRange = new AgeRange(VALID_AGE_RANGE);
        assertEquals(expectedAgeRange, ParserUtil.parseAgeRange(nameWithWhitespace));
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
}
