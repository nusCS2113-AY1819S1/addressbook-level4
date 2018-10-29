package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DAY_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.DAY_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.MONTH_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.MONTH_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_JAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_2018;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_2018;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_2019;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_JAN_2018;

import java.util.logging.Logger;

import org.junit.Test;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.SelectDeadlineCommand;
import seedu.address.model.task.Deadline;

//@@author emobeany
public class SelectDeadlineCommandParserTest {
    private static final Logger logger = LogsCenter.getLogger(SelectDeadlineCommandParserTest.class);
    private SelectDeadlineCommandParser parser = new SelectDeadlineCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Parser parser = new SelectDeadlineCommandParser();
        Deadline expectedDeadline = VALID_1ST_JAN_2018;

        // preamble only contains whitespace
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DAY_DESC_1 + MONTH_DESC_1 + YEAR_DESC_2018,
                new SelectDeadlineCommand(expectedDeadline));

        // multiple days - last day accepted
        assertParseSuccess(parser, DAY_DESC_2 + DAY_DESC_1 + MONTH_DESC_1 + YEAR_DESC_2018,
                new SelectDeadlineCommand(expectedDeadline));

        // multiple months - last month accepted
        assertParseSuccess(parser, DAY_DESC_1 + MONTH_DESC_2 + MONTH_DESC_1 + YEAR_DESC_2018,
                new SelectDeadlineCommand(expectedDeadline));

        // multiple years - last year accepted
        assertParseSuccess(parser, DAY_DESC_1 + MONTH_DESC_1 + YEAR_DESC_2019 + YEAR_DESC_2018,
                new SelectDeadlineCommand(expectedDeadline));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDeadlineCommand.MESSAGE_USAGE);

        // missing day prefix
        assertParseFailure(parser, VALID_DAY_1 + MONTH_DESC_1 + YEAR_DESC_2018, expectedMessage);

        // missing month prefix
        assertParseFailure(parser, DAY_DESC_1 + VALID_MONTH_JAN + YEAR_DESC_2018, expectedMessage);

        // missing year prefix
        assertParseFailure(parser, DAY_DESC_1 + MONTH_DESC_1 + VALID_YEAR_2018, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + DAY_DESC_1 + MONTH_DESC_1 + YEAR_DESC_2018,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDeadlineCommand.MESSAGE_USAGE));
    }
}
