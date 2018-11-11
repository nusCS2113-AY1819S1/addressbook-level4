package seedu.recruit.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT_DUE_TO_INVALID_ARGUMENT;
import static seedu.recruit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND_DUE_TO_INTERFACE;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertFilterCandidateCommandParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertFilterCompanyCommandParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertFindCandidateCommandParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertFindCompanyCommandParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSelectCandidateCommandParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSelectCandidateCommandParseSuccess;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSelectCompanyCommandParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSelectCompanyCommandParseSuccess;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSelectJobCommandParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSelectJobCommandParseSuccess;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertShortlistCandidateCommandParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSortCandidateCommandParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSortCompanyCommandParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertSortJobCommandParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertUnknownCommandInFourthStageShortlistParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertUnknownCommandInLastStageShortlistParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertUnknownCommandInSecondStageShortlistParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertUnknownCommandInThirdStageShortlistParseFailure;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.recruit.logic.LogicState;
import seedu.recruit.logic.commands.FilterCandidateCommand;
import seedu.recruit.logic.commands.FilterCompanyCommand;
import seedu.recruit.logic.commands.FindCandidateCommand;
import seedu.recruit.logic.commands.FindCompanyCommand;
import seedu.recruit.logic.commands.SelectCandidateCommand;
import seedu.recruit.logic.commands.SelectCompanyCommand;
import seedu.recruit.logic.commands.SelectJobCommand;
import seedu.recruit.logic.commands.ShortlistCandidateCommand;
import seedu.recruit.logic.commands.ShortlistCandidateInitializationCommand;
import seedu.recruit.logic.commands.SortCandidateCommand;
import seedu.recruit.logic.commands.SortCompanyCommand;
import seedu.recruit.logic.commands.SortJobOfferCommand;


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

    @Test
    public void parse_validArgs_returnsSortCompanyCommand() throws Exception {
        assertTrue(parser.parseCommand(SortCompanyCommand.COMMAND_WORD, " c/",
                new LogicState(SelectCompanyCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST)) instanceof SortCompanyCommand);
    }

    @Test
    public void parse_invalidArgsForSortCompanyCommand_throwsParseException() {
        assertSortCompanyCommandParseFailure(parser, " C/", SortCompanyCommand.MESSAGE_TAG_USAGE);
    }

    @Test
    public void parse_validArgs_returnsFindCompanyCommand() throws Exception {
        assertTrue(parser.parseCommand(FindCompanyCommand.COMMAND_WORD, " c/abc",
                new LogicState(SelectCompanyCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST)) instanceof FindCompanyCommand);
    }

    @Test
    public void parse_invalidArgsForFindCompanyCommand_throwsParseException() {
        assertFindCompanyCommandParseFailure(parser, " C/", MESSAGE_INVALID_COMMAND_FORMAT
                + FindCompanyCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_validArgs_returnsFilterCompanyCommand() throws Exception {
        assertTrue(parser.parseCommand(FilterCompanyCommand.COMMAND_WORD, " c/abc",
                new LogicState(SelectCompanyCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST))
                instanceof FilterCompanyCommand);
    }

    @Test
    public void parse_invalidArgsForFilterCompanyCommand_throwsParseException() {
        assertFilterCompanyCommandParseFailure(parser, " C/", MESSAGE_INVALID_COMMAND_FORMAT
                + FilterCompanyCommand.MESSAGE_USAGE);
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

    @Test
    public void parse_validArgs_returnsSortJobCommand() throws Exception {
        assertTrue(parser.parseCommand(SortJobOfferCommand.COMMAND_WORD, " j/",
                new LogicState(SelectJobCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST)) instanceof SortJobOfferCommand);
    }

    @Test
    public void parse_invalidArgsForSortJobCommand_throwsParseException() {
        assertSortJobCommandParseFailure(parser, " J/", SortJobOfferCommand.MESSAGE_TAG_USAGE);
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

    @Test
    public void parse_validArgs_returnsSortCandidateCommand() throws Exception {
        assertTrue(parser.parseCommand(SortCandidateCommand.COMMAND_WORD, " n/",
                new LogicState(SelectCandidateCommand.COMMAND_LOGIC_STATE)) instanceof SortCandidateCommand);
    }

    @Test
    public void parse_invalidArgsForSortCandidateCommand_throwsParseException() {
        assertSortCandidateCommandParseFailure(parser, " N/", SortCandidateCommand.MESSAGE_TAG_USAGE);
    }

    @Test
    public void parse_validArgs_returnsFindCandidateCommand() throws Exception {
        assertTrue(parser.parseCommand(FindCandidateCommand.COMMAND_WORD, " n/abc",
                new LogicState(SelectCandidateCommand.COMMAND_LOGIC_STATE)) instanceof FindCandidateCommand);
    }

    @Test
    public void parse_invalidArgsForFindCandidateCommand_throwsParseException() {
        assertFindCandidateCommandParseFailure(parser, " N/abc", MESSAGE_INVALID_COMMAND_FORMAT
                + FindCandidateCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_validArgs_returnsFilterCandidateCommand() throws Exception {
        assertTrue(parser.parseCommand(FilterCandidateCommand.COMMAND_WORD, " n/abc",
                new LogicState(SelectCandidateCommand.COMMAND_LOGIC_STATE))
                instanceof FilterCandidateCommand);
    }

    @Test
    public void parse_invalidArgsForFilterCandidateCommand_throwsParseException() {
        assertFilterCandidateCommandParseFailure(parser, " N/abc",
                MESSAGE_INVALID_COMMAND_FORMAT + FilterCandidateCommand.MESSAGE_USAGE);
    }

    // ================================ LAST STAGE: CONFIRMATION ===================================== //

    @Test
    public void parse_validArgs_returnsShortlistCandidateCommand() throws Exception {
        assertTrue(parser.parseCommand(ShortlistCandidateCommand.COMMAND_WORD, "",
                new LogicState(ShortlistCandidateCommand.COMMAND_LOGIC_STATE)) instanceof ShortlistCandidateCommand);
    }

    @Test
    public void parse_invalidLetterArgsForShortlistCandidateCommand_throwsParseException() {
        assertShortlistCandidateCommandParseFailure(parser, "a",
                MESSAGE_INVALID_COMMAND_FORMAT_DUE_TO_INVALID_ARGUMENT
                + ShortlistCandidateCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidIndexArgsForShortlistCandidateCommand_throwsParseException() {
        assertShortlistCandidateCommandParseFailure(parser, "1",
                MESSAGE_INVALID_COMMAND_FORMAT_DUE_TO_INVALID_ARGUMENT
                + ShortlistCandidateCommand.MESSAGE_USAGE);
    }

    // ================================ DISALLOWED COMMANDS ===================================== //

    @Test
    public void parse_deleteCandidateCommandUnknownCommandInSecondStageOfInterface_throwsParseException() {
        assertUnknownCommandInSecondStageShortlistParseFailure(parser, "1",
                MESSAGE_UNKNOWN_COMMAND_DUE_TO_INTERFACE
                        + ShortlistCandidateInitializationCommand.MESSAGE_NEXT_STEP
                        + SelectCompanyCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_deleteCandidateCommandUnknownCommandInThirdStageOfInterface_throwsParseException() {
        assertUnknownCommandInThirdStageShortlistParseFailure(parser, "1",
                MESSAGE_UNKNOWN_COMMAND_DUE_TO_INTERFACE
                        + SelectCompanyCommand.MESSAGE_SELECT_COMPANY_SUCCESS_NEXT_STEP
                        + SelectJobCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_deleteCandidateCommandUnknownCommandInFourthStageOfInterface_throwsParseException() {
        assertUnknownCommandInFourthStageShortlistParseFailure(parser, "1",
                MESSAGE_UNKNOWN_COMMAND_DUE_TO_INTERFACE
                        + SelectJobCommand.MESSAGE_SELECT_JOB_SUCCESS_NEXT_STEP_IN_SHORTLIST
                        + SelectCandidateCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_deleteCandidateCommandUnknownCommandInLastStageOfInterface_throwsParseException() {
        assertUnknownCommandInLastStageShortlistParseFailure(parser, "1",
                MESSAGE_UNKNOWN_COMMAND_DUE_TO_INTERFACE
                        + ShortlistCandidateCommand.MESSAGE_USAGE);
    }

}
