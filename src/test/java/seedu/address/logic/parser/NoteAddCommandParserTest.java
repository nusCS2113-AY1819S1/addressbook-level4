package seedu.address.logic.parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;


public class NoteAddCommandParserTest {

    private NoteAddCommandParser parser = new NoteAddCommandParser();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parse_invalidArgs_throws_parseException() throws ParseException {
        String args = "this is an invalid input";

        thrown.expect(ParseException.class);
        parser.parse(args);
    }
}
