package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_REQUEST3;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.AddLeaveCommand;
import seedu.address.model.leave.Date;

//@@author Hafizuddin-NUS
public class AddLeaveParserTest {

    private AddLeaveParser parser = new AddLeaveParser();


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLeaveCommand.MESSAGE_USAGE);

        // missing date prefix
        assertParseFailure(parser, VALID_DATE_REQUEST3,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date
        assertParseFailure(parser, INVALID_DATE_DESC,
                Date.MESSAGE_DATE_CONSTRAINTS);
    }

    @Test
    public void parse_extraPrefix_failure() {
        String addLeaveCommand = " " + CliSyntax.PREFIX_DATE + "20/03/2020 "
                + CliSyntax.PREFIX_DATE + "21/02/2020";
        assertParseFailure(parser, addLeaveCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLeaveCommand.MESSAGE_USAGE));
    }

}
