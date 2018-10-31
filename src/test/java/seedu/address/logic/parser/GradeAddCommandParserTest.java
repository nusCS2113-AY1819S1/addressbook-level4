package seedu.address.logic.parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.commands.GradeAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.GradeAddCommandParser.*;

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
    public void parse_missingModuleCode_throwsParseException() throws ParseException {
        String expectedMessage = MESSAGE_MISSING_PARAMS;

        String argWithoutModuleCode = " mc/ i/Finals a/A0122999W m/10";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argWithoutModuleCode);
    }

    @Test
    public void parse_missingGradebookComponentName_throwsParseException() throws ParseException {
        String expectedMessage = MESSAGE_MISSING_PARAMS;

        String argWithoutGradeComponentName = " mc/PC1222 i/ a/A2399002H m/50";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argWithoutGradeComponentName);
    }

    @Test
    public void parse_missingAdminNo_throwsParseException() throws ParseException {
        String expectedMessage = MESSAGE_MISSING_PARAMS;

        String argWithoutAdminNo = " mc/PC1222 i/Finals a/ m/50";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argWithoutAdminNo);
    }

    @Test
    public void parse_invalidMarksType_throwsParseException() throws ParseException {
        String expectedMessage = MESSAGE_MARKS_ERROR;

        String argWithInvalidMarksType = " mc/PC1222 i/Finals a/A9188789S m/60s";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argWithInvalidMarksType);
    }
}
