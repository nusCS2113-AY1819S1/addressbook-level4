package seedu.address.logic.parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.NoteListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.NoteBuilder;

/**
 * Contains tests for NoteListCommandParser.
 */
public class NoteListCommandParserTest {

    private NoteListCommandParser parser = new NoteListCommandParser();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parse_invalidArgs_throwsParseException() throws ParseException {
        String expectedMessage = String.format(
                Messages.MESSAGE_INVALID_COMMAND_FORMAT, NoteListCommand.MESSAGE_USAGE);

        // invalid args, missing prefix
        String args = " CS2113";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);

        parser.parse(args);
    }

    @Test
    public void parse_validArgs_success() throws ParseException {
        // valid empty args
        String args1 = "";

        // valid args with MODULE_CODE
        String args2 = " m/CS2113";

        parser.parse(args1);

        parser.parse(args2);
    }
}
