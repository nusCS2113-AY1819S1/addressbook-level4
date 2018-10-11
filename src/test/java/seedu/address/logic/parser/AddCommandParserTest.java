package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COST_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ISBN_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ISBN_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalBooks.AMY;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.book.Book;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Price;
import seedu.address.model.book.Quantity;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BookBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();
    /*
    @Test
    public void parse_allFieldsPresent_success() {
        Book expectedBook = new BookBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + ISBN_DESC_BOB + PRICE_DESC_BOB
                + COST_DESC_BOB + QUANTITY_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedBook));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + ISBN_DESC_BOB + PRICE_DESC_BOB
                + COST_DESC_BOB + QUANTITY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedBook));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + ISBN_DESC_AMY + ISBN_DESC_BOB + PRICE_DESC_BOB
                + COST_DESC_BOB + QUANTITY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedBook));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + ISBN_DESC_BOB + PRICE_DESC_AMY + PRICE_DESC_BOB
                + COST_DESC_BOB + QUANTITY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedBook));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + ISBN_DESC_BOB + PRICE_DESC_BOB + COST_DESC_BOB + QUANTITY_DESC_AMY
                + QUANTITY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedBook));

        // multiple tags - all accepted
        Book expectedBookMultipleTags = new BookBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + ISBN_DESC_BOB + PRICE_DESC_BOB + COST_DESC_BOB + QUANTITY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedBookMultipleTags));
    }
    */
    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Book expectedBook = new BookBuilder(AMY).withTags().build();
        // assertParseSuccess(parser, NAME_DESC_AMY + ISBN_DESC_AMY + PRICE_DESC_AMY
        // + COST_DESC_AMY + QUANTITY_DESC_AMY + TAG_DESC_FRIEND,
        // new AddCommand(expectedBook));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + ISBN_DESC_BOB + PRICE_DESC_BOB + COST_DESC_BOB + QUANTITY_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_ISBN_BOB + PRICE_DESC_BOB + COST_DESC_BOB + QUANTITY_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + ISBN_DESC_BOB + VALID_PRICE_BOB + QUANTITY_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + ISBN_DESC_BOB + PRICE_DESC_BOB + VALID_QUANTITY_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_ISBN_BOB + VALID_PRICE_BOB + VALID_QUANTITY_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        // assertParseFailure(parser, INVALID_NAME_DESC + ISBN_DESC_BOB
        // + PRICE_DESC_BOB + COST_DESC_BOB + QUANTITY_DESC_BOB
        // + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid isbn
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_ISBN_DESC
                + PRICE_DESC_BOB + QUANTITY_DESC_BOB + COST_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Isbn.MESSAGE_ISBN_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser, NAME_DESC_BOB + ISBN_DESC_BOB
                + INVALID_PRICE_DESC + QUANTITY_DESC_BOB + COST_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Price.MESSAGE_PRICE_CONSTRAINTS);

        // invalid quantity
        assertParseFailure(parser, NAME_DESC_BOB + ISBN_DESC_BOB
                + PRICE_DESC_BOB + COST_DESC_BOB + INVALID_QUANTITY_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Quantity.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + ISBN_DESC_BOB
                + PRICE_DESC_BOB + COST_DESC_BOB + QUANTITY_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);
        /*
        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + ISBN_DESC_BOB
                        + PRICE_DESC_BOB + COST_DESC_BOB + INVALID_QUANTITY_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + ISBN_DESC_BOB + PRICE_DESC_BOB + COST_DESC_BOB
                + QUANTITY_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        */
    }
}
