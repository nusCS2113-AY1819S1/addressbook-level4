package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

//import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ViewTaskCommand;

public class ViewTaskCommandParserTest {

    private ViewTaskCommandParser parser = new ViewTaskCommandParser();

    /*
    @Test
    public void parseParameterCompletedSuccess() {
        Command command = new ViewTaskCommand("completed");
        assertParseSuccess(parser, "completed", command);
    }

    @Test
    public void parseParameterUncompletedSuccess() {
        Command command = new ViewTaskCommand("uncompleted");
        assertParseSuccess(parser, "uncompleted", command);
    }

    @Test
    public void parseParameterAllSuccess() {
        Command command = new ViewTaskCommand("all");
        assertParseSuccess(parser, "all", command);
    }
    */

    @Test
    public void parseBlankFailure() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseInvalidFailure() {
        assertParseFailure(parser, "invalid", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewTaskCommand.MESSAGE_USAGE));
    }
}
