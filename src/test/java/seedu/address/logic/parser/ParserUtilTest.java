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

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupLocation;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.script.CommandType;
import seedu.address.model.script.TextFile;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String INVALID_GROUP_NAME = "TUT/1";
    private static final String INVALID_GROUP_LOCATION = "E1/01/01";

    private static final String INVALID_INDEX = "e";

    private static final String VALID_INDEX_1 = "1";
    private static final String VALID_INDEX_2 = "2";
    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String VALID_GROUP_NAME = "TUT[1]";
    private static final String VALID_GROUP_LOCATION = "E1-01-01";

    private static final String VALID_TEXT_FILE = "StudentList";
    private static final String INVALID_TEXT_FILE = "\\StudentList";

    private static final String VALID_COMMAND_TYPE = "add";
    private static final String INVALID_COMMAND_TYPE = "abc";

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
    public void parseIndices_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseIndices(null);
    }

    @Test
    public void parseIndices_collectionWithInvalidIndices_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndices(Arrays.asList(VALID_INDEX_1, INVALID_INDEX));
    }

    @Test
    public void parseIndices_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseIndices(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseIndices_collectionWithValidIndices_returnsIndexSet() throws Exception {
        Set<Index> actualIndexSet = ParserUtil.parseIndices(Arrays.asList(VALID_INDEX_1, VALID_INDEX_2));

        Set<Index> expectedIndexSet = new HashSet<>();
        expectedIndexSet.add(Index.fromOneBased(Integer.valueOf(VALID_INDEX_1)));
        expectedIndexSet.add(Index.fromOneBased(Integer.valueOf(VALID_INDEX_2)));

        assertEquals(expectedIndexSet, actualIndexSet);
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseGroupName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseGroupName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseGroupName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseGroupName(INVALID_GROUP_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseGroupName_validValueWithoutWhitespace_returnsName() throws Exception {
        GroupName expectedGroupName = new GroupName(VALID_GROUP_NAME);
        assertEquals(expectedGroupName, ParserUtil.parseGroupName(VALID_GROUP_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseGroupName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String groupNameWithWhitespace = WHITESPACE + VALID_GROUP_NAME + WHITESPACE;
        GroupName expectedGroupName = new GroupName(VALID_GROUP_NAME);
        assertEquals(expectedGroupName, ParserUtil.parseGroupName(groupNameWithWhitespace));
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
    public void parseGroupLocation_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseGroupLocation((String) null));
    }

    @Test
    public void parseGroupLocation_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseGroupLocation(INVALID_GROUP_LOCATION));
    }

    @Test
    public void parseGroupLocation_validValueWithoutWhitespace_returnsGroupLocation() throws Exception {
        GroupLocation expectedGroupLocation = new GroupLocation(VALID_GROUP_LOCATION);
        assertEquals(expectedGroupLocation, ParserUtil.parseGroupLocation(VALID_GROUP_LOCATION));
    }

    @Test
    public void parseGroupLocation_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String groupLocationWithWhitespace = WHITESPACE + VALID_GROUP_LOCATION + WHITESPACE;
        GroupLocation expectedGroupLocation = new GroupLocation(VALID_GROUP_LOCATION);
        assertEquals(expectedGroupLocation, ParserUtil.parseGroupLocation(groupLocationWithWhitespace));
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
    public void parseTextFile_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTextFile((String) null));
    }

    @Test
    public void parseTextFile_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTextFile(INVALID_TEXT_FILE);
    }

    @Test
    public void parseTextFile_validValue_returnTextFile() throws Exception {
        TextFile expectedTextFile = new TextFile(VALID_TEXT_FILE);
        assertEquals(expectedTextFile, ParserUtil.parseTextFile(VALID_TEXT_FILE));
    }

    @Test
    public void parseTextFile_validValueWithWhiteSpace_returnTextFile() throws Exception {
        String textFileWithSpace = WHITESPACE + VALID_TEXT_FILE + WHITESPACE;
        TextFile expectedTextFile = new TextFile(VALID_TEXT_FILE);
        assertEquals(expectedTextFile, ParserUtil.parseTextFile(textFileWithSpace));
    }

    @Test
    public void parseCommandType_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseCommandType((String) null));
    }

    @Test
    public void parseCommandType_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseCommandType(INVALID_COMMAND_TYPE);
    }

    @Test
    public void parseCommandType_validValue_returnCommandType() throws Exception {
        CommandType expectedCommandType = new CommandType(VALID_COMMAND_TYPE);
        assertEquals(expectedCommandType, ParserUtil.parseCommandType(VALID_COMMAND_TYPE));
    }

    @Test
    public void parseCommandType_validValueWithWhiteSpace_returnCommandType() throws Exception {
        String commandTypeWithSpace = WHITESPACE + VALID_COMMAND_TYPE + WHITESPACE;
        CommandType expectedTextFile = new CommandType(VALID_COMMAND_TYPE);
        assertEquals(expectedTextFile, ParserUtil.parseCommandType(commandTypeWithSpace));
    }

}
