package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MIN_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MIN_QUANTITY_DESC_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.MIN_QUANTITY_DESC_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_LAB1;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_LAB2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MIN_QUANTITY_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MIN_QUANTITY_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LAB1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LAB2;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ITEM;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditItemDescriptor;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditItemDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_RPLIDAR, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_RPLIDAR, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_RPLIDAR, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_MIN_QUANTITY_DESC,
                Quantity.MESSAGE_MIN_QUANTITY_CONSTRAINTS); // invalid min_quantity
        assertParseFailure(parser, "1" + INVALID_QUANTITY_DESC,
                Quantity.MESSAGE_QUANTITY_CONSTRAINTS); // invalid quantity
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Item} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_LAB1 + TAG_DESC_LAB2 + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_LAB1 + TAG_EMPTY + TAG_DESC_LAB2, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_LAB1 + TAG_DESC_LAB2, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_MIN_QUANTITY_DESC + VALID_QUANTITY_RPLIDAR,
                Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ITEM;
        String userInput = targetIndex.getOneBased() + QUANTITY_DESC_ARDUINO + TAG_DESC_LAB2 + MIN_QUANTITY_DESC_RPLIDAR
                + NAME_DESC_RPLIDAR + TAG_DESC_LAB1;

        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withName(VALID_NAME_RPLIDAR)
                .withQuantity(VALID_QUANTITY_ARDUINO).withMinQuantity(VALID_MIN_QUANTITY_RPLIDAR)
                .withTags(VALID_TAG_LAB2, VALID_TAG_LAB1).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + MIN_QUANTITY_DESC_ARDUINO;

        EditItemDescriptor descriptor = new EditItemDescriptorBuilder()
                .withMinQuantity(VALID_MIN_QUANTITY_ARDUINO).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_ITEM;
        String userInput = targetIndex.getOneBased() + NAME_DESC_RPLIDAR;
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withName(VALID_NAME_RPLIDAR).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // minQuantity
        userInput = targetIndex.getOneBased() + MIN_QUANTITY_DESC_RPLIDAR;
        descriptor = new EditItemDescriptorBuilder().withMinQuantity(VALID_MIN_QUANTITY_RPLIDAR).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // quantity
        userInput = targetIndex.getOneBased() + QUANTITY_DESC_RPLIDAR;
        descriptor = new EditItemDescriptorBuilder().withQuantity(VALID_QUANTITY_RPLIDAR).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_LAB1;
        descriptor = new EditItemDescriptorBuilder().withTags(VALID_TAG_LAB1).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + QUANTITY_DESC_RPLIDAR + MIN_QUANTITY_DESC_RPLIDAR
                + TAG_DESC_LAB1 + QUANTITY_DESC_RPLIDAR + MIN_QUANTITY_DESC_RPLIDAR + TAG_DESC_LAB1
                + QUANTITY_DESC_ARDUINO + MIN_QUANTITY_DESC_ARDUINO + TAG_DESC_LAB2;

        EditItemDescriptor descriptor = new EditItemDescriptorBuilder()
                .withMinQuantity(VALID_MIN_QUANTITY_ARDUINO).withQuantity(VALID_QUANTITY_ARDUINO)
                .withTags(VALID_TAG_LAB1, VALID_TAG_LAB2)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_ITEM;
        String userInput = targetIndex.getOneBased() + INVALID_QUANTITY_DESC + QUANTITY_DESC_ARDUINO;
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withQuantity(VALID_QUANTITY_ARDUINO).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + MIN_QUANTITY_DESC_ARDUINO + INVALID_QUANTITY_DESC
                + QUANTITY_DESC_ARDUINO
                + QUANTITY_DESC_ARDUINO;
        descriptor = new EditItemDescriptorBuilder().withQuantity(VALID_QUANTITY_ARDUINO)
                .withMinQuantity(VALID_MIN_QUANTITY_ARDUINO).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_ITEM;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
