package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.NoteDeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains tests for NoteDeleteCommandParser.
 */
public class NoteDeleteCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private NoteDeleteCommandParser parser = new NoteDeleteCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() throws ParseException {
        String expectedMessageIndexError = NoteDeleteCommand.MESSAGE_PARSE_INDEX_ERROR;
        String expectedMessageDuplicate = NoteDeleteCommand.MESSAGE_DUPLICATE_INDEX_FOUND;

        String args;

        try {
            // invalid args, contains non-numeric input
            args = " 15 this is an 2invalid input";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertEquals(expectedMessageIndexError, e.getMessage());
        }

        try {
            // invalid args, input contains a non-positive integer
            args = "3 4 0";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertEquals(expectedMessageIndexError, e.getMessage());
        }

        try {
            // invalid args, index range has lower bound > upper bound
            args = "3-2";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertEquals(expectedMessageIndexError, e.getMessage());
        }

        try {
            // invalid args, index range has lower bound = upper bound
            args = "2-2";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertEquals(expectedMessageIndexError, e.getMessage());
        }

        try {
            // invalid args, index range contains a non-positive integer
            args = "-2-3";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertEquals(expectedMessageIndexError, e.getMessage());
        }

        try {
            // invalid args, duplicate index exists
            args = "1 2-4 3";
            parser.parse(args);
            fail("Exception not thrown");
        } catch (ParseException e) {
            assertEquals(expectedMessageDuplicate, e.getMessage());
        }
    }

    @Test
    public void parse_argsIsNumeric_success() throws ParseException, CommandException {
        // valid args
        String args = "  1  ";

        NoteDeleteCommand noteDeleteCommand = parser.parse(args);
        assertNotNull(noteDeleteCommand);

        // valid args, index range
        args = "2-4";
        noteDeleteCommand = null;
        noteDeleteCommand = parser.parse(args);
        assertNotNull(noteDeleteCommand);

        // valid args, single index combined with range of indexes
        args = "2-4 6";
        noteDeleteCommand = null;
        noteDeleteCommand = parser.parse(args);
        assertNotNull(noteDeleteCommand);
    }
}
