package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_MARKS;
import static seedu.address.logic.parser.GradeAddCommandParser.MESSAGE_MODULE_CODE_INVALID;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.GradeAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;


public class GradeAddCommandParserTest {
    private GradeAddCommandParser parser = new GradeAddCommandParser();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parse_invalidFormat_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradeAddCommand.MESSAGE_USAGE);

        //invalid arguments found
        String arg = "this is an invalid format";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(arg);
    }

    @Test
    public void parse_invalidGradebookName_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_MODULE_CODE_INVALID);
        String moduleCode = "CS2113";
        String invalidGradebookComponentName = "Finals";
        String adminNo = "A0169999A";
        float marks = 10;

        //invalid module code
        String argsWithInvalidGradebookName = " "
                + PREFIX_MODULE_CODE
                + moduleCode
                + " "
                + PREFIX_GRADEBOOK_ITEM
                + invalidGradebookComponentName
                + " "
                + PREFIX_MATRIC
                + adminNo
                + " "
                + PREFIX_STUDENT_MARKS
                + marks;
        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argsWithInvalidGradebookName);
    }
}
