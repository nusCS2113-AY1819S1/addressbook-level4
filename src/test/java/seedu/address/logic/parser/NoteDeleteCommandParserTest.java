package seedu.address.logic.parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.NoteDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains tests for NoteDeleteCommandParser.
 */
public class NoteDeleteCommandParserTest {

    private NoteDeleteCommandParser parser = new NoteDeleteCommandParser();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parse_invalidArgs_throwsParseException() throws ParseException {
        String expectedMessage = String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, NoteDeleteCommand.MESSAGE_USAGE);

        // invalid args
        String args = " 15 this is an 2invalid input";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);

        parser.parse(args);
    }

    @Test
    public void parse_argsIsNumeric_success() throws ParseException {
        // valid args
        String args = "  3  ";

        parser.parse(args);
    }
}
