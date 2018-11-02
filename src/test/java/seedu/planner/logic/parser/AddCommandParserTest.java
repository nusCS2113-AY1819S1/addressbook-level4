package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_MONEYFLOW_DESC;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.planner.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.planner.logic.commands.CommandTestUtil.MONEYFLOW_EXPENSE_DESC_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.MONEYFLOW_INCOME_DESC_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.planner.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.planner.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.planner.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.planner.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_MONEYFLOW_EXPENSE_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.planner.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.planner.testutil.TypicalRecords.AMY;
import static seedu.planner.testutil.TypicalRecords.BOB;

import org.junit.Test;

import seedu.planner.logic.commands.AddCommand;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Name;
import seedu.planner.model.record.Record;
import seedu.planner.model.tag.Tag;
import seedu.planner.testutil.RecordBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Record expectedRecord = new RecordBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + DATE_DESC_BOB + MONEYFLOW_EXPENSE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedRecord));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + DATE_DESC_BOB + MONEYFLOW_EXPENSE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedRecord));

        // multiple dates - last date accepted
        assertParseSuccess(parser, NAME_DESC_BOB + DATE_DESC_AMY + DATE_DESC_BOB + MONEYFLOW_EXPENSE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedRecord));

        // multiple money flow - last money flow accepted
        assertParseSuccess(parser, NAME_DESC_BOB + DATE_DESC_BOB + MONEYFLOW_INCOME_DESC_AMY
                + MONEYFLOW_EXPENSE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedRecord));

        // multiple tags - all accepted
        Record expectedRecordMultipleTags = new RecordBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + DATE_DESC_BOB + MONEYFLOW_EXPENSE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedRecordMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Record expectedRecord = new RecordBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + DATE_DESC_AMY + MONEYFLOW_INCOME_DESC_AMY,
                new AddCommand(expectedRecord));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + DATE_DESC_BOB + MONEYFLOW_EXPENSE_DESC_BOB,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_DATE_BOB + MONEYFLOW_EXPENSE_DESC_BOB,
                expectedMessage);

        // missing moneyflow prefix
        assertParseFailure(parser, NAME_DESC_BOB + DATE_DESC_BOB + VALID_MONEYFLOW_EXPENSE_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_DATE_BOB + VALID_MONEYFLOW_EXPENSE_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + DATE_DESC_BOB + MONEYFLOW_EXPENSE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_DATE_DESC + MONEYFLOW_EXPENSE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Date.MESSAGE_DATE_CONSTRAINTS);

        // invalid moneyflow
        assertParseFailure(parser, NAME_DESC_BOB + DATE_DESC_BOB + INVALID_MONEYFLOW_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, MoneyFlow.MESSAGE_MONEY_FLOW_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + DATE_DESC_BOB + MONEYFLOW_EXPENSE_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + DATE_DESC_BOB + MONEYFLOW_EXPENSE_DESC_BOB,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + DATE_DESC_BOB
                + MONEYFLOW_EXPENSE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
