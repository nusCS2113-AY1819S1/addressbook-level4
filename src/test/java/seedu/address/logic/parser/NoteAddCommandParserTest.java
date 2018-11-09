package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_TITLE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.NoteAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.note.NoteDate;

/**
 * Contains tests for NoteAddCommandParser.
 */
public class NoteAddCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private NoteAddCommandParser parser = new NoteAddCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() throws ParseException {
        String expectedMessage = String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, NoteAddCommand.MESSAGE_USAGE);

        // invalid arguments
        String args = " this is an invalid input";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(args);
    }

    @Test
    public void parse_invalidModuleCode_throwsParseException() throws ParseException {
        String expectedMessage = ModuleCode.MESSAGE_MODULE_CODE_CONSTRAINT;

        // invalid module code args
        String args = " " + PREFIX_MODULE_CODE + "cs2113";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(args);
    }

    @Test
    public void parse_missingStartDateField_throwsParseException() throws ParseException {
        String expectedMessage = NoteDate.MESSAGE_START_DATE_MISSING_FIELD;

        // enter start time without start date
        String args = " "
                + PREFIX_MODULE_CODE + "CS2113 "
                + PREFIX_NOTE_START_TIME + "7:00 AM";
        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(args);

        // enter end date without start date
        args = " "
                + PREFIX_MODULE_CODE + "CS2113 "
                + PREFIX_NOTE_END_DATE + "12-12-2012";
        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(args);
    }

    @Test
    public void parse_invalidDateTimeFormat_throwsParseException() throws ParseException {
        String expectedMessageInvalidDate = Messages.MESSAGE_INVALID_DATE_FORMAT;
        String expectedMessageInvalidTime = Messages.MESSAGE_INVALID_TIME_FORMAT;
        String expectedMessageBlankField = Messages.MESSAGE_BLANK_FIELD;

        String args;

        try {
            // args contain prefix with blank value
            args = " "
                    + PREFIX_MODULE_CODE + "CS2113 "
                    + PREFIX_NOTE_START_DATE;
            parser.parse(args);
        } catch (ParseException e) {
            assertEquals(expectedMessageBlankField, e.getMessage());
        }

        try {
            // unrecognized date format for start date
            args = " "
                    + PREFIX_MODULE_CODE + "CS2113 "
                    + PREFIX_NOTE_START_DATE + "21 September 2013";
            parser.parse(args);
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains(expectedMessageInvalidDate));
        }

        try {
            // unrecognized time format for start time
            args = " "
                    + PREFIX_MODULE_CODE + "CS2113 "
                    + PREFIX_NOTE_START_DATE + "21/1/2013 "
                    + PREFIX_NOTE_START_TIME + "23:59";
            parser.parse(args);
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains(expectedMessageInvalidTime));
        }

        try {
            // unrecognized date format for end date
            args = " "
                    + PREFIX_MODULE_CODE + "CS2113 "
                    + PREFIX_NOTE_START_DATE + "1/1/2018 "
                    + PREFIX_NOTE_END_DATE + "2018-10-12";
            parser.parse(args);
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains(expectedMessageInvalidDate));
        }

        try {
            // unrecognized time format for end time, no space separation between the time and period(AM/PM)
            args = " "
                    + PREFIX_MODULE_CODE + "CS2113 "
                    + PREFIX_NOTE_START_DATE + "1.1.2013 "
                    + PREFIX_NOTE_START_TIME + "11:00 AM "
                    + PREFIX_NOTE_END_TIME + "1:00PM";
            parser.parse(args);
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains(expectedMessageInvalidTime));
        }

    }

    @Test
    public void parse_invalidDateTimeDifference_throwsParseException() throws ParseException {
        String expectedMessage = NoteAddCommandParser.MESSAGE_INVALID_DATE_TIME_DIFFERENCE;

        String args;
        try {
            // end date is earlier than start date
            args = " "
                    + PREFIX_MODULE_CODE + "CS2113 "
                    + PREFIX_NOTE_START_DATE + "20-12-2012 "
                    + PREFIX_NOTE_END_DATE + "19-12-2012";
            parser.parse(args);
        } catch (ParseException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

        try {
            // omitting end date sets its date similar to start date, hence, end date is earlier than start date
            args = " "
                    + PREFIX_MODULE_CODE + "CS2113 "
                    + PREFIX_NOTE_START_DATE + "20-12-2012 "
                    + PREFIX_NOTE_START_TIME + "9:01 PM "
                    + PREFIX_NOTE_END_TIME + "9:00 PM";
            parser.parse(args);
        } catch (ParseException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void parse_validArgs_success() throws ParseException {
        // valid args with only the mandatory field
        String args = " " + PREFIX_MODULE_CODE + "CS2113";
        NoteAddCommand noteAddCommand = parser.parse(args);

        assertNotNull(noteAddCommand);

        // valid args with completely valid input fields provided
        args = " "
                + PREFIX_MODULE_CODE + "CS2113 "
                + PREFIX_NOTE_TITLE + "A title "
                + PREFIX_NOTE_START_DATE + "10-10-2010 "
                + PREFIX_NOTE_START_TIME + "10:00 AM "
                + PREFIX_NOTE_END_DATE + "14-10-2010 "
                + PREFIX_NOTE_END_TIME + "06:00 PM "
                + PREFIX_NOTE_LOCATION + "This is a place";
        noteAddCommand = null;
        noteAddCommand = parser.parse(args);

        assertNotNull(noteAddCommand);
    }
}
