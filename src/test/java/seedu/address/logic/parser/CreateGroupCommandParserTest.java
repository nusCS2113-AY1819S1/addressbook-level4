package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_LOC_DESC_LAB;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_LOC_DESC_TUT;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_NAME_DESC_LAB;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_NAME_DESC_TUT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_LOC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_TAG;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_LAB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_TUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_LOCATION_LAB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_LOCATION_TUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME_LAB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.CreateGroupCommand;
import seedu.address.model.group.GroupLocation;
import seedu.address.model.group.GroupName;
import seedu.address.model.tag.Tag;

public class CreateGroupCommandParserTest {
    private CreateGroupCommandParser parser = new CreateGroupCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        /** TODO to be fixed!
        Group expectedGroup = new GroupBuilder(LAB).withTags(VALID_TAG_DIFFICULTY).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + GROUP_NAME_DESC_TUT + GROUP_LOC_DESC_TUT
                , new CreateGroupCommand(expectedGroup));

        // multiple group names entered - last group name accepted
        assertParseSuccess(parser, GROUP_NAME_DESC_TUT + GROUP_NAME_DESC_TUT + GROUP_LOC_DESC_TUT
                + TAG_DESC_TUT, new CreateGroupCommand(expectedGroup));

        // multiple group location entered - last group location accepted
        assertParseSuccess(parser, GROUP_NAME_DESC_TUT + GROUP_LOC_DESC_TUT + GROUP_LOC_DESC_TUT
                + TAG_DESC_TUT, new CreateGroupCommand(expectedGroup));

        // multiple tags - all accepted
        Group expectedGroupMultipleTags = new GroupBuilder(LAB).withTags(VALID_TAG_TUT, VALID_TAG_LAB)
                .build();
        assertParseSuccess(parser, GROUP_NAME_DESC_TUT + GROUP_LOC_DESC_TUT + TAG_DESC_TUT
                + TAG_DESC_LAB, new CreateGroupCommand(expectedGroupMultipleTags));
         */
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // No tags
        /** TODO require to fix testing
        Group expectedGroup = new GroupBuilder(LAB).withTags().build();
        assertParseSuccess(parser, GROUP_NAME_DESC_LAB + GROUP_LOC_DESC_LAB,
                new CreateGroupCommand(expectedGroup));
        */
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateGroupCommand.MESSAGE_USAGE);

        // missing group name prefix
        assertParseFailure(parser, VALID_GROUP_LOCATION_TUT + GROUP_LOC_DESC_TUT,
                expectedMessage);

        // missing group location prefix
        assertParseFailure(parser, GROUP_NAME_DESC_LAB + VALID_GROUP_LOCATION_LAB,
                expectedMessage);


        // all prefixes missing
        assertParseFailure(parser, VALID_GROUP_NAME_LAB + VALID_GROUP_LOCATION_LAB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid group name
        assertParseFailure(parser, INVALID_GROUP_NAME_DESC + GROUP_LOC_DESC_LAB
                + TAG_DESC_TUT + TAG_DESC_LAB, GroupName.MESSAGE_GROUP_NAME_CONSTRAINTS);

        // invalid group location
        assertParseFailure(parser, GROUP_NAME_DESC_LAB + INVALID_GROUP_LOC_DESC
                + TAG_DESC_TUT + TAG_DESC_LAB, GroupLocation.MESSAGE_GROUP_LOCATION_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, GROUP_NAME_DESC_LAB + GROUP_LOC_DESC_LAB
                + INVALID_GROUP_TAG + TAG_DESC_LAB, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        // invalid group name & invalid tag
        assertParseFailure(parser, INVALID_GROUP_NAME_DESC + GROUP_LOC_DESC_LAB
                + INVALID_GROUP_TAG + TAG_DESC_LAB, GroupName.MESSAGE_GROUP_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + GROUP_NAME_DESC_TUT
                        + GROUP_LOC_DESC_TUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateGroupCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseNoParametersFailure() {
        String expectedOutput = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateGroupCommand.MESSAGE_USAGE);

        //  without any parameters
        assertParseFailure(parser, CreateGroupCommand.COMMAND_WORD, expectedOutput);
        assertParseFailure(parser, CreateGroupCommand.COMMAND_WORD_2, expectedOutput);
    }
}
