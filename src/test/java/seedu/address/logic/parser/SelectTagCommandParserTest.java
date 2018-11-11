package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_NON_ALPHA;
import static seedu.address.logic.commands.CommandTestUtil.TAG_MODULE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;

import java.util.logging.Logger;

import org.junit.Test;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.SelectTagCommand;
import seedu.address.model.tag.Tag;

//@@author ChanChunCheong
public class SelectTagCommandParserTest {
    private static final Logger logger = LogsCenter.getLogger(SelectTagCommandParserTest.class);
    private SelectTagCommandParser parser = new SelectTagCommandParser();
    @Test
    public void parse_allFieldsPresent_success() {
        Tag expectedTag = new Tag(VALID_TAG);
        assertParseSuccess(parser, TAG_MODULE,
                new SelectTagCommand(expectedTag));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectTagCommand.MESSAGE_USAGE);

        // missing Tag prefix
        assertParseFailure(parser, " " , expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid tag
        assertParseFailure(parser, INVALID_TAG_NON_ALPHA,
                MESSAGE_TAG_CONSTRAINTS);
    }
}
//@@author
