package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.TypicalAddressBook.getGroupTypicalAddressBook;
import static seedu.address.testutil.TypicalAddressBook.getMultipleTypicalAddressBook;
import static seedu.address.testutil.TypicalAddressBook.getSingleTypicalAddressBook;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.EmailUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.email.Domain;
import seedu.address.model.email.Message;
import seedu.address.model.email.Subject;

public class EmailCommandTest {

    private static final Subject VALID_SUBJECT = new Subject("This is a valid subject");
    private static final Message VALID_MESSAGE = new Message("This is a valid message");
    private static final String TEST_MESSAGE_CONTAINS_SUBMISSION_EXCEEDED = "ThisIsATestMessage"
            + ".MessageSubmissionExceededException.MessageIsATest";
    private static final String TEST_MESSAGE_CONTAINS_OUTBOUND_SPAM = "ThisIsATestMessage"
            + ".OutboundSpamException.MessageIsATest";
    private static final String TEST_MESSAGE_CONTAINS_NO_RECIPIENT = "ThisIsATestMessage"
            + ".No recipient addresses.MessageIsATest";
    private static final String TEST_MESSAGE_CONTAINS_INVALID_ADDRESS = "ThisIsATestMessage"
            + ".Invalid Addresses.MessageIsATest";
    private static final String TEST_MESSAGE_GENERAL_FAIL = "ThisIsAFailMessage";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private Model modelSingle = new ModelManager(getSingleTypicalAddressBook(), new UserPrefs());
    private Model modelMultiple = new ModelManager(getMultipleTypicalAddressBook(), new UserPrefs());
    private Model modelGroup = new ModelManager(getGroupTypicalAddressBook(), new UserPrefs());
    private Model modelEmptyGroup = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Index index;
    private List<Index> indexList;

