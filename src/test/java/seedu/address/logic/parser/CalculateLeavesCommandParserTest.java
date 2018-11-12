package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_OVERLOAD_PREFIX_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMPLOYEEID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMPLOYEEID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHEDULE_YEAR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPLOYEEID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_SCHEDULE_DESC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.CalculateLeavesCommand;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.schedule.Year;


public class CalculateLeavesCommandParserTest {
    private CalculateLeavesCommandParser parser = new CalculateLeavesCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        EmployeeId employeeId = new EmployeeId(VALID_EMPLOYEEID_BOB);
        Year year = new Year(VALID_YEAR_BOB);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EMPLOYEEID_DESC_BOB
                + YEAR_SCHEDULE_DESC_BOB , new CalculateLeavesCommand(employeeId, year));
    }

    @Test
    public void parse_multipleFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_OVERLOAD_PREFIX_FORMAT,
                CalculateLeavesCommand.MESSAGE_USAGE);

        // multiple employeeIds
        assertParseFailure(parser, PREAMBLE_WHITESPACE + EMPLOYEEID_DESC_BOB + EMPLOYEEID_DESC_BOB
                + YEAR_SCHEDULE_DESC_BOB, expectedMessage);

        // multiple schedule years
        assertParseFailure(parser, PREAMBLE_WHITESPACE + EMPLOYEEID_DESC_BOB
                + YEAR_SCHEDULE_DESC_BOB + YEAR_SCHEDULE_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalculateLeavesCommand.MESSAGE_USAGE);

        // missing employeeId prefix
        assertParseFailure(parser, VALID_EMPLOYEEID_BOB
                + YEAR_SCHEDULE_DESC_BOB, expectedMessage);

        // missing year prefix
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB
                + VALID_YEAR_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid emplyoeeId
        assertParseFailure(parser, INVALID_EMPLOYEEID_DESC
                + YEAR_SCHEDULE_DESC_BOB, EmployeeId.MESSAGE_EMPLOYEEID_CONSTRAINTS);

        // invalid Year
        assertParseFailure(parser, EMPLOYEEID_DESC_BOB
                + INVALID_SCHEDULE_YEAR_DESC , Year.MESSAGE_YEAR_CONSTRAINTS);

    }
}
