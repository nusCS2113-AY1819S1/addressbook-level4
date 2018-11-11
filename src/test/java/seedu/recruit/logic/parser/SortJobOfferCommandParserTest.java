package seedu.recruit.logic.parser;

import static seedu.recruit.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE_RANGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_REVERSE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.recruit.logic.commands.SortJobOfferCommand;

public class SortJobOfferCommandParserTest {

    private SortJobOfferCommandParser parser = new SortJobOfferCommandParser();

    @Test
    public void parse_validArguments_success() {
        assertParseSuccess(parser, PREFIX_COMPANY_NAME.toString(),
                new SortJobOfferCommand(PREFIX_COMPANY_NAME));
        assertParseSuccess(parser, PREFIX_JOB.toString(),
                new SortJobOfferCommand(PREFIX_JOB));
        assertParseSuccess(parser, PREFIX_AGE_RANGE.toString(),
                new SortJobOfferCommand(PREFIX_AGE_RANGE));
        assertParseSuccess(parser, PREFIX_EDUCATION.toString(),
                new SortJobOfferCommand(PREFIX_EDUCATION));
        assertParseSuccess(parser, PREFIX_SALARY.toString(),
                new SortJobOfferCommand(PREFIX_SALARY));
        assertParseSuccess(parser, PREFIX_REVERSE.toString(),
                new SortJobOfferCommand(PREFIX_REVERSE));
    }

    @Test
    public void parse_invalidArguments_failure() {
        String expectedMessage = SortJobOfferCommand.MESSAGE_TAG_USAGE;
        assertParseFailure(parser, PREFIX_ADDRESS.toString(), expectedMessage);
        assertParseFailure(parser, PREFIX_AGE.toString(), expectedMessage);
        assertParseFailure(parser, PREFIX_NAME.toString(), expectedMessage);
        assertParseFailure(parser, "1", expectedMessage);
        assertParseFailure(parser, "n", expectedMessage);
        assertParseFailure(parser, "/n", expectedMessage);
        assertParseFailure(parser, " ", expectedMessage);
        assertParseFailure(parser, "", expectedMessage);
        assertParseFailure(parser, PREFIX_AGE_RANGE.toString() + PREFIX_JOB.toString(), expectedMessage);
        assertParseFailure(parser, PREFIX_EDUCATION.toString() + PREFIX_REVERSE.toString(), expectedMessage);
    }

}
