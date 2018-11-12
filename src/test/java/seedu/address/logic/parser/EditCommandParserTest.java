package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ISBN_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ISBN_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ISBN_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_ADD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_BIOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ADD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_ADD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BIOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_ADD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SCIENCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_STUDIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_BOOK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditBookDescriptor;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Price;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditBookDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_ADD, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 z/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser,
                "1" + INVALID_ISBN_DESC, Isbn.MESSAGE_ISBN_CONSTRAINTS); // invalid isbn
        assertParseFailure(parser, "1" + INVALID_PRICE_DESC, Price.MESSAGE_PRICE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_QUANTITY_DESC,
                EditCommand.MESSAGE_QUANTITY_PRESENT); // invalid address
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_ISBN_DESC + PRICE_DESC_AMY, Isbn.MESSAGE_ISBN_CONSTRAINTS);

        // valid isbn followed by invalid isbn. The test case for invalid isbn followed by valid isbn
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + ISBN_DESC_BOB + INVALID_ISBN_DESC, Isbn.MESSAGE_ISBN_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Book} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_PRICE_DESC
                + VALID_QUANTITY_ADD + INVALID_ISBN_DESC, Isbn.MESSAGE_ISBN_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_BOOK;
        String userInput = targetIndex.getOneBased() + ISBN_DESC_BOB + TAG_DESC_HUSBAND
                + PRICE_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditCommand.EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withName(VALID_NAME_ADD)
                .withIsbn(VALID_ISBN_BIOLOGY).withPrice(VALID_PRICE_ADD)
                .withTags(VALID_TAG_SCIENCE, VALID_TAG_STUDIES).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_BOOK;
        String userInput = targetIndex.getOneBased() + ISBN_DESC_BOB + PRICE_DESC_AMY;

        EditCommand.EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withIsbn(VALID_ISBN_BIOLOGY)
                .withPrice(VALID_PRICE_ADD).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_BOOK;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withName(VALID_NAME_ADD).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // isbn
        userInput = targetIndex.getOneBased() + ISBN_DESC_AMY;
        descriptor = new EditBookDescriptorBuilder().withIsbn(VALID_ISBN_ADD).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // price
        userInput = targetIndex.getOneBased() + PRICE_DESC_AMY;
        descriptor = new EditBookDescriptorBuilder().withPrice(VALID_PRICE_ADD).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // quantity
        userInput = targetIndex.getOneBased() + QUANTITY_DESC_AMY;
        descriptor = new EditBookDescriptorBuilder().withQuantity(VALID_QUANTITY_ADD).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseFailure(parser, userInput, EditCommand.MESSAGE_QUANTITY_PRESENT);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditBookDescriptorBuilder().withTags(VALID_TAG_STUDIES).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_BOOK;
        String userInput = targetIndex.getOneBased() + ISBN_DESC_AMY + PRICE_DESC_AMY
                + TAG_DESC_FRIEND + ISBN_DESC_AMY + PRICE_DESC_AMY + TAG_DESC_FRIEND
                + ISBN_DESC_BOB + PRICE_DESC_BOB + TAG_DESC_HUSBAND;

        EditCommand.EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withIsbn(VALID_ISBN_BIOLOGY)
                .withPrice(VALID_PRICE_BIOLOGY).withTags(VALID_TAG_STUDIES, VALID_TAG_SCIENCE)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_BOOK;
        String userInput = targetIndex.getOneBased() + INVALID_ISBN_DESC + ISBN_DESC_BOB;
        EditCommand.EditBookDescriptor descriptor =
                new EditBookDescriptorBuilder().withIsbn(VALID_ISBN_BIOLOGY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + PRICE_DESC_BOB + INVALID_ISBN_DESC + ISBN_DESC_BOB;
        descriptor = new EditBookDescriptorBuilder().withIsbn(VALID_ISBN_BIOLOGY).withPrice(VALID_PRICE_BIOLOGY)
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_BOOK;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditBookDescriptor descriptor = new EditBookDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
