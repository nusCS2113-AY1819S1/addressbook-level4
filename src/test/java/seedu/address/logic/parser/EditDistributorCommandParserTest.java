package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DIST_NAME_DESC_AHBENG;
import static seedu.address.logic.commands.CommandTestUtil.DIST_PHONE_DESC_AHBENG;
import static seedu.address.logic.commands.CommandTestUtil.DIST_PHONE_DESC_AHHUAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DIST_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DIST_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HEALTHY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_SWEET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIST_NAME_AHBENG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIST_PHONE_AHBENG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIST_PHONE_AHHUAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HEALTHY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SWEET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditDistributorCommand;
import seedu.address.logic.commands.EditDistributorCommand.EditDistributorDescriptor;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.distributor.DistributorPhone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditDistributorDescriptorBuilder;

public class EditDistributorCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditDistributorCommand.MESSAGE_USAGE);

    private EditDistributorCommandParser parser = new EditDistributorCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DIST_NAME_AHBENG, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditDistributorCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_DIST_NAME_AHBENG, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_DIST_NAME_AHBENG, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_DIST_NAME_DESC,
                DistributorName.MESSAGE_NAME_CONSTRAINTS); // invalid distributor name
        assertParseFailure(parser, "1" + INVALID_DIST_PHONE_DESC,
                DistributorPhone.MESSAGE_PHONE_CONSTRAINTS); // invalid distributor phone
        assertParseFailure(parser, "1" + INVALID_TAG_DESC,
                Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // invalid distributor phone followed by valid distributor phone
        assertParseFailure(parser, "1" + INVALID_DIST_PHONE_DESC + VALID_DIST_PHONE_AHBENG,
                DistributorPhone.MESSAGE_PHONE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid distributor phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + DIST_PHONE_DESC_AHBENG + INVALID_DIST_PHONE_DESC,
                DistributorPhone.MESSAGE_PHONE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Distributor} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_HEALTHY + TAG_DESC_SWEET + TAG_EMPTY,
                Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_HEALTHY + TAG_EMPTY + TAG_DESC_SWEET,
                Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_HEALTHY + TAG_DESC_SWEET,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_DIST_NAME_DESC + INVALID_DIST_PHONE_DESC
                + VALID_DIST_PHONE_AHBENG, DistributorName.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + DIST_PHONE_DESC_AHHUAT + TAG_DESC_SWEET + DIST_PHONE_DESC_AHBENG;

        EditDistributorDescriptor descriptor = new EditDistributorDescriptorBuilder()
                .withPhone(VALID_DIST_PHONE_AHHUAT).withTags(VALID_TAG_SWEET)
                .withPhone(VALID_DIST_PHONE_AHBENG).build();
        EditDistributorCommand expectedCommand = new EditDistributorCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + DIST_PHONE_DESC_AHHUAT + TAG_DESC_HEALTHY;

        EditDistributorDescriptor descriptor = new EditDistributorDescriptorBuilder()
                .withPhone(VALID_DIST_PHONE_AHHUAT).withTags(VALID_TAG_HEALTHY).build();
        EditDistributorCommand expectedCommand = new EditDistributorCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + DIST_NAME_DESC_AHBENG;
        EditDistributorDescriptor descriptor = new EditDistributorDescriptorBuilder()
                .withName(VALID_DIST_NAME_AHBENG).build();
        EditDistributorCommand expectedCommand = new EditDistributorCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + DIST_PHONE_DESC_AHHUAT;
        descriptor = new EditDistributorDescriptorBuilder().withPhone(VALID_DIST_PHONE_AHHUAT).build();
        expectedCommand = new EditDistributorCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_HEALTHY;
        descriptor = new EditDistributorDescriptorBuilder().withTags(VALID_TAG_HEALTHY).build();
        expectedCommand = new EditDistributorCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + DIST_PHONE_DESC_AHBENG + TAG_DESC_HEALTHY
                + DIST_PHONE_DESC_AHBENG + TAG_DESC_HEALTHY + DIST_PHONE_DESC_AHHUAT + TAG_DESC_SWEET;

        EditDistributorDescriptor descriptor = new EditDistributorDescriptorBuilder().withPhone(VALID_DIST_PHONE_AHHUAT)
                .withTags(VALID_TAG_HEALTHY, VALID_TAG_SWEET).build();
        EditDistributorCommand expectedCommand = new EditDistributorCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_DIST_PHONE_DESC + DIST_PHONE_DESC_AHHUAT;
        EditDistributorDescriptor descriptor = new EditDistributorDescriptorBuilder()
                .withPhone(VALID_DIST_PHONE_AHHUAT).build();
        EditDistributorCommand expectedCommand = new EditDistributorCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_DIST_PHONE_DESC + DIST_PHONE_DESC_AHHUAT;
        descriptor = new EditDistributorDescriptorBuilder().withPhone(VALID_DIST_PHONE_AHHUAT).build();
        expectedCommand = new EditDistributorCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditDistributorDescriptor descriptor = new EditDistributorDescriptorBuilder().withTags().build();
        EditDistributorCommand expectedCommand = new EditDistributorCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
