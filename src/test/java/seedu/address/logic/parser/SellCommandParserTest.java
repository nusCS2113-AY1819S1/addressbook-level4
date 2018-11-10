package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ISBN_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ISBN_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_ADD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_ADD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SellCommand;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Quantity;
import seedu.address.testutil.SellBookDescriptorBuilder;

public class SellCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE);

    private SellCommandParser parser = new SellCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index and isbn specified
        assertParseFailure(parser, " " + PREFIX_QUANTITY + VALID_QUANTITY_ADD, MESSAGE_INVALID_FORMAT);

        // no field specified with index
        assertParseFailure(parser, "1", Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        // no field specified with isbn
        assertParseFailure(parser, " " + PREFIX_ISBN + VALID_ISBN_ADD, Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        // no index and field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 q/" + VALID_QUANTITY_ADD, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 q/" + VALID_QUANTITY_ADD, MESSAGE_INVALID_FORMAT);

        // invalid isbn
        assertParseFailure(parser, " " + PREFIX_ISBN + INVALID_ISBN_DESC + " /q" + VALID_QUANTITY_ADD,
                Isbn.MESSAGE_ISBN_CONSTRAINTS);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 z/3", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid quantity with index
        assertParseFailure(parser, "1" + INVALID_QUANTITY_DESC, Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        // invalid quantity with isbn
        assertParseFailure(parser, ISBN_DESC_AMY + INVALID_QUANTITY_DESC,
                Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
    }

    @Test
    public void parse_quantityFieldSpecified_success() {
        Index targetIndex = INDEX_FIRST_BOOK;
        String index = Integer.toString(targetIndex.getZeroBased());
        String userInput = targetIndex.getOneBased() + QUANTITY_DESC_AMY;

        SellCommand.SellBookDescriptor descriptor =
                new SellBookDescriptorBuilder().withQuantity(VALID_QUANTITY_ADD).build();
        SellCommand expectedCommand = new SellCommand(index, "Index", descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
