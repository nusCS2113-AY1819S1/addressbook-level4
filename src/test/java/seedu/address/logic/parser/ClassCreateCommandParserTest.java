package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.ClassCreateCommand;

public class ClassCreateCommandParserTest {
    private ClassCreateCommandParser parser = new ClassCreateCommandParser();

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClassCreateCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, ClassCreateCommand.COMMAND_WORD, expectedMessage);
    }
}
