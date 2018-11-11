package seedu.recruit.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT_DUE_TO_INVALID_ARGUMENT;
import static seedu.recruit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_NAME;
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
import seedu.recruit.logic.commands.AddCompanyCommand;
import seedu.recruit.logic.commands.AddJobDetailsCommand;
import seedu.recruit.logic.commands.CancelCommand;
import seedu.recruit.logic.commands.ClearCandidateBookCommand;
import seedu.recruit.logic.commands.ClearCompanyBookCommand;
import seedu.recruit.logic.commands.DeleteCandidateCommand;
import seedu.recruit.logic.commands.DeleteShortlistedCandidateCommand;
import seedu.recruit.logic.commands.DeleteShortlistedCandidateInitializationCommand;
import seedu.recruit.logic.commands.EditCandidateCommand;
import seedu.recruit.logic.commands.EditCandidateCommand.EditPersonDescriptor;
import seedu.recruit.logic.commands.EditCompanyCommand;
import seedu.recruit.logic.commands.EditCompanyCommand.EditCompanyDescriptor;
import seedu.recruit.logic.commands.EditJobDetailsCommand;
import seedu.recruit.logic.commands.EditJobDetailsCommand.EditJobOfferDescriptor;
import seedu.recruit.logic.commands.ExitCommand;
import seedu.recruit.logic.commands.FindCandidateCommand;
import seedu.recruit.logic.commands.HelpCommand;
import seedu.recruit.logic.commands.HistoryCommand;
import seedu.recruit.logic.commands.ListCandidateCommand;
import seedu.recruit.logic.commands.ListCompanyCommand;
import seedu.recruit.logic.commands.RedoCommand;
import seedu.recruit.logic.commands.SelectCandidateCommand;
import seedu.recruit.logic.commands.SelectCompanyCommand;
import seedu.recruit.logic.commands.SelectJobCommand;
import seedu.recruit.logic.commands.ShortlistCandidateCommand;
import seedu.recruit.logic.commands.ShortlistCandidateInitializationCommand;
import seedu.recruit.logic.commands.SortCandidateCommand;
import seedu.recruit.logic.commands.SortCompanyCommand;
import seedu.recruit.logic.commands.SortJobOfferCommand;
import seedu.recruit.logic.commands.StartAddCandidateCommand;
import seedu.recruit.logic.commands.StartAddCompanyCommand;
import seedu.recruit.logic.commands.StartAddJobCommand;
import seedu.recruit.logic.commands.UndoCommand;
import seedu.recruit.logic.commands.emailcommand.EmailInitialiseCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.testutil.CandidateBuilder;
import seedu.recruit.testutil.CandidateContainsFindKeywordsPredicateBuilder;
import seedu.recruit.testutil.CompanyBuilder;
import seedu.recruit.testutil.EditCompanyDescriptorBuilder;
import seedu.recruit.testutil.EditJobOfferDescriptorBuilder;
import seedu.recruit.testutil.EditPersonDescriptorBuilder;
import seedu.recruit.testutil.JobOfferBuilder;
import seedu.recruit.testutil.ModelUtil;

