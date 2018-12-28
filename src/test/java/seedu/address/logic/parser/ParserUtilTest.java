package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medhistory.Allergy;
import seedu.address.model.medhistory.MedHistDate;
import seedu.address.model.medhistory.PrevCountry;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.addinfo.BloodType;
import seedu.address.model.person.addinfo.DateOfBirth;
import seedu.address.model.person.addinfo.Gender;
import seedu.address.model.person.addinfo.Height;
import seedu.address.model.person.addinfo.MaritalStatus;
import seedu.address.model.person.addinfo.Nric;
import seedu.address.model.person.addinfo.Occupation;
import seedu.address.model.person.addinfo.Weight;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_NRIC = "S3719B";
    private static final String INVALID_DATE_OF_BIRTH = "01/01/2010";
    private static final String INVALID_HEIGHT_WEIGHT = "4A";
    private static final String INVALID_GENDER = "A";
    private static final String INVALID_BLOODTYPE = "MP";
    private static final String INVALID_OCCUPATION = "D0Ct0R";
    private static final String INVALID_MARITAL_STATUS = "MARRIED";
    private static final String INVALID_ALLERGY = "$r1ce";
    private static final String INVALID_MEDHISTDATE = "10/10/2010";
    private static final String INVALID_PREVCOUNTRY = "Cheena!";

    private static final String INCORRECT_DATE_OF_BIRTH = "01-20-1920";
    private static final String INCORRECT_BLOODTYPE = "BO+";
    private static final String INCORRECT_NRIC = "S1234567A";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_NRIC = "S3719668A";
    private static final String VALID_DATE_OF_BIRTH = "01-01-2010";
    private static final String VALID_HEIGHT_WEIGHT = "80";
    private static final String VALID_GENDER = "F";
    private static final String VALID_BLOODTYPE = "O+";
    private static final String VALID_OCCUPATION = "DOCTOR";
    private static final String VALID_MARITAL_STATUS = "M";
    private static final String VALID_ALLERGY = "rice";
    private static final String VALID_MEDHISTDATE = "10-10-2010";
    private static final String VALID_PREVCOUNTRY = "China";

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
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
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

    //@@author xhxh96
    @Test
    public void parseNric_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseNric((String) null));
    }

    @Test
    public void parseNric_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseNric(INVALID_NRIC));
    }

    @Test
    public void parseNric_incorrectValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseNric(INCORRECT_NRIC));
    }

    @Test
    public void parseNric_validValueWithoutWhitespace_returnsNric() throws Exception {
        Nric expectedNric = new Nric(VALID_NRIC);
        assertEquals(expectedNric, ParserUtil.parseNric(VALID_NRIC));
    }

    @Test
    public void parseNric_validValueWithWhitespace_returnsTrimmedNric() throws Exception {
        String nricWithWhitespace = WHITESPACE + VALID_NRIC + WHITESPACE;
        Nric expectedNric = new Nric(VALID_NRIC);
        assertEquals(expectedNric, ParserUtil.parseNric(nricWithWhitespace));
    }

    @Test
    public void parseDateOfBirth_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDateOfBirth(INVALID_DATE_OF_BIRTH));
    }

    @Test
    public void parseDateOfBirth_incorrectValue_throwsDateTimeParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDateOfBirth(INCORRECT_DATE_OF_BIRTH));
    }

    @Test
    public void parseDateOfBirth_validValueWithoutWhitespace_returnsDateOfBirth() throws Exception {
        DateOfBirth expectedDateOfBirth = new DateOfBirth(VALID_DATE_OF_BIRTH);
        assertEquals(expectedDateOfBirth, ParserUtil.parseDateOfBirth(VALID_DATE_OF_BIRTH));
    }

    @Test
    public void parseHeight_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseHeight(INVALID_HEIGHT_WEIGHT));
    }

    @Test
    public void parseHeight_validValueWithoutWhitespace_returnsHeight() throws Exception {
        Height expectedHeight = new Height(VALID_HEIGHT_WEIGHT);
        assertEquals(expectedHeight, ParserUtil.parseHeight(VALID_HEIGHT_WEIGHT));
    }

    @Test
    public void parseHeight_validValueWithWhitespace_returnsTrimmedHeight() throws Exception {
        String heightWithWhitespace = WHITESPACE + VALID_HEIGHT_WEIGHT;
        Height expectedHeight = new Height(VALID_HEIGHT_WEIGHT);
        assertEquals(expectedHeight, ParserUtil.parseHeight(heightWithWhitespace));
    }

    @Test
    public void parseWeight_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseWeight(INVALID_HEIGHT_WEIGHT));
    }

    @Test
    public void parseWeight_validValueWithoutWhitespace_returnsHeight() throws Exception {
        Weight expectedWeight = new Weight(VALID_HEIGHT_WEIGHT);
        assertEquals(expectedWeight, ParserUtil.parseWeight(VALID_HEIGHT_WEIGHT));
    }

    @Test
    public void parseWeight_validValueWithWhitespace_returnsTrimmedHeight() throws Exception {
        String weightWithWhitespace = WHITESPACE + VALID_HEIGHT_WEIGHT;
        Weight expectedWeight = new Weight(VALID_HEIGHT_WEIGHT);
        assertEquals(expectedWeight, ParserUtil.parseWeight(weightWithWhitespace));
    }

    @Test
    public void parseGender_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseGender(INVALID_GENDER));
    }

    @Test
    public void parseGender_validValue_returnsGender() throws Exception {
        Gender expectedGender = new Gender(VALID_GENDER);
        assertEquals(expectedGender, ParserUtil.parseGender(VALID_GENDER));
    }

    @Test
    public void parseBloodType_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseBloodType(INVALID_BLOODTYPE));
    }

    @Test
    public void parseBloodType_incorrectValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseBloodType(INCORRECT_BLOODTYPE));
    }

    @Test
    public void parseBloodType_validValue_returnBloodType() throws Exception {
        BloodType expectedBloodType = new BloodType(VALID_BLOODTYPE);
        assertEquals(expectedBloodType, ParserUtil.parseBloodType(VALID_BLOODTYPE));
    }

    @Test
    public void parseOccupation_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseOccupation(INVALID_OCCUPATION));
    }

    @Test
    public void parseOccupation_validValue_returnOccupation() throws Exception {
        Occupation expectedOccupation = new Occupation(VALID_OCCUPATION);
        assertEquals(expectedOccupation, ParserUtil.parseOccupation(VALID_OCCUPATION));
    }

    @Test
    public void parseMaritalStatus_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseMaritalStatus(INVALID_MARITAL_STATUS));
    }

    @Test
    public void parseMaritalStatus_validValueReturns_maritalStatus() throws Exception {
        MaritalStatus expectedMaritalStatus = new MaritalStatus(VALID_MARITAL_STATUS);
        assertEquals(expectedMaritalStatus, ParserUtil.parseMaritalStatus(VALID_MARITAL_STATUS));
    }

    //@@author
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

    //@@author xShaivan
    @Test
    public void parseAllergynullthrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAllergy((String) null));
    }

    @Test
    public void parseAllergyinvalidValuethrowsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseAllergy(INVALID_ALLERGY));
    }

    @Test
    public void parseAllergyvalidValueWithoutWhitespacereturnsAllergy() throws Exception {
        Allergy expectedAllergy = new Allergy(VALID_ALLERGY);
        assertEquals(expectedAllergy, ParserUtil.parseAllergy(VALID_ALLERGY));
    }

    @Test
    public void parseAllergyvalidValueWithWhitespacereturnsTrimmedAllergy() throws Exception {
        String allergyWithWhitespace = WHITESPACE + VALID_ALLERGY + WHITESPACE;
        Allergy expectedAllergy = new Allergy(VALID_ALLERGY);
        assertEquals(expectedAllergy, ParserUtil.parseAllergy(allergyWithWhitespace));
    }

    @Test
    public void parseMedHistDatenullthrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseMedHistDate((String) null));
    }

    @Test
    public void parseMedHistDateinvalidValuethrowsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseMedHistDate(INVALID_MEDHISTDATE));
    }

    @Test
    public void parseMedHistDatevalidValueWithoutWhitespacereturnsMedHistDate() throws Exception {
        MedHistDate expectedMedHistDate = new MedHistDate(VALID_MEDHISTDATE);
        assertEquals(expectedMedHistDate, ParserUtil.parseMedHistDate(VALID_MEDHISTDATE));
    }

    @Test
    public void parseMedHistDatevalidValueWithWhitespacereturnsTrimmedMedHistDate() throws Exception {
        String medHistDateWithWhitespace = WHITESPACE + VALID_MEDHISTDATE + WHITESPACE;
        MedHistDate expectedMedHistDate = new MedHistDate(VALID_MEDHISTDATE);
        assertEquals(expectedMedHistDate, ParserUtil.parseMedHistDate(medHistDateWithWhitespace));
    }

    @Test
    public void parsePrevCountrynullthrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePrevCountry((String) null));
    }

    @Test
    public void parsePrevCountryinvalidValuethrowsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePrevCountry(INVALID_PREVCOUNTRY));
    }

    @Test
    public void parsePrevCountryvalidValueWithoutWhitespacereturnsPrevCountry() throws Exception {
        PrevCountry expectedPrevCountry = new PrevCountry(VALID_PREVCOUNTRY);
        assertEquals(expectedPrevCountry, ParserUtil.parsePrevCountry(VALID_PREVCOUNTRY));
    }

    @Test
    public void parsePrevCountryvalidValueWithWhitespacereturnsTrimmedPrevCountry() throws Exception {
        String prevCountryWithWhitespace = WHITESPACE + VALID_PREVCOUNTRY + WHITESPACE;
        PrevCountry expectedPrevCountry = new PrevCountry(VALID_PREVCOUNTRY);
        assertEquals(expectedPrevCountry, ParserUtil.parsePrevCountry(prevCountryWithWhitespace));
    }
}
