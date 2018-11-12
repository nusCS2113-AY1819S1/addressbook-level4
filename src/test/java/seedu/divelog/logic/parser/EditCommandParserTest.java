package seedu.divelog.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.divelog.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.divelog.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.divelog.testutil.TypicalIndexes.INDEX_FIRST_DIVE;

import org.junit.Test;

import seedu.divelog.commons.core.index.Index;
import seedu.divelog.logic.commands.EditCommand;
import seedu.divelog.logic.commands.EditCommand.EditDiveDescriptor;
import seedu.divelog.logic.parser.exceptions.ParseException;
import seedu.divelog.model.dive.DepthProfile;
import seedu.divelog.model.dive.Location;
import seedu.divelog.model.dive.OurDate;
import seedu.divelog.model.dive.Time;
import seedu.divelog.model.dive.TimeZone;

public class EditCommandParserTest {


    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {

    }

    @Test
    public void parse_allFieldsSpecified_success() {

    }

    @Test
    public void parse_someFieldsSpecified_success() {

    }

    @Test
    public void parse_oneFieldSpecified_success() throws ParseException {
        /*Check each field individually*/
        Index targetIndex = INDEX_FIRST_DIVE;
        /*Check date start*/
        String command = targetIndex.getOneBased() + " ds/20122018";
        EditCommand editCommand = parser.parse(command);
        EditDiveDescriptor expectedDesc = new EditDiveDescriptor();
        expectedDesc.setDateStart(new OurDate("20122018"));
        EditCommand expected = new EditCommand(targetIndex, expectedDesc);
        assertEquals(editCommand, expected);
        /*Check date ended*/
        command = targetIndex.getOneBased() + " de/20122018";
        editCommand = parser.parse(command);
        expectedDesc = new EditDiveDescriptor();
        expectedDesc.setDateEnd(new OurDate("20122018"));
        expected = new EditCommand(targetIndex, expectedDesc);
        assertEquals(editCommand, expected);
        /*Check the time started*/
        command = targetIndex.getOneBased() + " ts/2000";
        editCommand = parser.parse(command);
        expectedDesc = new EditDiveDescriptor();
        expectedDesc.setStart(new Time("2000"));
        expected = new EditCommand(targetIndex, expectedDesc);
        assertEquals(editCommand, expected);
        /*Check the time ended*/
        command = targetIndex.getOneBased() + " te/2020";
        editCommand = parser.parse(command);
        expectedDesc = new EditDiveDescriptor();
        expectedDesc.setEnd(new Time("2020"));
        expected = new EditCommand(targetIndex, expectedDesc);
        assertEquals(editCommand, expected);
        /*Check the location*/
        command = targetIndex.getOneBased() + " l/Great Barrier Reef";
        editCommand = parser.parse(command);
        expectedDesc = new EditDiveDescriptor();
        expectedDesc.setLocation(new Location("Great Barrier Reef"));
        expected = new EditCommand(targetIndex, expectedDesc);
        assertEquals(editCommand, expected);
        /*Check depth*/
        command = targetIndex.getOneBased() + " d/9.0";
        editCommand = parser.parse(command);
        expectedDesc = new EditDiveDescriptor();
        expectedDesc.setDepthProfile(new DepthProfile(9.0f));
        expected = new EditCommand(targetIndex, expectedDesc);
        assertEquals(editCommand, expected);
        /*Check timezone*/
        command = targetIndex.getOneBased() + " tz/+9";
        editCommand = parser.parse(command);
        expectedDesc = new EditDiveDescriptor();
        expectedDesc.setTimeZone(new TimeZone("+9"));
        expected = new EditCommand(targetIndex, expectedDesc);
        assertEquals(editCommand, expected);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {

    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {

    }
}
