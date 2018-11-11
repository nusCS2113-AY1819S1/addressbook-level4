//@@author XiaoYunhan
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.SortTaskCommand.INPUT_BLANK;
//import static seedu.address.logic.commands.SortTaskCommand.INPUT_DATE;
//import static seedu.address.logic.commands.SortTaskCommand.INPUT_DEFAULT;
import static seedu.address.logic.commands.SortTaskCommand.INPUT_INVALID;
//import static seedu.address.logic.commands.SortTaskCommand.INPUT_MODULE;
//import static seedu.address.logic.commands.SortTaskCommand.INPUT_PRIORITY;
//import static seedu.address.logic.commands.SortTaskCommand.INPUT_REVERSE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

//import seedu.address.logic.commands.Command;

import seedu.address.logic.commands.SortTaskCommand;

public class SortTaskCommandParserTest {

    private SortTaskCommandParser parser = new SortTaskCommandParser();

    /*
    @Test
    public void parseDefaultSuccess() {
        Command command = new SortTaskCommand(INPUT_DEFAULT);
        assertParseSuccess(parser, INPUT_DEFAULT, command);
    }

    @Test
    public void parseDateSuccess() {
        Command command = new SortTaskCommand(INPUT_DATE);
        assertParseSuccess(parser, INPUT_DATE, command);
    }

    @Test
    public void parsePrioritySuccess() {
        Command command = new SortTaskCommand(INPUT_PRIORITY);
        assertParseSuccess(parser, INPUT_PRIORITY, command);
    }

    @Test
    public void parseModuleSuccess() {
        Command command = new SortTaskCommand(INPUT_MODULE);
        assertParseSuccess(parser, INPUT_MODULE, command);
    }

    @Test
    public void parseReverseSuccess() {
        Command command = new SortTaskCommand(INPUT_REVERSE);
        assertParseSuccess(parser, INPUT_REVERSE, command);
    }
    */

    @Test
    public void parseBlankFailure() {
        String input = INPUT_BLANK;
        String output = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortTaskCommand.MESSAGE_USAGE);
        assertParseFailure(parser, input, output);
    }

    @Test
    public void parseInvalidParameterFailure() {
        String input = INPUT_INVALID;
        String output = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortTaskCommand.MESSAGE_USAGE);
        assertParseFailure(parser, input, output);
    }

}
