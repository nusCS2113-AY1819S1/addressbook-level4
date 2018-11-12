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
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.NoteEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.StorageController;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleManager;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.note.NoteLocation;
import seedu.address.model.note.NoteManager;
import seedu.address.model.note.NoteTitle;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.NoteBuilder;

/**
 * Contains tests for NoteEditCommandParser.
 */
public class NoteEditCommandParserTest {

    private static NoteManager noteManager = NoteManager.getInstance();
    private static ModuleManager moduleManager = ModuleManager.getInstance();
    private static ModuleBuilder module = new ModuleBuilder();
    private static boolean moduleDoesExist;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private NoteEditCommandParser parser = new NoteEditCommandParser();

    @Before
    public void setUp() {
        StorageController.enterTestMode();
        noteManager.clearNotes();
        noteManager.saveNoteList();
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws ParseException {
        String expectedMessageInvalidFormat = String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, NoteEditCommand.MESSAGE_USAGE);
        String expectedMessageBlankField = Messages.MESSAGE_BLANK_FIELD;

        String args;

        try {
            // invalid arguments
            args = "this is an invalid input";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertEquals(expectedMessageInvalidFormat, e.getMessage());
        }

        try {
            // invalid alphanumeric index
            args = "2B";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertEquals(expectedMessageInvalidFormat, e.getMessage());
        }

        try {
            // invalid args, prefix exists but no value
            args = "1 " + PREFIX_NOTE_TITLE;
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
        String args = "1 " + PREFIX_MODULE_CODE + "invalid";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(args);
    }

    @Test
    public void parse_moduleDoesNotExist_throwsParseException() throws ParseException {
        String expectedMessage = NoteEditCommand.MESSAGE_MODULE_CODE_DOES_NOT_EXIST;
        String moduleCode = "XX5920F";

        // invalid args, module does not exist in data file
        String args = " 1 " + PREFIX_MODULE_CODE + moduleCode;

        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(expectedMessage, moduleCode));
        parser.parse(args);
    }

    @Test
    public void parse_invalidTitle_throwsParseException() throws ParseException {
        String expectedMessage = NoteTitle.MESSAGE_TITLE_EXCEED_MAX_CHAR_COUNT;

        // 31 characters > max character count for title (30)
        String longTitle = "iOD24I1ZMQf7p43xiP79GIa3q6TX2v1";
        String args = "1 " + PREFIX_NOTE_TITLE + longTitle;

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(args);
    }

    @Test
    public void parse_invalidLocation_throwsParseException() throws ParseException {
        String expectedMessage = NoteLocation.MESSAGE_LOCATION_EXCEED_MAX_CHAR_COUNT;

        // 81 characters > max character count for location (80)
        String longLocation = "A52OWdCK3nm9eTN4K0gY8TjbJVKYt5VOPAFvD3nenwuShoBqxNNrIqolflLry0Y8VJb6uBUtIFLNXS8br";
        String args = "1 " + PREFIX_NOTE_LOCATION + longLocation;

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(args);
    }

    @Test
    public void parse_invalidDateTimeFormat_throwsParseException() throws ParseException {
        String expectedMessageInvalidDate = Messages.MESSAGE_INVALID_DATE_FORMAT;
        String expectedMessageInvalidTime = Messages.MESSAGE_INVALID_TIME_FORMAT;

        String args;

        try {
            // unrecognized date format for start date
            args = " 1 " + PREFIX_NOTE_START_DATE + "21 September 2013";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains(expectedMessageInvalidDate));
        }

        try {
            // unrecognized time format for start time
            args = " 1 "
                    + PREFIX_NOTE_START_DATE + "21/1/2013 "
                    + PREFIX_NOTE_START_TIME + "23:59";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains(expectedMessageInvalidTime));
        }

        try {
            // unrecognized date format for end date, the format is not one of the accepted date formats.
            args = " 1 "
                    + PREFIX_NOTE_START_DATE + "1-1-2018 "
                    + PREFIX_NOTE_END_DATE + "2018-10-12";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains(expectedMessageInvalidDate));
        }

        try {
            // unrecognized time format for end time, no space separation between the time and period(AM/PM)
            args = " 1 "
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
    public void parse_validArgs_success() throws ParseException {
        noteManager.addNote(new NoteBuilder().build());
        noteManager.saveNoteList();

        try {
            moduleManager.addModule(module.build());
        } catch (DuplicateModuleException dme) {
            moduleDoesExist = true;
        }

        String moduleCode = "CS2113";

        // valid args
        String args1 = " 1  ";
        String args2 = "1 " + PREFIX_NOTE_TITLE + "New title";
        String args3 = "1 " + PREFIX_MODULE_CODE + moduleCode;

        NoteEditCommand noteEditCommand = parser.parse(args1);
        assertNotNull(noteEditCommand);

        noteEditCommand = null;
        noteEditCommand = parser.parse(args2);
        assertNotNull(noteEditCommand);

        noteEditCommand = null;
        noteEditCommand = parser.parse(args3);
        assertNotNull(noteEditCommand);
    }

    @AfterClass
    public static void tearDown() {
        noteManager.clearNotes();
        noteManager.saveNoteList();
        if (!moduleDoesExist) {
            moduleManager.deleteModule(module.build());
        }
    }
}
