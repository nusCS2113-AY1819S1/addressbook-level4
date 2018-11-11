package seedu.recruit.logic.parser;

import static seedu.recruit.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_REVERSE;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.recruit.logic.commands.SortCompanyCommand;

public class SortCompanyCommandParserTest {

    private SortCompanyCommandParser parser = new SortCompanyCommandParser();

    @Test
    public void parse_validArguments_success() {
        assertParseSuccess(parser, PREFIX_COMPANY_NAME.toString(),
                new SortCompanyCommand(PREFIX_COMPANY_NAME));
        assertParseSuccess(parser, PREFIX_EMAIL.toString(),
                new SortCompanyCommand(PREFIX_EMAIL));
        assertParseSuccess(parser, PREFIX_REVERSE.toString(),
                new SortCompanyCommand(PREFIX_REVERSE));
    }

    @Test
    public void parse_invalidArguments_failure() {
        String expectedMessage = SortCompanyCommand.MESSAGE_TAG_USAGE;
        assertParseFailure(parser, PREFIX_ADDRESS.toString(), expectedMessage);
        assertParseFailure(parser, PREFIX_PHONE.toString(), expectedMessage);
        assertParseFailure(parser, PREFIX_NAME.toString(), expectedMessage);
        assertParseFailure(parser, "1", expectedMessage);
        assertParseFailure(parser, "n", expectedMessage);
        assertParseFailure(parser, "/n", expectedMessage);
        assertParseFailure(parser, " ", expectedMessage);
        assertParseFailure(parser, "", expectedMessage);
        assertParseFailure(parser, PREFIX_COMPANY_NAME.toString() + PREFIX_EMAIL.toString(), expectedMessage);
        assertParseFailure(parser, PREFIX_REVERSE.toString() + PREFIX_EMAIL.toString(), expectedMessage);
    }

}
