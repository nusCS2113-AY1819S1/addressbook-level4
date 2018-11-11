package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX_STRING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_NON_ALPHA;
import static seedu.address.logic.commands.CommandTestUtil.TAG_MODULE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDEX_STRING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;

import java.util.logging.Logger;

import org.junit.Test;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.model.tag.Tag;

//@@author ChanChunCheong
public class RemoveTagCommandParserTest {
    private static final Logger logger = LogsCenter.getLogger(RemoveTagCommandParserTest.class);
    private RemoveTagCommandParser parser = new RemoveTagCommandParser();
    @Test
    public void parse_allFieldsPresent_success() {
        Tag expectedTag = new Tag(VALID_TAG);
        assertParseSuccess(parser, VALID_INDEX_STRING + TAG_MODULE,
                new RemoveTagCommand(VALID_INDEX, expectedTag));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE);

        // missing index prefix
        assertParseFailure(parser, TAG_MODULE, expectedMessage);

        // missing title prefix
        assertParseFailure(parser, VALID_INDEX_STRING, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid index
        assertParseFailure(parser, INVALID_INDEX_STRING + TAG_MODULE,
                MESSAGE_INVALID_INDEX);
        // invalid tag
        assertParseFailure(parser, VALID_INDEX_STRING + INVALID_TAG_NON_ALPHA,
                MESSAGE_TAG_CONSTRAINTS);
    }
}
//author
