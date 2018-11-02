package seedu.recruit.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT_DUE_TO_INVALID_ARGUMENT;
import static seedu.recruit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertClearCandidateBookCommandParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertDeleteShortlistedCandidateInitializationCommandParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertListCandidateCommandParseFailure;
import static seedu.recruit.logic.parser.CommandParserTestUtil.assertListCompanyCommandParseFailure;
import static seedu.recruit.testutil.TestUtil.getIndexSet;
import static seedu.recruit.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.LogicState;
import seedu.recruit.logic.commands.AddCandidateCommand;
import seedu.recruit.logic.commands.ClearCandidateBookCommand;
import seedu.recruit.logic.commands.DeleteCandidateCommand;
import seedu.recruit.logic.commands.DeleteShortlistedCandidateInitializationCommand;
import seedu.recruit.logic.commands.EditCandidateCommand;
import seedu.recruit.logic.commands.EditCandidateCommand.EditPersonDescriptor;
import seedu.recruit.logic.commands.ExitCommand;
import seedu.recruit.logic.commands.FindCandidateCommand;
import seedu.recruit.logic.commands.HelpCommand;
import seedu.recruit.logic.commands.HistoryCommand;
import seedu.recruit.logic.commands.ListCandidateCommand;
import seedu.recruit.logic.commands.ListCompanyCommand;
import seedu.recruit.logic.commands.RedoCandidateBookCommand;
import seedu.recruit.logic.commands.SelectCandidateCommand;
import seedu.recruit.logic.commands.SelectCompanyCommand;
import seedu.recruit.logic.commands.SelectJobCommand;
import seedu.recruit.logic.commands.UndoCandidateBookCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.testutil.CandidateBuilder;
import seedu.recruit.testutil.CandidateContainsKeywordsPredicateBuilder;
import seedu.recruit.testutil.EditPersonDescriptorBuilder;
import seedu.recruit.testutil.PersonUtil;

