package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB_WORKOUT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WORKOUT;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.workout.Remark;
import seedu.address.testutil.RemarkBuilder;



public class RemarkCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);
    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_missingParts_failure() {
        Index targetIndex = INDEX_FIRST_WORKOUT;

        // no index specified
        assertParseFailure(parser, VALID_REMARK_AMY_WORKOUT, MESSAGE_INVALID_FORMAT);

        //no prefix
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        //invalid prefix
        assertParseFailure(parser, "1" + "r", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_WORKOUT;
        String userInput = " 1" + REMARK_DESC_BOB_WORKOUT;

        Remark remark = new RemarkBuilder(new Remark(VALID_REMARK_BOB_WORKOUT)).build();

        RemarkCommand expectedCommand = new RemarkCommand(targetIndex, remark);

        assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + REMARK_DESC_AMY_WORKOUT, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + REMARK_DESC_AMY_WORKOUT, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }
}
