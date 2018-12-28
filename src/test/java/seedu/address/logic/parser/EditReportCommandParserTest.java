package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_WPREFIX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFO1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFO_WPREFIX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_WPREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORIGINAL_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORIGINAL_TITLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditMedicalReportCommand;
import seedu.address.logic.commands.EditMedicalReportCommand.EditReportDescriptor;
import seedu.address.model.medicalreport.ReportDate;
import seedu.address.model.medicalreport.Title;
import seedu.address.testutil.EditReportDescriptorBuilder;

//@@author chewkahmeng
public class EditReportCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EditMedicalReportCommand.MESSAGE_USAGE);
    private static final String INVALID_INDEX = "-5";
    private EditMedicalReportCommandParser parser = new EditMedicalReportCommandParser();

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(parser, VALID_TITLE1, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseInvalidPreambleFailure() {
        assertParseFailure(parser, INVALID_INDEX + " " + VALID_TITLE_WPREFIX, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_ORIGINAL_TITLE + VALID_TITLE1 + " "
                + PREFIX_ORIGINAL_DATE + VALID_DATE1 + " " + VALID_TITLE_WPREFIX + " "
                + VALID_DATE_WPREFIX + " " + VALID_INFO_WPREFIX;
        EditReportDescriptor descriptor = new EditReportDescriptorBuilder().withTitle(VALID_TITLE1)
                .withDate(VALID_DATE1).withInfo(VALID_INFO1).build();
        EditMedicalReportCommand expectedCommand = new EditMedicalReportCommand(targetIndex, new Title(VALID_TITLE1),
                new ReportDate(VALID_DATE1), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
