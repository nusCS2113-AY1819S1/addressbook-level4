package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static UnRefactored.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.PriorityLevel;
import UnRefactored.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_DEADLINE_1 = "31/2";
    private static final String INVALID_DEADLINE_2 = "0/1";
    private static final String INVALID_PRIORITY = "+owmedium";

    private static final String VALID_DEADLINE = "1/1";
    private static final String VALID_TITLE = "Do homework";
    private static final String VALID_DESCRIPTION = "study in library";
    private static final String VALID_PRIORITY = "lowmedium";

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
        assertEquals(INDEX_FIRST_TASK, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_TASK, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseTitle_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTitle((String) null));
    }

    @Test
    public void parseTitle_validValueWithoutWhitespace_returnsName() throws Exception {
        String expectedName = VALID_TITLE;
        assertEquals(expectedName, ParserUtil.parseTitle(VALID_TITLE));
    }

    @Test
    public void parseTitle_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_TITLE + WHITESPACE;
        String expectedName = VALID_TITLE;
        assertEquals(expectedName, ParserUtil.parseTitle(nameWithWhitespace));
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription((String) null));
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsDescription() throws Exception {
        String expectedDescription = VALID_DESCRIPTION;
        assertEquals(expectedDescription, ParserUtil.parseDescription(VALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedDescription() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_DESCRIPTION + WHITESPACE;
        String expectedDescription = VALID_DESCRIPTION;
        assertEquals(expectedDescription, ParserUtil.parseDescription(phoneWithWhitespace));
    }

    @Test
    public void parsePriorityLevel_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePriorityLevel((String) null));
    }

    @Test
    public void parsePriorityLevel_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePriorityLevel(INVALID_PRIORITY));
    }

    @Test
    public void parsePriorityLevel_validValueWithoutWhitespace_returnsAddress() throws Exception {
        PriorityLevel expectedPriority = new PriorityLevel(VALID_PRIORITY);
        assertEquals(expectedPriority, ParserUtil.parsePriorityLevel(VALID_PRIORITY));
    }

    @Test
    public void parsePriorityLevel_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_PRIORITY + WHITESPACE;
        PriorityLevel expectedPriority = new PriorityLevel(VALID_PRIORITY);
        assertEquals(expectedPriority, ParserUtil.parsePriorityLevel(addressWithWhitespace));
    }

    /*@Test
    public void parseDeadline_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDeadline((String) null));
    }

    @Test
    public void parseDeadline_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDeadline(INVALID_DEADLINE_1));
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDeadline(INVALID_DEADLINE_2));
    }

    @Test
    public void parseDeadline_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Deadline expectedEmail = new Deadline(VALID_DEADLINE);
        assertEquals(expectedEmail, ParserUtil.parseDeadline(VALID_DEADLINE));
    }

    @Test
    public void parseDeadline_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_DEADLINE + WHITESPACE;
        Deadline expectedEmail = new Deadline(VALID_DEADLINE);
        assertEquals(expectedEmail, ParserUtil.parseDeadline(emailWithWhitespace));
    }*/
}
