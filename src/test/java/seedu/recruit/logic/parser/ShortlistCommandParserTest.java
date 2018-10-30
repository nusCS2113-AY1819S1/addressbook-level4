package seedu.recruit.logic.parser;

import static seedu.recruit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSelectCandidateCommandParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSelectCandidateCommandParseSuccess;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSelectCompanyCommandParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSelectCompanyCommandParseSuccess;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSelectJobCommandParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSelectJobCommandParseSuccess;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertShortlistCandidateCommandParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertShortlistCandidateCommandParseSuccess;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Ignore;
import org.junit.Test;
import seedu.recruit.logic.commands.SelectCandidateCommand;
import seedu.recruit.logic.commands.SelectCompanyCommand;
import seedu.recruit.logic.commands.SelectJobCommand;
import seedu.recruit.logic.commands.ShortlistCandidateCommand;

/**
 * Test scope: similar to {@code DeleteCandidateCommandParserTest}.
 * @see DeleteCandidateCommandParserTest
 */
public class ShortlistCommandParserTest {

    private ShortlistParser parser = new ShortlistParser();

    // ================================ 2ND STAGE: SELECT COMPANY ===================================== //

    @Test
    public void parse_validArgs_returnsSelectCompanyCommand() {
        assertSelectCompanyCommandParseSuccess(parser, "1", new SelectCompanyCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgsForSelectCompanyCommand_throwsParseException() {
        assertSelectCompanyCommandParseFailure(parser, "a", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_emptyArgsForSelectCompanyCommand_throwsParseException() {
        assertSelectCompanyCommandParseFailure(parser, "", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    // ================================ 3RD STAGE: SELECT JOB ===================================== //

    @Test
    public void parse_validArgs_returnsSelectJobCommand() {
        assertSelectJobCommandParseSuccess(parser, "1", new SelectJobCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgsForSelectJobCommand_throwsParseException() {
        assertSelectJobCommandParseFailure(parser, "a", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_emptyArgsForSelectJobCommand_throwsParseException() {
        assertSelectJobCommandParseFailure(parser, "", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    // ================================ 4TH STAGE: SELECT CANDIDATE ===================================== //

    @Test
    public void parse_validArgs_returnsSelectCandidateCommand() {
        assertSelectCandidateCommandParseSuccess(parser, "1", new SelectCandidateCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgsForSelectCandidateCommand_throwsParseException() {
        assertSelectCandidateCommandParseFailure(parser, "a", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_emptyArgsForSelectCandidateCommand_throwsParseException() {
        assertSelectCandidateCommandParseFailure(parser, "", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    // ================================ LAST STAGE: CONFIRMATION ===================================== //

    @Test
    @Ignore
    public void parse_validArgs_returnsShortlistCandidateCommand() {
        assertShortlistCandidateCommandParseSuccess(parser, "", new ShortlistCandidateCommand());
    }

    @Test
    public void parse_invalidLetterArgsForShortlistCandidateCommand_throwsParseException() {
        assertShortlistCandidateCommandParseFailure(parser, "a", MESSAGE_UNKNOWN_COMMAND
        + ShortlistCandidateCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidIndexArgsForShortlistCandidateCommand_throwsParseException() {
        assertShortlistCandidateCommandParseFailure(parser, "1", MESSAGE_UNKNOWN_COMMAND
                + ShortlistCandidateCommand.MESSAGE_USAGE);
    }
}