public class RecruitBookParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // dummy LogicState stub
    private LogicState state = new LogicState("primary");
    private EmailUtil emailUtil = new EmailUtil();
    private UserPrefs userPrefs = new UserPrefs();

    private final RecruitBookParser parser = new RecruitBookParser();

    // ============================================== Primary Commands ============================================== //

    @Test
    public void parseCommand_start_addcandidates() throws Exception {
        assertTrue(parser.parseCommand(StartAddCandidateCommand.COMMAND_WORD, state, emailUtil, userPrefs)
                instanceof StartAddCandidateCommand);
        try {
            parser.parseCommand(StartAddCandidateCommand.COMMAND_WORD + " 12", state, emailUtil, userPrefs);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_COMMAND_FORMAT_DUE_TO_INVALID_ARGUMENT
                    + StartAddCandidateCommand.MESSAGE_USAGE, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_start_addcompanies() throws Exception {
        assertTrue(parser.parseCommand(StartAddCompanyCommand.COMMAND_WORD, state, emailUtil, userPrefs)
                instanceof StartAddCompanyCommand);

        try {
            parser.parseCommand(StartAddCompanyCommand.COMMAND_WORD + " lol", state, emailUtil, userPrefs);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_COMMAND_FORMAT_DUE_TO_INVALID_ARGUMENT
                    + StartAddCompanyCommand.MESSAGE_USAGE, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_start_addjobs() throws Exception {
        assertTrue(parser.parseCommand(StartAddJobCommand.COMMAND_WORD, state, emailUtil, userPrefs)
                instanceof StartAddJobCommand);

        try {
            parser.parseCommand(StartAddJobCommand.COMMAND_WORD + " lol", state, emailUtil, userPrefs);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_COMMAND_FORMAT_DUE_TO_INVALID_ARGUMENT
                    + StartAddJobCommand.MESSAGE_USAGE, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_clearCandidateBook() throws Exception {
        assertTrue(parser.parseCommand(ClearCandidateBookCommand.COMMAND_WORD, state, emailUtil, userPrefs)
                instanceof ClearCandidateBookCommand);
    }

    @Test
    public void parseCommand_clear_candidateBook() throws Exception {
        assertTrue(parser.parseCommand(ClearCandidateBookCommand.COMMAND_WORD, state, emailUtil, userPrefs)
                instanceof ClearCandidateBookCommand);
        try {
            parser.parseCommand(ClearCandidateBookCommand.COMMAND_WORD + " nani", state, emailUtil, userPrefs);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_COMMAND_FORMAT_DUE_TO_INVALID_ARGUMENT
                    + ClearCandidateBookCommand.MESSAGE_USAGE, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_clear_companyBook() throws Exception {
        assertTrue(parser.parseCommand(ClearCompanyBookCommand.COMMAND_WORD, state, emailUtil, userPrefs)
                instanceof ClearCompanyBookCommand);
        try {
            parser.parseCommand(ClearCompanyBookCommand.COMMAND_WORD + " random", state, emailUtil, userPrefs);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_COMMAND_FORMAT_DUE_TO_INVALID_ARGUMENT
                    + ClearCompanyBookCommand.MESSAGE_USAGE, pe.getMessage());
        }
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
                DeleteCandidateCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), state, emailUtil, userPrefs);
        assertEquals(new DeleteCandidateCommand(getIndexSet(INDEX_FIRST)), command);
    }

    @Test
    public void parseCommand_deleteShortlistedCandidateInitialization() throws Exception {
        assertTrue(parser.parseCommand(DeleteShortlistedCandidateInitializationCommand.COMMAND_WORD + "",
                new LogicState(DeleteShortlistedCandidateInitializationCommand.COMMAND_LOGIC_STATE), emailUtil,
                userPrefs)
                instanceof DeleteShortlistedCandidateInitializationCommand);
    }

    @Test
    public void parseCommand_invalidArgsForDeleteShortlistedCandidateInitializationCommand_throwsParseException() {
        assertDeleteShortlistedCandidateInitializationCommandParseFailure(parser, "1",
                state, emailUtil, MESSAGE_INVALID_COMMAND_FORMAT_DUE_TO_INVALID_ARGUMENT
                        + DeleteShortlistedCandidateInitializationCommand.MESSAGE_USAGE);
    }

    @Test
    public void parseCommand_editCandidates() throws Exception {
        Candidate candidate = new CandidateBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(candidate).build();
        EditCandidateCommand command = (EditCandidateCommand) parser.parseCommand(
                EditCandidateCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + ModelUtil.getEditCandidateDescriptorDetails(descriptor),
                new LogicState("primary"), emailUtil, userPrefs);
        assertEquals(new EditCandidateCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_email() throws Exception {
        assertTrue(parser.parseCommand(EmailInitialiseCommand.COMMAND_WORD, state, emailUtil, userPrefs)
                instanceof EmailInitialiseCommand);
        try {
            parser.parseCommand(EmailInitialiseCommand.COMMAND_WORD + " test", state, emailUtil, userPrefs);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_INVALID_COMMAND_FORMAT_DUE_TO_INVALID_ARGUMENT
                    + EmailInitialiseCommand.MESSAGE_USAGE, pe.getMessage());
        }
      
    public void parseCommand_editCompanies() throws Exception {
        Company company = new CompanyBuilder().build();
        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder(company).build();
        EditCompanyCommand command = (EditCompanyCommand) parser.parseCommand(
                EditCompanyCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased() + " " + ModelUtil.getEditCompanyDescriptorDetails(descriptor),
                new LogicState("primary"), emailUtil, userPrefs);
        assertEquals(new EditCompanyCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_editJobOffers() throws Exception {
        JobOffer jobOffer = new JobOfferBuilder().build();
        EditJobOfferDescriptor descriptor = new EditJobOfferDescriptorBuilder(jobOffer).build();
        EditJobDetailsCommand command = (EditJobDetailsCommand) parser.parseCommand(
                EditJobDetailsCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased() + " " + ModelUtil.getEditJobOfferDescriptorDetails(descriptor),
                new LogicState("primary"), emailUtil, userPrefs);
        assertEquals(new EditJobDetailsCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_sortCandidates() throws Exception {
        SortCandidateCommand command = (SortCandidateCommand) parser.parseCommand(
                SortCandidateCommand.COMMAND_WORD + " " + PREFIX_NAME, state, emailUtil, userPrefs);
        assertEquals(new SortCandidateCommand(PREFIX_NAME), command);
    }

    @Test
    public void parseCommand_sortCompanies() throws Exception {
        SortCompanyCommand command = (SortCompanyCommand) parser.parseCommand(
                SortCompanyCommand.COMMAND_WORD + " " + PREFIX_COMPANY_NAME, state, emailUtil, userPrefs);
        assertEquals(new SortCompanyCommand(PREFIX_COMPANY_NAME), command);
    }

    @Test
    public void parseCommand_sortJobOffers() throws Exception {
        SortJobOfferCommand command = (SortJobOfferCommand) parser.parseCommand(
                SortJobOfferCommand.COMMAND_WORD + " " + PREFIX_JOB, state, emailUtil, userPrefs);
        assertEquals(new SortJobOfferCommand(PREFIX_JOB), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, state, emailUtil, userPrefs) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", state, emailUtil, userPrefs)
                instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywordsList = Arrays.asList("n/foo", "p/bar", "e/baz");
        String keywords = keywordsList.stream().collect(Collectors.joining(" "));
        FindCandidateCommand command = (FindCandidateCommand) parser.parseCommand(
                FindCandidateCommand.COMMAND_WORD + " " + keywords, state, emailUtil, userPrefs);
        assertEquals(new FindCandidateCommand(
                new CandidateContainsFindKeywordsPredicateBuilder(" " + keywords).getCandidatePredicate()), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, state, emailUtil, userPrefs) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3", state, emailUtil, userPrefs)
                instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD, state, emailUtil, userPrefs)
                instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3", state, emailUtil, userPrefs)
                instanceof HistoryCommand);

        try {
            parser.parseCommand("histories", state, emailUtil, userPrefs);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_listCandidates() throws Exception {
        assertTrue(parser.parseCommand(ListCandidateCommand.COMMAND_WORD, state, emailUtil, userPrefs)
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
        assertTrue(parser.parseCommand(ListCompanyCommand.COMMAND_WORD, state, emailUtil, userPrefs)
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
                SelectCandidateCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(),
                state, emailUtil, userPrefs);
        assertEquals(new SelectCandidateCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_selectCompany() throws Exception {
        SelectCompanyCommand command = (SelectCompanyCommand) parser.parseCommand(
                SelectCompanyCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(),
                state, emailUtil, userPrefs);
        assertEquals(new SelectCompanyCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_selectJob() throws Exception {
        SelectJobCommand command = (SelectJobCommand) parser.parseCommand(
                SelectJobCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(), state, emailUtil, userPrefs);
        assertEquals(new SelectJobCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_shortlistCandidateInitialization() throws Exception {
        assertTrue(parser.parseCommand(ShortlistCandidateInitializationCommand.COMMAND_WORD, state, emailUtil,
                userPrefs) instanceof ShortlistCandidateInitializationCommand);
    }

    @Test
    public void parseCommand_deleteShortlistInitializationCandidate() throws Exception {
        assertTrue(parser.parseCommand(DeleteShortlistedCandidateInitializationCommand.COMMAND_WORD, state, emailUtil,
                userPrefs) instanceof DeleteShortlistedCandidateInitializationCommand);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD, state, emailUtil, userPrefs)
                instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1", state, emailUtil, userPrefs)
                instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD, state, emailUtil, userPrefs)
                instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3", state, emailUtil, userPrefs)
                instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("", state, emailUtil, userPrefs);
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand", state, emailUtil, userPrefs);
    }

    // =========================================== ADD INTERMEDIATE COMMANDS ======================================= //

    @Test
    public void parseCommand_add_candidate() throws Exception {
        Candidate candidate = new CandidateBuilder().build();
        LogicState addCandidateState = new LogicState(AddCandidateCommand.COMMAND_WORD);
        AddCandidateCommand command =
                (AddCandidateCommand) parser.parseCommand(ModelUtil.getAddCandidateCommand(candidate),
                        addCandidateState, emailUtil, userPrefs);
        assertEquals(new AddCandidateCommand(candidate), command);
    }

    @Test
    public void parseCommand_add_jobOffer() throws Exception {
        JobOffer jobOffer = new JobOfferBuilder().build();
        LogicState addJobState = new LogicState(AddJobDetailsCommand.COMMAND_WORD);
        AddJobDetailsCommand command =
                (AddJobDetailsCommand) parser.parseCommand(ModelUtil.getAddJobCommand(jobOffer),
                        addJobState, emailUtil, userPrefs);
        assertEquals(new AddJobDetailsCommand(jobOffer), command);
    }

    @Test
    public void parseCommand_add_company() throws Exception {
        Company company = new CompanyBuilder().build();
        LogicState addCompanyState = new LogicState(AddCompanyCommand.COMMAND_WORD);
        AddCompanyCommand command =
                (AddCompanyCommand) parser.parseCommand(ModelUtil.getAddCompanyCommand(company),
                        addCompanyState, emailUtil, userPrefs);
        assertEquals(new AddCompanyCommand(company), command);
    }


    // ================================ SHORTLIST INTERMEDIATE COMMANDS ===================================== //

    @Test
    public void parseCommand_selectCompanyForShortlist() throws Exception {
        LogicState selectCompanyState = new LogicState(SelectCompanyCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST);
        SelectCompanyCommand command =
                (SelectCompanyCommand) parser.parseCommand(SelectCompanyCommand.COMMAND_WORD + " 1",
                        selectCompanyState, emailUtil, userPrefs);
        assertEquals(new SelectCompanyCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_selectJobForShortlist() throws Exception {
        LogicState selectJobState = new LogicState(SelectJobCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST);
        SelectJobCommand command =
                (SelectJobCommand) parser.parseCommand(SelectJobCommand.COMMAND_WORD + " 1",
                        selectJobState, emailUtil, userPrefs);
        assertEquals(new SelectJobCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_selectCandidateForShortlist() throws Exception {
        LogicState selectCandidateState = new LogicState(SelectCandidateCommand.COMMAND_LOGIC_STATE);
        SelectCandidateCommand command =
                (SelectCandidateCommand) parser.parseCommand(SelectCandidateCommand.COMMAND_WORD + " 1",
                        selectCandidateState, emailUtil, userPrefs);
        assertEquals(new SelectCandidateCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_confirmationForShortlist() throws Exception {
        assertTrue(parser.parseCommand(ShortlistCandidateCommand.COMMAND_WORD,
                new LogicState(ShortlistCandidateCommand.COMMAND_LOGIC_STATE), emailUtil, userPrefs)
                instanceof ShortlistCandidateCommand);
    }

    @Test
    public void parseCommand_cancel() throws Exception {
        assertTrue(parser.parseCommand(CancelCommand.COMMAND_WORD,
                new LogicState(SelectJobCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST), emailUtil, userPrefs)
                instanceof CancelCommand);
    }

    // ================================ DELETE SHORTLIST INTERMEDIATE COMMANDS ===================================== //

    @Test
    public void parseCommand_selectCompanyForDeleteShortlist() throws Exception {
        LogicState selectCompanyState = new LogicState(SelectCompanyCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST_DELETE);
        SelectCompanyCommand command =
                (SelectCompanyCommand) parser.parseCommand(SelectCompanyCommand.COMMAND_WORD + " 1",
                        selectCompanyState, emailUtil, userPrefs);
        assertEquals(new SelectCompanyCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_selectJobForDeleteShortlist() throws Exception {
        LogicState selectJobState = new LogicState(SelectJobCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST_DELETE);
        SelectJobCommand command =
                (SelectJobCommand) parser.parseCommand(SelectJobCommand.COMMAND_WORD + " 1",
                        selectJobState, emailUtil, userPrefs);
        assertEquals(new SelectJobCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_deleteCandidateForDeleteShortlist() throws Exception {
        assertTrue(parser.parseCommand(DeleteShortlistedCandidateCommand.COMMAND_WORD + " 1",
                new LogicState(DeleteShortlistedCandidateCommand.COMMAND_LOGIC_STATE), emailUtil, userPrefs)
                instanceof DeleteShortlistedCandidateCommand);
    }
}
