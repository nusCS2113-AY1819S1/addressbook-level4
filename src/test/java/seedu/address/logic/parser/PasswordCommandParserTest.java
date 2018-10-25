package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.commons.util.FileEncryptor;
import seedu.address.logic.commands.PasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class PasswordCommandParserTest {
    private PasswordCommandParser parser = new PasswordCommandParser();

    @Test
    public void noArgumentTest() {
        String expectedMessage = String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, PasswordCommand.MESSAGE_USAGE);
        try {
            parser.parse(""); // Empty password
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getLocalizedMessage());
        }
    }

    @Test
    public void passwordNotAlphaNumeric () {
        String expectedMessage = String.format(
                FileEncryptor.MESSAGE_PASSWORD_ALNUM, PasswordCommand.MESSAGE_USAGE);
        try {
            parser.parse("test^&*"); // Password with symbols

        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getLocalizedMessage());
        }
    }

    @Test
    public void passwordWithMultipleEntries() {
        String expectedMessage = String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, PasswordCommand.MESSAGE_USAGE);
        try {
            parser.parse("test test test"); // Password with multiple white spaces
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getLocalizedMessage());
        }

    }


}
