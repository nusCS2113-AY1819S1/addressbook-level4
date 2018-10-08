package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_LOCATION_DESC_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_LOCATION_DESC_TUT_1;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_NAME_DESC_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_NAME_DESC_TUT_1;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_TAG_DESC_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_TAG_DESC_TUT_1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_LOCATION_TUT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_TUT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_TAG_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_TAG_TUT_1;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalGroups.TUT_1;

import org.junit.Test;

import seedu.address.logic.commands.CreateGroupCommand;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupLocation;
import seedu.address.model.group.GroupName;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.GroupBuilder;

public class CreateGroupCommandParserTest {
    private CreateGroupCommandParser parser = new CreateGroupCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Group expectedGroup = new GroupBuilder(TUT_1).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + GROUP_NAME_DESC_TUT_1 + GROUP_LOCATION_DESC_TUT_1
                + GROUP_TAG_DESC_TUT_1, new CreateGroupCommand(expectedGroup));

        // multiple group names - last group name accepted
        assertParseSuccess(parser, GROUP_NAME_DESC_CS1010 + GROUP_NAME_DESC_TUT_1 + GROUP_LOCATION_DESC_TUT_1
                + GROUP_TAG_DESC_TUT_1, new CreateGroupCommand(expectedGroup));

        // multiple group locations - last location accepted
        assertParseSuccess(parser, GROUP_NAME_DESC_TUT_1 + GROUP_LOCATION_DESC_CS1010 + GROUP_LOCATION_DESC_TUT_1
                + GROUP_TAG_DESC_TUT_1, new CreateGroupCommand(expectedGroup));

        // multiple tags - all accepted
        Group expectedGroupMultipleTags = new GroupBuilder(TUT_1).withTags(VALID_GROUP_TAG_TUT_1, VALID_GROUP_TAG_CS1010).build();
        assertParseSuccess(parser, GROUP_NAME_DESC_TUT_1 + GROUP_LOCATION_DESC_TUT_1
                + GROUP_TAG_DESC_TUT_1 + GROUP_TAG_DESC_CS1010, new CreateGroupCommand(expectedGroupMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Group expectedGroup = new GroupBuilder(TUT_1).withTags().build();
        assertParseSuccess(parser, GROUP_NAME_DESC_TUT_1 + GROUP_LOCATION_DESC_TUT_1,
                new CreateGroupCommand(expectedGroup));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateGroupCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_GROUP_NAME_TUT_1 + GROUP_LOCATION_DESC_TUT_1,
                expectedMessage);

        // missing group location prefix
        assertParseFailure(parser, GROUP_NAME_DESC_TUT_1 + VALID_GROUP_LOCATION_TUT_1,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_GROUP_NAME_TUT_1 + VALID_GROUP_LOCATION_TUT_1,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid group name
        assertParseFailure(parser, INVALID_GROUP_NAME_DESC + GROUP_LOCATION_DESC_TUT_1
                + GROUP_TAG_DESC_TUT_1 + GROUP_TAG_DESC_CS1010, GroupName.MESSAGE_GROUP_NAME_CONSTRAINTS);

        // invalid group location
        assertParseFailure(parser, GROUP_NAME_DESC_TUT_1 + INVALID_GROUP_LOCATION_DESC
                + GROUP_TAG_DESC_TUT_1 + GROUP_TAG_DESC_CS1010, GroupLocation.MESSAGE_GROUP_LOCATION_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, GROUP_NAME_DESC_TUT_1 + GROUP_LOCATION_DESC_TUT_1
                + INVALID_GROUP_TAG_DESC + VALID_GROUP_TAG_TUT_1, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_GROUP_NAME_DESC + INVALID_GROUP_LOCATION_DESC,
                GroupName.MESSAGE_GROUP_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + GROUP_NAME_DESC_TUT_1 + GROUP_LOCATION_DESC_TUT_1
                        + GROUP_TAG_DESC_TUT_1 + GROUP_TAG_DESC_CS1010,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateGroupCommand.MESSAGE_USAGE));
    }
}
