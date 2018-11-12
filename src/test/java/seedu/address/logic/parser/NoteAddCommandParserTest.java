package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_TITLE;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.NoteAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleManager;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.note.NoteDate;
import seedu.address.model.note.NoteLocation;
import seedu.address.model.note.NoteTitle;
import seedu.address.testutil.ModuleBuilder;

/**
 * Contains tests for NoteAddCommandParser.
 */
public class NoteAddCommandParserTest {

    private static ModuleManager moduleManager = ModuleManager.getInstance();
    private static ModuleBuilder module = new ModuleBuilder();
    private static boolean moduleDoesExist;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private NoteAddCommandParser parser = new NoteAddCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() throws ParseException {
        String expectedMessage = String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, NoteAddCommand.MESSAGE_USAGE);
        String expectedMessageBlankField = Messages.MESSAGE_BLANK_FIELD;

        String args;

        try {
            // invalid arguments
            args = " this is an invalid input";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

        try {
            // args contain prefix with blank value
            args = " "
                    + PREFIX_NOTE_START_DATE;
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertEquals(expectedMessageBlankField, e.getMessage());
        }
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
    public void parse_invalidTitle_throwsParseException() throws ParseException {
        String expectedMessage = NoteTitle.MESSAGE_TITLE_EXCEED_MAX_CHAR_COUNT;

        String longTitle = "iOD24I1ZMQf7p43xiP79GIa3q6TX2v1"; // 31 characters > max character count for title (30)
        String args = " " + PREFIX_NOTE_TITLE + longTitle;

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(args);
    }

    @Test
    public void parse_invalidLocation_throwsParseException() throws ParseException {
        String expectedMessage = NoteLocation.MESSAGE_LOCATION_EXCEED_MAX_CHAR_COUNT;

        // 81 characters > max character count for location (80)
        String longLocation = "A52OWdCK3nm9eTN4K0gY8TjbJVKYt5VOPAFvD3nenwuShoBqxNNrIqolflLry0Y8VJb6uBUtIFLNXS8br";
        String args = " " + PREFIX_NOTE_LOCATION + longLocation;

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(args);
    }

    @Test
    public void parse_missingStartDateField_throwsParseException() throws ParseException {
        String expectedMessage = NoteDate.MESSAGE_START_DATE_MISSING_FIELD;

        String args;
        try {
            // enter start time without start date
            args = " " + PREFIX_NOTE_START_TIME + "7:00 AM";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains(expectedMessage));
        }

        try {
            // enter end date without start date
            args = " " + PREFIX_NOTE_END_DATE + "12-12-2012";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains(expectedMessage));
        }

        try {
            // enter end time without start date
            args = " " + PREFIX_NOTE_END_TIME + "7:00 PM";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains(expectedMessage));
        }
    }

    @Test
    public void parse_invalidDateTimeFormat_throwsParseException() throws ParseException {
        String expectedMessageInvalidDate = Messages.MESSAGE_INVALID_DATE_FORMAT;
        String expectedMessageInvalidTime = Messages.MESSAGE_INVALID_TIME_FORMAT;

        String args;

        try {
            // unrecognized date format for start date
            args = " " + PREFIX_NOTE_START_DATE + "21 September 2013";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains(expectedMessageInvalidDate));
        }

        try {
            // unrecognized time format for start time
            args = " "
                    + PREFIX_NOTE_START_DATE + "21/1/2013 "
                    + PREFIX_NOTE_START_TIME + "23:59";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains(expectedMessageInvalidTime));
        }

        try {
            // unrecognized date format for end date, the format is not one of the accepted date formats.
            args = " "
                    + PREFIX_NOTE_START_DATE + "1-1-2018 "
                    + PREFIX_NOTE_END_DATE + "2018-10-12";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains(expectedMessageInvalidDate));
        }

        try {
            // unrecognized time format for end time, no space separation between the time and period(AM/PM)
            args = " "
                    + PREFIX_NOTE_START_DATE + "1.1.2013 "
                    + PREFIX_NOTE_START_TIME + "11:00 AM "
                    + PREFIX_NOTE_END_TIME + "1:00PM";
            parser.parse(args);
            fail("Exception not thrown");
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
                    + PREFIX_NOTE_START_DATE + "20-12-2012 "
                    + PREFIX_NOTE_END_DATE + "19-12-2012";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertEquals(expectedMessage, e.getMessage());
        }

        try {
            // omitting end date sets its date similar to start date, hence time of the day is compared
            args = " "
                    + PREFIX_NOTE_START_DATE + "20-12-2012 "
                    + PREFIX_NOTE_START_TIME + "9:01 PM "
                    + PREFIX_NOTE_END_TIME + "9:00 PM";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void parse_moduleDoesNotExist_throwsParseException() throws ParseException {
        String expectedMessage = NoteAddCommand.MESSAGE_MODULE_CODE_DOES_NOT_EXIST;
        String moduleCode = "XX1234F";

        // invalid args, module does not exist in data file
        String args = " " + PREFIX_MODULE_CODE + moduleCode;

        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(expectedMessage, moduleCode));
        parser.parse(args);
    }

    @Test
    public void parse_validArgs_success() throws ParseException {
        try {
            moduleManager.addModule(module.build());
        } catch (DuplicateModuleException dme) {
            moduleDoesExist = true;
        }

        // valid args with no parameters
        String args = "";
        NoteAddCommand noteAddCommand = parser.parse(args);
        assertNotNull(noteAddCommand);

        // valid args with only 1 parameter.
        args = " " + PREFIX_NOTE_TITLE + "This is a title";
        noteAddCommand = null;
        noteAddCommand = parser.parse(args);
        assertNotNull(noteAddCommand);

        // valid args with complete valid input fields provided
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

    @AfterClass
    public static void tearDown() {
        if (!moduleDoesExist) {
            moduleManager.deleteModule(module.build());
        }
    }
}
