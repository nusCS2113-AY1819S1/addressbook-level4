package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetDepartmentCommand;
import seedu.address.model.person.Department;
import seedu.address.testutil.TypicalIndexes;

//@@author Woonhian
public class SetDepartmentCommandParserTest {
    private SetDepartmentCommandParser parser = new SetDepartmentCommandParser();

    @Test
    public void parse_missingParts_failure() {
        String setDepartmentCommand = SetDepartmentCommand.COMMAND_WORD + " %s %s";

        // no index specified
        assertParseFailure(parser, String.format(setDepartmentCommand, null, "Junior Management"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetDepartmentCommand.MESSAGE_USAGE));

        // no field specified
        assertParseFailure(parser, String.format(setDepartmentCommand, "1", null),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetDepartmentCommand.MESSAGE_USAGE));

        // no index and no field specified
        assertParseFailure(parser, String.format(setDepartmentCommand, null, null),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetDepartmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraPrefix_failure() {
        String setDepartmentCommand = "1 " + CliSyntax.PREFIX_DEPARTMENT + "Junior Management "
                + CliSyntax.PREFIX_DEPARTMENT + "Senior Management";
        assertParseFailure(parser, setDepartmentCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetDepartmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_failure() {
        String setDepartmentCommand = "invalid " + CliSyntax.PREFIX_DEPARTMENT
                + "Junior Management";
        assertParseFailure(parser, setDepartmentCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetDepartmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDepartment_failure() {
        String setDepartmentCommand = "1 " + CliSyntax.PREFIX_DEPARTMENT + "Jun1or Management";
        assertParseFailure(parser, setDepartmentCommand,
                Department.MESSAGE_DEPARTMENT_CONSTRAINTS);

        setDepartmentCommand = "1 " + CliSyntax.PREFIX_DEPARTMENT + "Junior Manage";
        assertParseFailure(parser, setDepartmentCommand,
                Department.MESSAGE_DEPARTMENT_CONSTRAINTS);

    }

    @Test
    public void parse_validParameters_success() {
        Index targetIndex = TypicalIndexes.INDEX_FIRST_PERSON;
        Department expectedDepartment = new Department("Junior Management");
        SetDepartmentCommand expectedCommandReturn = new SetDepartmentCommand(targetIndex, expectedDepartment);

        String sd = String.valueOf(targetIndex.getOneBased()) + " " + CliSyntax.PREFIX_DEPARTMENT
                + expectedDepartment.fullDepartment;
        assertParseSuccess(parser, sd, expectedCommandReturn);
    }
}
