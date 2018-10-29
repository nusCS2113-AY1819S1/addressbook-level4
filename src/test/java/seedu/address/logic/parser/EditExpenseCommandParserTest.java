package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EXPENSE_CATEGORY_DESC_MRT;
import static seedu.address.logic.commands.CommandTestUtil.EXPENSE_CATEGORY_DESC_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.EXPENSE_DATE_DESC_MRT;
import static seedu.address.logic.commands.CommandTestUtil.EXPENSE_DATE_DESC_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.EXPENSE_VALUE_DESC_MRT;
import static seedu.address.logic.commands.CommandTestUtil.EXPENSE_VALUE_DESC_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPENSE_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPENSE_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPENSE_VALUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_TAOBAO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_CATEGORY_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_DATE_MRT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseExpenseSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXPENSE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EXPENSE;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditExpenseCommand;
import seedu.address.model.expense.ExpenseCategory;
import seedu.address.model.expense.ExpenseDate;
import seedu.address.model.expense.ExpenseValue;
import seedu.address.model.tag.Tag;

public class EditExpenseCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditExpenseCommand.MESSAGE_USAGE);

    private EditExpenseCommandParser parser = new EditExpenseCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_EXPENSE_CATEGORY_SHOPPING, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditExpenseCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + EXPENSE_CATEGORY_DESC_MRT, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + EXPENSE_CATEGORY_DESC_MRT, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_EXPENSE_CATEGORY_DESC,
                ExpenseCategory.MESSAGE_EXPENSE_CATEGORY_CONSTRAINTS); // invalid expense category
        assertParseFailure(parser, "1" + INVALID_EXPENSE_DATE_DESC,
                ExpenseDate.MESSAGE_EXPENSE_DATE_CONSTRAINTS); // invalid expense date
        assertParseFailure(parser, "1" + INVALID_EXPENSE_VALUE_DESC,
                ExpenseValue.MESSAGE_EXPENSE_VALUE_CONSTRAINTS); // invalid expense value
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // invalid value followed by valid date
        assertParseFailure(parser, "1" + INVALID_EXPENSE_VALUE_DESC + EXPENSE_DATE_DESC_SHOPPING,
                ExpenseValue.MESSAGE_EXPENSE_VALUE_CONSTRAINTS);

        // valid value followed by invalid value. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + EXPENSE_VALUE_DESC_SHOPPING + INVALID_EXPENSE_VALUE_DESC,
                ExpenseValue.MESSAGE_EXPENSE_VALUE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Expense} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_TAOBAO + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_TAOBAO + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_TAOBAO + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_EXPENSE_CATEGORY_DESC + INVALID_EXPENSE_VALUE_DESC
                + VALID_EXPENSE_DATE_MRT, ExpenseCategory.MESSAGE_EXPENSE_CATEGORY_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_EXPENSE;
        String userInput = targetIndex.getOneBased() + EXPENSE_VALUE_DESC_MRT + TAG_DESC_HUSBAND
                + EXPENSE_DATE_DESC_SHOPPING + EXPENSE_CATEGORY_DESC_SHOPPING + TAG_DESC_TAOBAO;
        assertParseExpenseSuccess(parser, userInput);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_EXPENSE;
        String userInput = targetIndex.getOneBased() + EXPENSE_VALUE_DESC_MRT + EXPENSE_DATE_DESC_MRT;
        assertParseExpenseSuccess(parser, userInput);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // expense category
        Index targetIndex = INDEX_THIRD_EXPENSE;
        String userInput = targetIndex.getOneBased() + EXPENSE_CATEGORY_DESC_SHOPPING;
        assertParseExpenseSuccess(parser, userInput);

        // expense date
        userInput = targetIndex.getOneBased() + EXPENSE_DATE_DESC_SHOPPING;
        assertParseExpenseSuccess(parser, userInput);

        // expense value
        userInput = targetIndex.getOneBased() + EXPENSE_VALUE_DESC_SHOPPING;
        assertParseExpenseSuccess(parser, userInput);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_TAOBAO;
        assertParseExpenseSuccess(parser, userInput);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_EXPENSE;
        String userInput = targetIndex.getOneBased() + EXPENSE_VALUE_DESC_SHOPPING + EXPENSE_CATEGORY_DESC_SHOPPING
                + EXPENSE_DATE_DESC_SHOPPING + TAG_DESC_TAOBAO + EXPENSE_VALUE_DESC_MRT + EXPENSE_CATEGORY_DESC_MRT
                + EXPENSE_DATE_DESC_MRT + TAG_DESC_HUSBAND + EXPENSE_VALUE_DESC_SHOPPING
                + EXPENSE_CATEGORY_DESC_SHOPPING + EXPENSE_DATE_DESC_SHOPPING + TAG_DESC_TAOBAO;
        assertParseExpenseSuccess(parser, userInput);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_EXPENSE;
        String userInput = targetIndex.getOneBased() + INVALID_EXPENSE_VALUE_DESC + EXPENSE_VALUE_DESC_SHOPPING;
        assertParseExpenseSuccess(parser, userInput);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_EXPENSE_VALUE_DESC + EXPENSE_DATE_DESC_MRT
                + EXPENSE_VALUE_DESC_MRT;
        assertParseExpenseSuccess(parser, userInput);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_EXPENSE;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;
        assertParseExpenseSuccess(parser, userInput);
    }

}
