package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.CheckCommand;
import seedu.address.model.person.Mode;

public class CheckCommandParserTest {
    private CheckCommandParser parser = new CheckCommandParser();
    private final String inMode = "in";
    private final String outMode = "out";

    @Test
    public void parse_modeSpecified_success() {
        // in mode
        String userInput = " " + PREFIX_MODE + inMode;
        CheckCommand expectedCommand = new CheckCommand(new Mode(inMode));
        assertParseSuccess(parser, userInput, expectedCommand);

        // out mode
        userInput = " " + PREFIX_MODE + outMode;
        expectedCommand = new CheckCommand(new Mode(outMode));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, CheckCommand.COMMAND_WORD, expectedMessage);
    }

    @Test
    public void parse_extraPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE);
        String userInput = " " + PREFIX_MODE + inMode + " " + PREFIX_MODE + inMode;
        assertParseFailure(parser, userInput, expectedMessage);

        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE);
        userInput = " " + PREFIX_MODE + inMode + " " + PREFIX_MODE + outMode;
        assertParseFailure(parser, userInput, expectedMessage);

        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE);
        userInput = " " + PREFIX_MODE + outMode + " " + PREFIX_MODE + inMode;
        assertParseFailure(parser, userInput, expectedMessage);

        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE);
        userInput = " " + PREFIX_MODE + outMode + " " + PREFIX_MODE + outMode;
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
