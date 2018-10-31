package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveCommand;

//@@author jieliangang
public class RemoveCommandParserTest {

    private RemoveCommandParser parser = new RemoveCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE);

        // no field specified
        assertParseFailure(parser, PREFIX_FROM + "1", expectedMessage);

        // no index field specified
        assertParseFailure(parser, "1", expectedMessage);

        // no index and no field specified
        assertParseFailure(parser, "", expectedMessage);
        assertParseFailure(parser, " ", expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE);
        // negative index
        assertParseFailure(parser, "-1 " + PREFIX_FROM + "1", expectedMessage);
        assertParseFailure(parser, "1 " + PREFIX_FROM + "-1", expectedMessage);
        assertParseFailure(parser, "-1 " + PREFIX_FROM + "-1", expectedMessage);

        // zero index
        assertParseFailure(parser, "0 " + PREFIX_FROM + "1", expectedMessage);
        assertParseFailure(parser, "1 " + PREFIX_FROM + "0", expectedMessage);
        assertParseFailure(parser, "0 " + PREFIX_FROM + "0", expectedMessage);

        // invalid arguments
        assertParseFailure(parser, "amy " + PREFIX_FROM + "birthday", expectedMessage);
        assertParseFailure(parser, "1 " + PREFIX_FROM + "event", expectedMessage);

    }

    @Test
    public void parse_allFieldsPresent_success() {
        Index personIndex = INDEX_FIRST_PERSON;
        Index eventIndex = INDEX_FIRST_EVENT;
        String userInput = String.valueOf(personIndex.getOneBased())
                + " " + PREFIX_FROM + String.valueOf(eventIndex.getOneBased());
        RemoveCommand expectedCommand = new RemoveCommand(personIndex, eventIndex);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
