package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.planner.logic.commands.Command;
import seedu.planner.logic.commands.SortCommand;

public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_firstFieldsPresent_success() {
        // Category specified
        Command command = new SortCommand(SortCommand.CATEGORY_MONEY, true);
        String inputCommand = SortCommand.CATEGORY_MONEY;
        assertParseSuccess(parser, inputCommand, command);

        // Order specified
        command = new SortCommand(SortCommand.CATEGORY_NAME, false);
        inputCommand = SortCommand.DESCENDING_CONDITION;
        assertParseSuccess(parser, inputCommand, command);

        // Order specified
        command = new SortCommand(SortCommand.CATEGORY_NAME, true);
        inputCommand = SortCommand.ASCENDING_CONDITION;
        assertParseSuccess(parser, inputCommand, command);
    }

    @Test
    public void parse_bothFieldsPresent_success() {
        // Category and order specified
        Command command = new SortCommand(SortCommand.CATEGORY_MONEY, false);
        String inputCommand = SortCommand.CATEGORY_MONEY + " "
                + SortCommand.DESCENDING_CONDITION;
        assertParseSuccess(parser, inputCommand, command);

        // Order and category specified
        command = new SortCommand(SortCommand.CATEGORY_DATE, true);
        inputCommand = SortCommand.ASCENDING_CONDITION + " "
                + SortCommand.CATEGORY_DATE;
        assertParseSuccess(parser, inputCommand, command);
    }

    @Test
    public void parse_additionalWhitespacesPresent_success() {
        Command command = new SortCommand(SortCommand.CATEGORY_MONEYFLOW, false);
        String inputCommand = "     " + SortCommand.CATEGORY_MONEYFLOW + "     "
                + SortCommand.DESCENDING_CONDITION + "      ";
        assertParseSuccess(parser, inputCommand, command);
    }


    @Test
    public void parse_noFieldsPresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        String inputCommand = "";

        assertParseFailure(parser, inputCommand, expectedMessage);
    }

    @Test
    public void parse_invalidFieldPresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        String inputCommand = "invalidword";
        assertParseFailure(parser, inputCommand, expectedMessage);

        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        inputCommand = SortCommand.ASCENDING_CONDITION + " " + "invalidword";
        assertParseFailure(parser, inputCommand, expectedMessage);
    }

    @Test
    public void parse_tooManyFieldsPresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        String inputCommand = SortCommand.DESCENDING_CONDITION + " " + SortCommand.CATEGORY_DATE + " "
                + "unnecessarystring" + " " + "morestrings";
        assertParseFailure(parser, inputCommand, expectedMessage);
    }
}
