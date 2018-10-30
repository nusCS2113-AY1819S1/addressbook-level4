package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.model.Filetype;

//@@author jitwei98
public class ExportCommandParserTest {

    private static final String FILETYPE_CSV = "csv";
    private static final String FILETYPE_VCF = "vcf";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_missingNotNullField_failure() {
        // no parameters
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingIndex_failure() {
        assertParseFailure(parser, " csv", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingFiletype_failure() {
        assertParseFailure(parser, " 123456", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_success() {
        Index index;
        Filetype filetype;
        ExportCommand expectedCommand;

        // parse csv filetype
        index = Index.fromOneBased(999);
        filetype = new Filetype(FILETYPE_CSV);
        expectedCommand = new ExportCommand(index, filetype);
        assertParseSuccess(parser, "999 csv", expectedCommand);

        // parse vcf filetype
        index = Index.fromOneBased(12345);
        filetype = new Filetype(FILETYPE_VCF);
        expectedCommand = new ExportCommand(index, filetype);
        assertParseSuccess(parser, "12345 vcf", expectedCommand);
    }
    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "a csv", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "0 csv", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidFiletype_throwsParseException() {
        assertParseFailure(parser, "1 CSV", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 aaaaa", MESSAGE_INVALID_FORMAT);
    }

}
