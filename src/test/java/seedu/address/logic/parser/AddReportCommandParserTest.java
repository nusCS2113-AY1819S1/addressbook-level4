package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFO1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE1;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFORMATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddMedicalReportCommand;
import seedu.address.testutil.ReportBuilder;

public class AddReportCommandParserTest {

    private AddMedicalReportCommandParser parser = new AddMedicalReportCommandParser();

    @Test
    public void parseIndexSpecifiedSuccess() {
        // have report
        Index targetIndex = INDEX_FIRST_PERSON;
        AddMedicalReportCommand expectedCommand =
                new AddMedicalReportCommand(INDEX_FIRST_PERSON, new ReportBuilder().build());
        assertParseSuccess(parser, targetIndex.getOneBased()
                + " " + PREFIX_TITLE + VALID_TITLE1
                + " " + PREFIX_DATE + VALID_DATE1
                + " " + PREFIX_INFORMATION + VALID_INFO1, expectedCommand);
    }

    @Test
    public void parseMissingCompulsoryFieldFailure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedicalReportCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, AddMedicalReportCommand.COMMAND_WORD, expectedMessage);

        // missing index
        assertParseFailure(parser, PREFIX_TITLE + VALID_TITLE1
                + " " + PREFIX_DATE + VALID_DATE1
                + " " + PREFIX_INFORMATION + VALID_INFO1, expectedMessage);
    }
}
