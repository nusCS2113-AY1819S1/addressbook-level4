package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.TypicalAddressBook.getMultipleTypicalAddressBook;
import static seedu.address.testutil.TypicalAddressBook.getSingleTypicalAddressBook;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private Model modelSingle = new ModelManager(getSingleTypicalAddressBook(), new UserPrefs());
    private Model modelMultiple = new ModelManager(getMultipleTypicalAddressBook(), new UserPrefs());

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

    /**
     * Method to set valid email account for sending emails.
     */
    public void setValidLogin() {
        EmailUtil.setUserEmailAddress(Domain.SEND_GRID_USERNAME);
        EmailUtil.setUserEmailPassword(Domain.SEND_GRID_PASSWORD);
    }

    public void setInvalidLogin() {
        EmailUtil.setUserEmailAddress("stubEmail123@hotmail.com");
        EmailUtil.setUserEmailPassword("stubPassword123");
    }

}