    @Test
    public void execute_sendEmailToSinglePersonSuccessful() throws Exception {
        setValidLogin();
        try {
            index = ParserUtil.parseIndex("1");
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE), pe);
        }
        CommandResult commandResult = new EmailCommand(index, VALID_SUBJECT, VALID_MESSAGE)
                .execute(modelSingle, commandHistory);
        assertEquals(String.format(EmailCommand.MESSAGE_SUCCESS), commandResult.feedbackToUser);
    }

    @Test
    public void execute_sendEmailToMultiplePersonSuccessful() throws Exception {
        setValidLogin();
        try {
            indexList = ParserUtil.parseMultipleIndex("1,2,3,4,5");
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE), pe);
        }
        CommandResult commandResult = new EmailCommand(indexList, VALID_SUBJECT, VALID_MESSAGE)
                .execute(modelMultiple, commandHistory);
        assertEquals(String.format(EmailCommand.MESSAGE_SUCCESS), commandResult.feedbackToUser);
    }

    @Test
    public void execute_sendEmailToGroupSuccessful() throws Exception {
        setValidLogin();
        try {
            index = ParserUtil.parseGroupIndex("g/1");
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE), pe);
        }
        CommandResult commandResult = new EmailCommand(index, VALID_SUBJECT, VALID_MESSAGE, true)
                .execute(modelGroup, commandHistory);
        assertEquals(String.format(EmailCommand.MESSAGE_SUCCESS), commandResult.feedbackToUser);
    }

    @Test
    public void execute_sendEmailUnsuccessfulDueToNoLogin() throws Exception {
        setNoLogin();
        try {
            index = ParserUtil.parseIndex("1");
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE), pe);
        }
        thrown.expect(CommandException.class);
        thrown.expectMessage(EmailCommand.MESSAGE_NO_LOGIN);
        CommandResult commandResult = new EmailCommand(index, VALID_SUBJECT, VALID_MESSAGE)
                .execute(modelSingle, commandHistory);
        assertEquals(String.format(EmailCommand.MESSAGE_NO_LOGIN), commandResult.feedbackToUser);
    }

    @Test
    public void execute_sendEmailToSinglePersonUnsuccessfulDueToInvalidLogin() throws Exception {
        setInvalidLogin();
        try {
            index = ParserUtil.parseIndex("1");
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE), pe);
        }
        thrown.expect(CommandException.class);
        thrown.expectMessage(EmailCommand.MESSAGE_AUTHENTICATION_FAIL);
        CommandResult commandResult = new EmailCommand(index, VALID_SUBJECT, VALID_MESSAGE)
                .execute(modelSingle, commandHistory);
        assertEquals(String.format(EmailCommand.MESSAGE_AUTHENTICATION_FAIL), commandResult.feedbackToUser);
    }

    @Test
    public void execute_sendEmailToMultiplePersonUnsuccessfulDueToInvalidLogin() throws Exception {
        setInvalidLogin();
        try {
            indexList = ParserUtil.parseMultipleIndex("1,2,3,4,5");
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE), pe);
        }
        thrown.expect(CommandException.class);
        thrown.expectMessage(EmailCommand.MESSAGE_AUTHENTICATION_FAIL);
        CommandResult commandResult = new EmailCommand(indexList, VALID_SUBJECT, VALID_MESSAGE)
                .execute(modelMultiple, commandHistory);
        assertEquals(String.format(EmailCommand.MESSAGE_AUTHENTICATION_FAIL), commandResult.feedbackToUser);
    }

    @Test
    public void execute_sendEmailToSinglePersonUnsuccessfulDueToInvalidIndex() throws Exception {
        setValidLogin();
        try {
            index = ParserUtil.parseIndex("2");
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE), pe);
        }
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        CommandResult commandResult = new EmailCommand(index, VALID_SUBJECT, VALID_MESSAGE)
                .execute(modelSingle, commandHistory);
        assertEquals(String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX), commandResult.feedbackToUser);
    }

    @Test
    public void execute_sendEmailToMultiplePersonsUnsuccessfulDueToInvalidIndex() throws Exception {
        setValidLogin();
        try {
            indexList = ParserUtil.parseMultipleIndex("1,2,3,4,5,6");
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE), pe);
        }
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_MULTIPLE_DISPLAYED_INDEX);
        CommandResult commandResult = new EmailCommand(indexList, VALID_SUBJECT, VALID_MESSAGE)
                .execute(modelMultiple, commandHistory);
        assertEquals(String.format(Messages.MESSAGE_INVALID_MULTIPLE_DISPLAYED_INDEX), commandResult.feedbackToUser);
    }

    @Test
    public void execute_sendEmailToGroupUnsuccessfulDueToInvalidIndex() throws Exception {
        setValidLogin();
        try {
            index = ParserUtil.parseGroupIndex("g/2");
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE), pe);
        }
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        CommandResult commandResult = new EmailCommand(index, VALID_SUBJECT, VALID_MESSAGE, true)
                .execute(modelGroup, commandHistory);
        assertEquals(String.format(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX), commandResult.feedbackToUser);
    }

    @Test
    public void execute_sendEmailWithSmtpSendFailedException() throws Exception {
        setValidLogin();
        try {
            index = ParserUtil.parseGroupIndex("g/1");
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE), pe);
        }
        thrown.expect(CommandException.class);
        thrown.expectMessage(EmailCommand.setErrorMessageForSendFailedException(TEST_MESSAGE_CONTAINS_NO_RECIPIENT));
        CommandResult commandResult = new EmailCommand(index, VALID_SUBJECT, VALID_MESSAGE, true)
                .execute(modelEmptyGroup, commandHistory);
        assertEquals(String.format(TEST_MESSAGE_CONTAINS_NO_RECIPIENT), commandResult.feedbackToUser);
    }

    @Test
    public void setErrorMessageForSendFailedExceptionTest() {
        String messageConstraint = EmailCommand.MESSAGE_FAIL + ": " + Message.MESSAGE_MESSAGE_CONSTRAINTS;
        String messageFailException = EmailCommand.MESSAGE_FAIL + ": " + EmailCommand.SMTP_FAIL_EXCEPTION_MESSAGE;
        String messageFailExceptionNoRecipient = EmailCommand.MESSAGE_FAIL + ": " + EmailCommand.MESSAGE_NO_RECIPIENT;
        String messageFailExceptionInvalidAddress = EmailCommand.MESSAGE_FAIL + ": "
                + EmailCommand.MESSAGE_INVALID_ADDRESSES;
        String messageGenericFail = EmailCommand.MESSAGE_FAIL;

        assertEquals(EmailCommand.setErrorMessageForSendFailedException(TEST_MESSAGE_CONTAINS_SUBMISSION_EXCEEDED),
                messageConstraint);
        assertEquals(EmailCommand.setErrorMessageForSendFailedException(TEST_MESSAGE_CONTAINS_OUTBOUND_SPAM),
                messageFailException);
        assertEquals(EmailCommand.setErrorMessageForSendFailedException(TEST_MESSAGE_CONTAINS_INVALID_ADDRESS),
                messageFailExceptionInvalidAddress);
        assertEquals(EmailCommand.setErrorMessageForSendFailedException(TEST_MESSAGE_CONTAINS_NO_RECIPIENT),
                messageFailExceptionNoRecipient);
        assertEquals(EmailCommand.setErrorMessageForSendFailedException(TEST_MESSAGE_GENERAL_FAIL),
                messageGenericFail);
    }

    /**
     * Method to set valid email account for sending emails.
     */
    public void setValidLogin() {
        EmailUtil.setUserEmailAddress(Domain.SEND_GRID_USERNAME);
        EmailUtil.setUserEmailPassword(Domain.SEND_GRID_PASSWORD);
    }

    /**
     * Method to set invalid email account for sending emails.
     */
    public void setInvalidLogin() {
        EmailUtil.setUserEmailAddress("stubEmail123@hotmail.com");
        EmailUtil.setUserEmailPassword("stubPassword123");
    }

    /**
     * Method to set no login in EmailUtil.
     */
    public void setNoLogin() {
        EmailUtil.setUserEmailAddress("");
        EmailUtil.setUserEmailPassword("");
    }

}
