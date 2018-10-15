package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_INDEX_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_INDEX_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PERSON_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_INDEX_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_INDEX_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_INDEX_DESC_3;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_INDEX_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERSON_INDEX_1;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalAddGroups.getAddGroup1;
import static seedu.address.testutil.TypicalAddGroups.getAddGroup3;

import org.junit.Test;

import seedu.address.logic.commands.AddGroupCommand;

public class AddGroupCommandParserTest {

    private AddGroupCommandParser parser = new AddGroupCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + GROUP_INDEX_DESC_1 + PERSON_INDEX_DESC_1,
                new AddGroupCommand(getAddGroup3()));

        // multiple group indices - last group name accepted
        assertParseSuccess(parser, GROUP_INDEX_DESC_2 + GROUP_INDEX_DESC_1 + PERSON_INDEX_DESC_1,
                new AddGroupCommand(getAddGroup3()));

        // multiple person indices - all accepted
        assertParseSuccess(parser, GROUP_INDEX_DESC_1 + PERSON_INDEX_DESC_1
                        + PERSON_INDEX_DESC_2
                        + PERSON_INDEX_DESC_3,
                new AddGroupCommand(getAddGroup1()));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroupCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_GROUP_INDEX_1 + PERSON_INDEX_DESC_1,
                expectedMessage);

        // missing person index prefix
        assertParseFailure(parser, GROUP_INDEX_DESC_1 + VALID_PERSON_INDEX_1,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_GROUP_INDEX_1 + VALID_PERSON_INDEX_1,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid group index
        assertParseFailure(parser, INVALID_GROUP_INDEX_DESC  + PERSON_INDEX_DESC_1,
                MESSAGE_INVALID_INDEX);

        // invalid person index
        assertParseFailure(parser, GROUP_INDEX_DESC_1 + INVALID_PERSON_INDEX_DESC,
                MESSAGE_INVALID_INDEX);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_GROUP_INDEX_DESC + INVALID_PERSON_INDEX_DESC,
                MESSAGE_INVALID_INDEX);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + GROUP_INDEX_DESC_1 + PERSON_INDEX_DESC_1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroupCommand.MESSAGE_USAGE));
    }

}
