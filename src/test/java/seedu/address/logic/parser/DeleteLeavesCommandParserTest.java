package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_SCHEDULE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATE_SCHEDULE_DESC_CARL;
import static seedu.address.logic.commands.CommandTestUtil.DATE_SCHEDULE_DESC_DONNIE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHEDULE_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_CARL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DONNIE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.DeleteLeavesCommand;
import seedu.address.model.schedule.Date;

public class DeleteLeavesCommandParserTest {
    private DeleteLeavesCommandParser parser = new DeleteLeavesCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Set<Date> dateSet = new HashSet<>();
        dateSet.add(new Date(VALID_DATE_BOB));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DATE_SCHEDULE_DESC_BOB,
                new DeleteLeavesCommand(dateSet));
    }

    @Test
    public void parse_multipleFields_success() {
        Set<Date> dateSet = new HashSet<>();
        dateSet.add(new Date(VALID_DATE_BOB));
        dateSet.add(new Date(VALID_DATE_CARL));
        dateSet.add(new Date(VALID_DATE_DONNIE));

        // multiple dates
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DATE_SCHEDULE_DESC_BOB
                + DATE_SCHEDULE_DESC_CARL + DATE_SCHEDULE_DESC_DONNIE, new DeleteLeavesCommand(dateSet));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLeavesCommand.MESSAGE_USAGE);

        // missing date prefix
        assertParseFailure(parser, VALID_DATE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date format
        assertParseFailure(parser, INVALID_SCHEDULE_DATE_DESC, Date.MESSAGE_DATE_CONSTRAINTS_DEFAULT);
    }
}
