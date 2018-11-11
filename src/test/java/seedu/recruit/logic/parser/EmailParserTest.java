package seedu.recruit.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.recruit.commons.util.EmailUtil.EMAIL_ADD_COMMAND;
import static seedu.recruit.commons.util.EmailUtil.EMAIL_BACK_COMMAND;
import static seedu.recruit.commons.util.EmailUtil.EMAIL_NEXT_COMMAND;
import static seedu.recruit.commons.util.EmailUtil.EMAIL_PREVIEW_COMMAND;
import static seedu.recruit.commons.util.EmailUtil.EMAIL_SEND_COMMAND;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.LogicState;
import seedu.recruit.logic.commands.FindCandidateCommand;
import seedu.recruit.logic.commands.ListCandidateCommand;
import seedu.recruit.logic.commands.ListCompanyCommand;
import seedu.recruit.logic.commands.emailcommand.EmailContentsAddCommand;
import seedu.recruit.logic.commands.emailcommand.EmailContentsBackCommand;
import seedu.recruit.logic.commands.emailcommand.EmailContentsCommand;
import seedu.recruit.logic.commands.emailcommand.EmailContentsNextCommand;
import seedu.recruit.logic.commands.emailcommand.EmailRecipientsAddCommand;
import seedu.recruit.logic.commands.emailcommand.EmailRecipientsCommand;
import seedu.recruit.logic.commands.emailcommand.EmailRecipientsNextCommand;
import seedu.recruit.logic.commands.emailcommand.EmailSendBackCommand;
import seedu.recruit.logic.commands.emailcommand.EmailSendCommand;
import seedu.recruit.logic.commands.emailcommand.EmailSendPreviewCommand;
import seedu.recruit.logic.commands.emailcommand.EmailSendSendCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.testutil.CandidateContainsFindKeywordsPredicateBuilder;

/**
 * unit test for email parser .java
 */
class EmailParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private EmailUtil emailUtil = new EmailUtil();
    private final EmailParser parser = new EmailParser();

    // ------------------------------- Recipient Stage ------------------------------- //
    @Test
    void parseCommand_listCandidateRecipientState() throws Exception {
        LogicState state = new LogicState(EmailRecipientsCommand.COMMAND_LOGIC_STATE);
        assertTrue(parser.parseCommand(ListCandidateCommand.COMMAND_WORD, "", state, emailUtil)
                instanceof ListCandidateCommand);
    }

    @Test
    void parseCommand_listCompanyRecipientState() throws Exception {
        LogicState state = new LogicState(EmailRecipientsCommand.COMMAND_LOGIC_STATE);
        assertTrue(parser.parseCommand(ListCompanyCommand.COMMAND_WORD, "", state, emailUtil)
                instanceof ListCompanyCommand);
    }

    @Test
    void parseCommand_nextCommandRecipientState() throws Exception {
        LogicState state = new LogicState(EmailRecipientsCommand.COMMAND_LOGIC_STATE);
        assertTrue(parser.parseCommand(EMAIL_NEXT_COMMAND, "", state, emailUtil)
                instanceof EmailRecipientsNextCommand);
    }

    @Test
    void parseCommand_addCommandRecipientState() throws Exception {
        LogicState state = new LogicState(EmailRecipientsCommand.COMMAND_LOGIC_STATE);
        assertTrue(parser.parseCommand(EMAIL_ADD_COMMAND, "", state, emailUtil)
                instanceof EmailRecipientsAddCommand);
    }

    @Test
    void parseCommand_findCandidateRecipientState() throws Exception {
        LogicState state = new LogicState(EmailRecipientsCommand.COMMAND_LOGIC_STATE);
        List<String> keywordsList = Arrays.asList(" n/foo", "p/bar", "e/baz");
        String keywords = keywordsList.stream().collect(Collectors.joining(" "));
        FindCandidateCommand command = (FindCandidateCommand) parser.parseCommand(
                FindCandidateCommand.COMMAND_WORD, keywords, state, emailUtil);
        assertEquals(new FindCandidateCommand(
                new CandidateContainsFindKeywordsPredicateBuilder(" " + keywords).getCandidatePredicate()), command);
    }

    @Test
    void parseCommand_unknownCommandRecipientState() throws Exception {
        LogicState state = new LogicState(EmailRecipientsCommand.COMMAND_LOGIC_STATE);
        try {
            parser.parseCommand("unknownCommand", "", state, emailUtil);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }
    // ------------------------------- Contents Stage ------------------------------- //
    // ------------------------------- EmailUtil.areCandidatesRecipients TRUE ------------------------------- //

    @Test
    void parseCommand_listCandidateContentStateEmailUtilTrue() throws Exception {
        LogicState state = new LogicState(EmailContentsCommand.COMMAND_LOGIC_STATE);
        emailUtil.setAreRecipientsCandidates(true);
        try {
            parser.parseCommand(ListCandidateCommand.COMMAND_WORD, "", state, emailUtil);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    void parseCommand_listCompanyContentStateEmailUtilTrue() throws Exception {
        LogicState state = new LogicState(EmailContentsCommand.COMMAND_LOGIC_STATE);
        emailUtil.setAreRecipientsCandidates(true);
        assertTrue(parser.parseCommand(ListCompanyCommand.COMMAND_WORD, "", state, emailUtil)
                instanceof ListCompanyCommand);
    }

    @Test
    void parseCommand_nextCommandContentsStateUtilTrue() throws Exception {
        LogicState state = new LogicState(EmailContentsCommand.COMMAND_LOGIC_STATE);
        emailUtil.setAreRecipientsCandidates(true);
        assertTrue(parser.parseCommand(EMAIL_NEXT_COMMAND, "", state, emailUtil)
                instanceof EmailContentsNextCommand);
    }

    @Test
    void parseCommand_addCommandContentsStateUtilTrue() throws Exception {
        LogicState state = new LogicState(EmailContentsCommand.COMMAND_LOGIC_STATE);
        emailUtil.setAreRecipientsCandidates(true);
        assertTrue(parser.parseCommand(EMAIL_ADD_COMMAND, "", state, emailUtil)
                instanceof EmailContentsAddCommand);
    }

    @Test
    void parseCommand_backCommandContentsStateUtilTrue() throws Exception {
        LogicState state = new LogicState(EmailContentsCommand.COMMAND_LOGIC_STATE);
        emailUtil.setAreRecipientsCandidates(true);
        assertTrue(parser.parseCommand(EMAIL_BACK_COMMAND, "", state, emailUtil)
                instanceof EmailContentsBackCommand);
    }

    @Test
    void parseCommand_unknownCommandContentStateEmailUtilTrue() throws Exception {
        LogicState state = new LogicState(EmailContentsCommand.COMMAND_LOGIC_STATE);
        emailUtil.setAreRecipientsCandidates(true);
        try {
            parser.parseCommand("unknownCommand", "", state, emailUtil);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    // ------------------------------- EmailUtil.areCandidatesRecipients FALSE ------------------------------- //

    @Test
    void parseCommand_listCandidateContentStateEmailUtilFalse() throws Exception {
        LogicState state = new LogicState(EmailContentsCommand.COMMAND_LOGIC_STATE);
        emailUtil.setAreRecipientsCandidates(false);
        assertTrue(parser.parseCommand(ListCandidateCommand.COMMAND_WORD, "", state, emailUtil)
                instanceof ListCandidateCommand);
    }

    @Test
    void parseCommand_listCompanyContentStateEmailUtilFalse() throws Exception {
        LogicState state = new LogicState(EmailContentsCommand.COMMAND_LOGIC_STATE);
        emailUtil.setAreRecipientsCandidates(false);
        try {
            parser.parseCommand(ListCompanyCommand.COMMAND_WORD, "", state, emailUtil);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    void parseCommand_findCandidateContentStateEmailUtilFalse() throws Exception {
        LogicState state = new LogicState(EmailContentsCommand.COMMAND_LOGIC_STATE);
        emailUtil.setAreRecipientsCandidates(false);
        List<String> keywordsList = Arrays.asList(" n/foo", "p/bar", "e/baz");
        String keywords = keywordsList.stream().collect(Collectors.joining(" "));
        FindCandidateCommand command = (FindCandidateCommand) parser.parseCommand(
                FindCandidateCommand.COMMAND_WORD, keywords, state, emailUtil);
        assertEquals(new FindCandidateCommand(
                new CandidateContainsFindKeywordsPredicateBuilder(" " + keywords).getCandidatePredicate()), command);
    }

    @Test
    void parseCommand_nextCommandContentStateEmailUtilFalse() throws Exception {
        LogicState state = new LogicState(EmailContentsCommand.COMMAND_LOGIC_STATE);
        emailUtil.setAreRecipientsCandidates(false);
        assertTrue(parser.parseCommand(EMAIL_NEXT_COMMAND, "", state, emailUtil)
                instanceof EmailContentsNextCommand);
    }

    @Test
    void parseCommand_addCommandContentStateEmailUtilFalse() throws Exception {
        LogicState state = new LogicState(EmailContentsCommand.COMMAND_LOGIC_STATE);
        emailUtil.setAreRecipientsCandidates(false);
        assertTrue(parser.parseCommand(EMAIL_ADD_COMMAND, "", state, emailUtil)
                instanceof EmailContentsAddCommand);
    }

    @Test
    void parseCommand_backCommandContentStateEmailUtilFalse() throws Exception {
        LogicState state = new LogicState(EmailContentsCommand.COMMAND_LOGIC_STATE);
        emailUtil.setAreRecipientsCandidates(false);
        assertTrue(parser.parseCommand(EMAIL_BACK_COMMAND, "", state, emailUtil)
                instanceof EmailContentsBackCommand);
    }

    @Test
    void parseCommand_unknownCommandContentStateEmailUtilFalse() throws Exception {
        LogicState state = new LogicState(EmailContentsCommand.COMMAND_LOGIC_STATE);
        emailUtil.setAreRecipientsCandidates(false);
        try {
            parser.parseCommand("unknownCommand", "", state, emailUtil);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    // ------------------------------- Email Send Stage ------------------------------- //

    @Test
    void parseCommand_sendCommandSendState() throws Exception {
        LogicState state = new LogicState(EmailSendCommand.COMMAND_LOGIC_STATE);
        assertTrue(parser.parseCommand(EMAIL_SEND_COMMAND, "", state, emailUtil)
                instanceof EmailSendSendCommand);
    }

    @Test
    void parseCommand_backCommandSendState() throws Exception {
        LogicState state = new LogicState(EmailSendCommand.COMMAND_LOGIC_STATE);
        assertTrue(parser.parseCommand(EMAIL_BACK_COMMAND, "", state, emailUtil)
                instanceof EmailSendBackCommand);
    }

    @Test
    void parseCommand_previewCommandSendState() throws Exception {
        LogicState state = new LogicState(EmailSendCommand.COMMAND_LOGIC_STATE);
        assertTrue(parser.parseCommand(EMAIL_PREVIEW_COMMAND, "", state, emailUtil)
                instanceof EmailSendPreviewCommand);
    }

    @Test
    void parseCommand_unknownSendState() throws Exception {
        LogicState state = new LogicState(EmailSendCommand.COMMAND_LOGIC_STATE);
        try {
            parser.parseCommand("unknownCommand", "", state, emailUtil);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

}
