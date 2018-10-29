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
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_TAOBAO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_CATEGORY_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_DATE_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_VALUE_SHOPPING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseExpenseSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.AddExpenseCommand;
import seedu.address.model.expense.ExpenseCategory;
import seedu.address.model.expense.ExpenseDate;
import seedu.address.model.expense.ExpenseValue;
import seedu.address.model.tag.Tag;

public class AddExpenseCommandParserTest {
    private AddExpenseCommandParser parser = new AddExpenseCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        // whitespace only preamble
        assertParseExpenseSuccess(parser, PREAMBLE_WHITESPACE + EXPENSE_CATEGORY_DESC_SHOPPING
                + EXPENSE_VALUE_DESC_SHOPPING + EXPENSE_DATE_DESC_SHOPPING + TAG_DESC_TAOBAO);

        // multiple expense category - last expense category accepted
        assertParseExpenseSuccess(parser, EXPENSE_CATEGORY_DESC_MRT + EXPENSE_CATEGORY_DESC_SHOPPING
                + EXPENSE_VALUE_DESC_SHOPPING + EXPENSE_DATE_DESC_SHOPPING + TAG_DESC_TAOBAO);

        // multiple expense date - last expense date accepted
        assertParseExpenseSuccess(parser, EXPENSE_CATEGORY_DESC_SHOPPING + EXPENSE_VALUE_DESC_SHOPPING
                + EXPENSE_DATE_DESC_MRT + EXPENSE_DATE_DESC_SHOPPING + TAG_DESC_TAOBAO);

        // multiple expense value - last expense value accepted
        assertParseExpenseSuccess(parser, EXPENSE_CATEGORY_DESC_SHOPPING + EXPENSE_VALUE_DESC_MRT
                + EXPENSE_VALUE_DESC_SHOPPING + EXPENSE_DATE_DESC_SHOPPING + TAG_DESC_TAOBAO);

        // multiple tags - all accepted
        assertParseExpenseSuccess(parser, EXPENSE_CATEGORY_DESC_SHOPPING + EXPENSE_VALUE_DESC_SHOPPING
                + EXPENSE_DATE_DESC_SHOPPING + TAG_DESC_TAOBAO + TAG_DESC_HUSBAND);
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        assertParseExpenseSuccess(parser, EXPENSE_CATEGORY_DESC_SHOPPING + EXPENSE_VALUE_DESC_SHOPPING
                + EXPENSE_DATE_DESC_SHOPPING);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpenseCommand.MESSAGE_USAGE);

        // missing expense category prefix
        assertParseFailure(parser, VALID_EXPENSE_CATEGORY_SHOPPING + EXPENSE_VALUE_DESC_SHOPPING
                + EXPENSE_DATE_DESC_SHOPPING, expectedMessage);

        // missing expense date prefix
        assertParseFailure(parser, EXPENSE_CATEGORY_DESC_SHOPPING + EXPENSE_VALUE_DESC_SHOPPING
                + VALID_EXPENSE_DATE_SHOPPING, expectedMessage);

        // missing expense value prefix
        assertParseFailure(parser, EXPENSE_CATEGORY_DESC_SHOPPING + VALID_EXPENSE_VALUE_SHOPPING
                + EXPENSE_DATE_DESC_SHOPPING, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_EXPENSE_CATEGORY_SHOPPING + VALID_EXPENSE_DATE_SHOPPING
                + VALID_EXPENSE_VALUE_SHOPPING, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid expense category
        assertParseFailure(parser, INVALID_EXPENSE_CATEGORY_DESC + EXPENSE_VALUE_DESC_SHOPPING
                + EXPENSE_DATE_DESC_SHOPPING + TAG_DESC_TAOBAO, ExpenseCategory.MESSAGE_EXPENSE_CATEGORY_CONSTRAINTS);

        // invalid expense date
        assertParseFailure(parser, EXPENSE_CATEGORY_DESC_SHOPPING + EXPENSE_VALUE_DESC_SHOPPING
                + INVALID_EXPENSE_DATE_DESC + TAG_DESC_TAOBAO, ExpenseDate.MESSAGE_EXPENSE_DATE_CONSTRAINTS);

        // invalid expense value
        assertParseFailure(parser, EXPENSE_CATEGORY_DESC_SHOPPING + INVALID_EXPENSE_VALUE_DESC
                + EXPENSE_DATE_DESC_SHOPPING + TAG_DESC_TAOBAO, ExpenseValue.MESSAGE_EXPENSE_VALUE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, EXPENSE_CATEGORY_DESC_SHOPPING + EXPENSE_VALUE_DESC_SHOPPING
                + EXPENSE_DATE_DESC_SHOPPING + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_EXPENSE_CATEGORY_DESC + EXPENSE_VALUE_DESC_SHOPPING
                + INVALID_EXPENSE_DATE_DESC, ExpenseCategory.MESSAGE_EXPENSE_CATEGORY_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EXPENSE_CATEGORY_DESC_SHOPPING
                        + EXPENSE_VALUE_DESC_SHOPPING + EXPENSE_DATE_DESC_SHOPPING + TAG_DESC_TAOBAO,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpenseCommand.MESSAGE_USAGE));
    }
}
