package seedu.recruit.logic.parser;

import static seedu.recruit.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE_RANGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_REVERSE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.recruit.logic.commands.SortCandidateCommand;

public class SortCandidateCommandParserTest {

    private SortCandidateCommandParser parser = new SortCandidateCommandParser();

    @Test
    public void parse_validArguments_success() {
        assertParseSuccess(parser, PREFIX_NAME.toString(),
                new SortCandidateCommand(PREFIX_NAME));
        assertParseSuccess(parser, PREFIX_AGE.toString(),
                new SortCandidateCommand(PREFIX_AGE));
        assertParseSuccess(parser, PREFIX_EMAIL.toString(),
                new SortCandidateCommand(PREFIX_EMAIL));
        assertParseSuccess(parser, PREFIX_JOB.toString(),
                new SortCandidateCommand(PREFIX_JOB));
        assertParseSuccess(parser, PREFIX_EDUCATION.toString(),
                new SortCandidateCommand(PREFIX_EDUCATION));
        assertParseSuccess(parser, PREFIX_SALARY.toString(),
                new SortCandidateCommand(PREFIX_SALARY));
        assertParseSuccess(parser, PREFIX_REVERSE.toString(),
                new SortCandidateCommand(PREFIX_REVERSE));
    }

    @Test
    public void parse_invalidArguments_failure() {
        String expectedMessage = SortCandidateCommand.MESSAGE_TAG_USAGE;
        assertParseFailure(parser, PREFIX_ADDRESS.toString(), expectedMessage);
        assertParseFailure(parser, PREFIX_AGE_RANGE.toString(), expectedMessage);
        assertParseFailure(parser, PREFIX_PHONE.toString(), expectedMessage);
        assertParseFailure(parser, PREFIX_COMPANY_NAME.toString(), expectedMessage);
        assertParseFailure(parser, "1", expectedMessage);
        assertParseFailure(parser, "n", expectedMessage);
        assertParseFailure(parser, "/n", expectedMessage);
        assertParseFailure(parser, " ", expectedMessage);
        assertParseFailure(parser, "", expectedMessage);
        assertParseFailure(parser, PREFIX_AGE.toString() + PREFIX_NAME.toString(), expectedMessage);
        assertParseFailure(parser, PREFIX_EDUCATION.toString() + PREFIX_EMAIL.toString(), expectedMessage);
    }

}
