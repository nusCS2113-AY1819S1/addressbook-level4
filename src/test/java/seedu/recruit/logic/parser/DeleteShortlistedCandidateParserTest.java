package seedu.recruit.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertDeleteShortlistedCandidateCommandParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertFilterCompanyCommandForDeleteShortlistParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertFindCompanyCommandForDeleteShortlistParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSelectCompanyCommandForDeleteShortlistParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSelectCompanyCommandForDeleteShortlistParseSuccess;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSelectJobCommandForDeleteShortlistParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSelectJobCommandForDeleteShortlistParseSuccess;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSortCompanyCommandForDeleteShortlistParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSortJobCommandForDeleteShortlistParseFailure;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;
import seedu.recruit.logic.LogicState;
import seedu.recruit.logic.commands.DeleteShortlistedCandidateCommand;
import seedu.recruit.logic.commands.FilterCompanyCommand;
import seedu.recruit.logic.commands.FindCompanyCommand;
import seedu.recruit.logic.commands.SelectCompanyCommand;
import seedu.recruit.logic.commands.SelectJobCommand;
import seedu.recruit.logic.commands.SortCompanyCommand;
import seedu.recruit.logic.commands.SortJobOfferCommand;


public class DeleteShortlistedCandidateParserTest {

    private DeleteShortlistedCandidateParser parser = new DeleteShortlistedCandidateParser();

    // ================================ 2ND STAGE: SELECT COMPANY ===================================== //

    @Test
    public void parse_validArgs_returnsSelectCompanyCommand() {
        assertSelectCompanyCommandForDeleteShortlistParseSuccess(parser, "1",
                new SelectCompanyCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgsForSelectCompanyCommand_throwsParseException() {
        assertSelectCompanyCommandForDeleteShortlistParseFailure(parser, "a",
                ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_emptyArgsForSelectCompanyCommand_throwsParseException() {
        assertSelectCompanyCommandForDeleteShortlistParseFailure(parser, "",
                ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_validArgs_returnsSortCompanyCommand() throws Exception {
        assertTrue(parser.parseCommand(SortCompanyCommand.COMMAND_WORD, " c/",
                new LogicState(SelectCompanyCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST_DELETE))
                instanceof SortCompanyCommand);
    }

    @Test
    public void parse_invalidArgsForSortCompanyCommand_throwsParseException() {
        assertSortCompanyCommandForDeleteShortlistParseFailure(parser, " abc/",
                SortCompanyCommand.MESSAGE_TAG_USAGE);
    }

    @Test
    public void parse_validArgs_returnsFindCompanyCommand() throws Exception {
        assertTrue(parser.parseCommand(FindCompanyCommand.COMMAND_WORD, " c/abc",
                new LogicState(SelectCompanyCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST_DELETE))
                instanceof FindCompanyCommand);
    }

    @Test
    public void parse_invalidArgsForFindCompanyCommand_throwsParseException() {
        assertFindCompanyCommandForDeleteShortlistParseFailure(parser, " abc/",
                MESSAGE_INVALID_COMMAND_FORMAT + FindCompanyCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_validArgs_returnsFilterCompanyCommand() throws Exception {
        assertTrue(parser.parseCommand(FilterCompanyCommand.COMMAND_WORD, " c/abc",
                new LogicState(SelectCompanyCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST_DELETE))
                instanceof FilterCompanyCommand);
    }

    @Test
    public void parse_invalidArgsForFilterCompanyCommand_throwsParseException() {
        assertFilterCompanyCommandForDeleteShortlistParseFailure(parser, " abc/",
                MESSAGE_INVALID_COMMAND_FORMAT + FilterCompanyCommand.MESSAGE_USAGE);
    }

    // ================================ 3RD STAGE: SELECT JOB ===================================== //

    @Test
    public void parse_validArgs_returnsSelectJobCommand() {
        assertSelectJobCommandForDeleteShortlistParseSuccess(parser, "1", new SelectJobCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgsForSelectJobCommand_throwsParseException() {
        assertSelectJobCommandForDeleteShortlistParseFailure(parser, "a", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_emptyArgsForSelectJobCommand_throwsParseException() {
        assertSelectJobCommandForDeleteShortlistParseFailure(parser, "", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_validArgs_returnsSortJobCommand() throws Exception {
        assertTrue(parser.parseCommand(SortJobOfferCommand.COMMAND_WORD, " j/",
                new LogicState(SelectJobCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST_DELETE))
                instanceof SortJobOfferCommand);
    }

    @Test
    public void parse_invalidArgsForSortJobCommand_throwsParseException() {
        assertSortJobCommandForDeleteShortlistParseFailure(parser, " abc/",
                SortJobOfferCommand.MESSAGE_TAG_USAGE);
    }

    // ============================ 4TH STAGE: DELETE SHORTLISTED CANDIDATE ===================================== //

    @Test
    public void parse_validArgs_returnsDeleteShortlistedCandidateCommand() throws Exception {
        assertTrue(parser.parseCommand(DeleteShortlistedCandidateCommand.COMMAND_WORD, "1",
                new LogicState(DeleteShortlistedCandidateCommand.COMMAND_LOGIC_STATE))
                instanceof DeleteShortlistedCandidateCommand);
    }

    @Test
    public void parse_invalidArgsForDeleteShortlistedCandidateCommand_throwsParseException() {
        assertDeleteShortlistedCandidateCommandParseFailure(parser, "a", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_emptyArgsForDeleteShortlistedCandidateCommand_throwsParseException() {
        assertDeleteShortlistedCandidateCommandParseFailure(parser, "", ParserUtil.MESSAGE_INVALID_INDEX);
    }

}