public class RecruitBookParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // dummy LogicState stub
    private LogicState state = new LogicState("primary");
    private EmailUtil emailUtil = new EmailUtil();

    private final RecruitBookParser parser = new RecruitBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Candidate candidate = new CandidateBuilder().build();
        AddCandidateCommand command =
                (AddCandidateCommand) parser.parseCommand(PersonUtil.getAddCandidateCommand(candidate), state,
                        emailUtil);
        assertEquals(new AddCandidateCommand(candidate), command);
    }

    @Test
    public void parseCommand_clearCandidateBook() throws Exception {
        assertTrue(parser.parseCommand(ClearCandidateBookCommand.COMMAND_WORD, state, emailUtil)
                instanceof ClearCandidateBookCommand);
    }

    @Test
    public void parseCommand_clearCandidateBook_throwsException() {
        assertClearCandidateBookCommandParseFailure(parser, "1", state, emailUtil,
                MESSAGE_INVALID_COMMAND_FORMAT_DUE_TO_INVALID_ARGUMENT
                        + ClearCandidateBookCommand.MESSAGE_USAGE);
    }


    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCandidateCommand command = (DeleteCandidateCommand) parser.parseCommand(
                DeleteCandidateCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), state, emailUtil);
        assertEquals(new DeleteCandidateCommand(getIndexSet(INDEX_FIRST)), command);
    }

    @Test
    public void parseCommand_deleteShortlistedCandidateInitialization() throws Exception {
        assertTrue(parser.parseCommand(DeleteShortlistedCandidateInitializationCommand.COMMAND_WORD + "",
                new LogicState(DeleteShortlistedCandidateInitializationCommand.COMMAND_LOGIC_STATE), emailUtil)
                instanceof DeleteShortlistedCandidateInitializationCommand);
    }

    @Test
    public void parseCommand_invaidArgsForDeleteShortlistedCandidateInitializationCommand_throwsParseException() {
        assertDeleteShortlistedCandidateInitializationCommandParseFailure(parser, "1",
                state, emailUtil, MESSAGE_INVALID_COMMAND_FORMAT_DUE_TO_INVALID_ARGUMENT
                        + DeleteShortlistedCandidateInitializationCommand.MESSAGE_USAGE);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Candidate candidate = new CandidateBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(candidate).build();
        EditCandidateCommand command = (EditCandidateCommand) parser.parseCommand(
                EditCandidateCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor),
                new LogicState("primary"), emailUtil);
        assertEquals(new EditCandidateCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, state, emailUtil) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", state, emailUtil)
                instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywordsList = Arrays.asList("n/foo", "p/bar", "e/baz");
        String keywords = keywordsList.stream().collect(Collectors.joining(" "));
        FindCandidateCommand command = (FindCandidateCommand) parser.parseCommand(
                FindCandidateCommand.COMMAND_WORD + " " + keywords, state, emailUtil);
        assertEquals(new FindCandidateCommand(
                new CandidateContainsKeywordsPredicateBuilder(" " + keywords).getCandidatePredicate()), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, state, emailUtil) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3", state, emailUtil)
                instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD, state, emailUtil) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3", state, emailUtil)
                instanceof HistoryCommand);

        try {
            parser.parseCommand("histories", state, emailUtil);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_listCandidates() throws Exception {
        assertTrue(parser.parseCommand(ListCandidateCommand.COMMAND_WORD, state, emailUtil)
                instanceof ListCandidateCommand);
    }

    @Test
    public void parseCommand_invalidArgsForListCandidateCommand_throwsParseException() {
        assertListCandidateCommandParseFailure(parser, "1", state, emailUtil,
                MESSAGE_INVALID_COMMAND_FORMAT_DUE_TO_INVALID_ARGUMENT
                        + ListCandidateCommand.MESSAGE_USAGE);
    }

    @Test
    public void parseCommand_listCompaniesAndJobOffers() throws Exception {
        assertTrue(parser.parseCommand(ListCompanyCommand.COMMAND_WORD, state, emailUtil)
                instanceof ListCompanyCommand);
    }

    @Test
    public void parseCommand_invalidArgsForListCompanyCommand_throwsParseException() throws Exception {
        assertListCompanyCommandParseFailure(parser, "1", state, emailUtil,
                MESSAGE_INVALID_COMMAND_FORMAT_DUE_TO_INVALID_ARGUMENT
                        + ListCompanyCommand.MESSAGE_USAGE);
    }

    @Test
    public void parseCommand_selectCandidate() throws Exception {
        SelectCandidateCommand command = (SelectCandidateCommand) parser.parseCommand(
                SelectCandidateCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), state, emailUtil);
        assertEquals(new SelectCandidateCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_selectCompany() throws Exception {
        SelectCompanyCommand command = (SelectCompanyCommand) parser.parseCommand(
                SelectCompanyCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), state, emailUtil);
        assertEquals(new SelectCompanyCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_selectJob() throws Exception {
        SelectJobCommand command = (SelectJobCommand) parser.parseCommand(
                SelectJobCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), state, emailUtil);
        assertEquals(new SelectJobCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_shortlistCandidate() throws Exception {
        SelectJobCommand command = (SelectJobCommand) parser.parseCommand(
                SelectJobCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), state, emailUtil);
        assertEquals(new SelectJobCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCandidateBookCommand.COMMAND_WORD, state, emailUtil)
                instanceof RedoCandidateBookCommand);
        assertTrue(parser.parseCommand("redoc 1", state, emailUtil)
                instanceof RedoCandidateBookCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCandidateBookCommand.COMMAND_WORD, state, emailUtil)
                instanceof UndoCandidateBookCommand);
        assertTrue(parser.parseCommand("undoc 3", state, emailUtil)
                instanceof UndoCandidateBookCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("", state, emailUtil);
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand", state, emailUtil);
    }
}
