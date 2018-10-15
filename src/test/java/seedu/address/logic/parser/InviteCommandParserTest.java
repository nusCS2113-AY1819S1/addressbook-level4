package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.InviteCommand;

//@@author jieliangang

public class InviteCommandParserTest {

    private InviteCommandParser parser = new InviteCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, InviteCommand.MESSAGE_USAGE);

        // no field specified
        assertParseFailure(parser, PREFIX_TO + "1", expectedMessage);

        // no index field specified
        assertParseFailure(parser, "1", expectedMessage);

        // no index and no field specified
        assertParseFailure(parser, "", expectedMessage);
        assertParseFailure(parser, " ", expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, InviteCommand.MESSAGE_USAGE);
        // negative index
        assertParseFailure(parser, "-1 " + PREFIX_TO + "1", expectedMessage);
        assertParseFailure(parser, "1 " + PREFIX_TO + "-1", expectedMessage);
        assertParseFailure(parser, "-1 " + PREFIX_TO + "-1", expectedMessage);

        // zero index
        assertParseFailure(parser, "0 " + PREFIX_TO + "1", expectedMessage);
        assertParseFailure(parser, "1 " + PREFIX_TO + "0", expectedMessage);
        assertParseFailure(parser, "0 " + PREFIX_TO + "0", expectedMessage);

        // invalid arguments
        assertParseFailure(parser, "amy " + PREFIX_TO + "birthday", expectedMessage);
        assertParseFailure(parser, "1 " + PREFIX_TO + "event", expectedMessage);

    }

    @Test
    public void parse_allFieldsPresent_success() {
        Index personIndex = INDEX_FIRST_PERSON;
        Index eventIndex = INDEX_FIRST_EVENT;
        String userInput = String.valueOf(personIndex.getOneBased())
                + " " + PREFIX_TO + String.valueOf(eventIndex.getOneBased());
        InviteCommand expectedCommand = new InviteCommand(personIndex, eventIndex);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
