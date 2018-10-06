package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_MIN_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MIN_QUANTITY_DESC_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.MIN_QUANTITY_DESC_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_RPLIDAR;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_LAB1;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_LAB2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MIN_QUANTITY_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LAB1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LAB2;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalItems.ARDUINO;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.item.Item;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ItemBuilder;


public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Item expectedItem = new ItemBuilder(ARDUINO).withTags(VALID_TAG_LAB1).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_ARDUINO + MIN_QUANTITY_DESC_ARDUINO
                + QUANTITY_DESC_ARDUINO + TAG_DESC_LAB1, new AddCommand(expectedItem));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_RPLIDAR + NAME_DESC_ARDUINO + MIN_QUANTITY_DESC_ARDUINO
                + QUANTITY_DESC_ARDUINO + TAG_DESC_LAB1, new AddCommand(expectedItem));

        // multiple minQuantity - last email accepted
        assertParseSuccess(parser, NAME_DESC_ARDUINO + MIN_QUANTITY_DESC_RPLIDAR + MIN_QUANTITY_DESC_ARDUINO
                + QUANTITY_DESC_ARDUINO + TAG_DESC_LAB1, new AddCommand(expectedItem));

        // multiple quantity - last address accepted
        assertParseSuccess(parser, NAME_DESC_ARDUINO + MIN_QUANTITY_DESC_ARDUINO + QUANTITY_DESC_RPLIDAR
                + QUANTITY_DESC_ARDUINO + TAG_DESC_LAB1, new AddCommand(expectedItem));

        // multiple tags - all accepted
        Item expectedItemMultipleTags = new ItemBuilder(ARDUINO).withTags(VALID_TAG_LAB1, VALID_TAG_LAB2)
                .build();
        assertParseSuccess(parser, NAME_DESC_ARDUINO + MIN_QUANTITY_DESC_ARDUINO + QUANTITY_DESC_ARDUINO
                + TAG_DESC_LAB2 + TAG_DESC_LAB1, new AddCommand(expectedItemMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Item expectedItem = new ItemBuilder(ARDUINO).withTags().build();
        assertParseSuccess(parser, NAME_DESC_ARDUINO + QUANTITY_DESC_ARDUINO + MIN_QUANTITY_DESC_ARDUINO,
                new AddCommand(expectedItem));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_ARDUINO + MIN_QUANTITY_DESC_ARDUINO + QUANTITY_DESC_ARDUINO,
                expectedMessage);

        // missing minQuantity prefix
        assertParseFailure(parser, NAME_DESC_ARDUINO + VALID_MIN_QUANTITY_ARDUINO + QUANTITY_DESC_ARDUINO,
                expectedMessage);

        // missing quantity prefix
        assertParseFailure(parser, NAME_DESC_ARDUINO + MIN_QUANTITY_DESC_ARDUINO + VALID_QUANTITY_ARDUINO,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_ARDUINO + VALID_MIN_QUANTITY_ARDUINO + VALID_QUANTITY_ARDUINO,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + MIN_QUANTITY_DESC_ARDUINO + QUANTITY_DESC_ARDUINO
                + TAG_DESC_LAB2 + TAG_DESC_LAB1, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid minQuantity
        assertParseFailure(parser, NAME_DESC_ARDUINO + INVALID_MIN_QUANTITY_DESC + QUANTITY_DESC_ARDUINO
                + TAG_DESC_LAB2 + TAG_DESC_LAB1, Quantity.MESSAGE_MIN_QUANTITY_CONSTRAINTS);

        // invalid quantity
        assertParseFailure(parser, NAME_DESC_ARDUINO + MIN_QUANTITY_DESC_ARDUINO + INVALID_QUANTITY_DESC
                + TAG_DESC_LAB2 + TAG_DESC_LAB1, Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_ARDUINO + MIN_QUANTITY_DESC_ARDUINO + QUANTITY_DESC_ARDUINO
                + INVALID_TAG_DESC + VALID_TAG_LAB1, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + MIN_QUANTITY_DESC_ARDUINO + INVALID_QUANTITY_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_ARDUINO + MIN_QUANTITY_DESC_ARDUINO
                + QUANTITY_DESC_ARDUINO + TAG_DESC_LAB2 + TAG_DESC_LAB1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
