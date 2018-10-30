package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalGroups.getTypicalGroups;
import static seedu.address.testutil.TypicalGroups.getTypicalGroupsValidEmailPersons;
import static seedu.address.testutil.TypicalPersons.getMultipleValidTypicalPerson;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.email.Domain;
import seedu.address.model.group.Group;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;

/**
 * Contains integration tests for sending emails to a single index of person, multiple indexes of persons,
 * persons belonging to a group.
 */
public class EmailCommandIntegrationTest {
    private static final String commandSingleIndex = "sendmail 8 s/Solution for Assignment "
            + "m/Hi Bob, solution has been attached.";
    private static final String commandSingleIndexAlias = "sm 8 s/Solution for Assignment "
            + "m/Hi Bob, solution has been attached.";
    private static final String commandMultipleIndex = "sendmail 8,9,10,11,12 s/Tutorial Cancelled "
            + "m/Tutorial class for Friday has been cancelled.";
    private static final String commandMultipleIndexAlias = "sm 8,9,10,11,12 s/Tutorial Cancelled "
            + "m/Tutorial Class for Friday has been cancelled.";
    private static final String commandGroupIndex = "sendmail g/6 s/Announcement "
            + "m/Please bring your calculator tomorrow.";
    private static final String commandGroupIndexAlias = "sm g/6 s/Announcement "
            + "m/Please bring your calculator tomorrow.";
    private static final String commandLogin = "login e/" + Domain.SEND_GRID_USERNAME
            + " pw/" + Domain.SEND_GRID_PASSWORD;
    private static final String commandLoginInvalid = "login e/stubEmail123@hotmail.com pw/stubPassword123";
    private static final String commandLoginNoLogin = "login e/ pw/";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        for (Person person : getMultipleValidTypicalPerson()) {
            ab.addPerson(person);
        }
        for (Group group : getTypicalGroups()) {
            ab.createGroup(group);
        }
        ab.createGroup(getTypicalGroupsValidEmailPersons());

        model = new ModelManager(ab, new UserPrefs());
    }

    @Test
    public void execute_sendEmailToSingleIndexSuccessful() throws Exception {
        executeCommand(commandSingleIndex);
    }

    @Test
    public void execute_sendEmailToMultipleIndexSuccessful() throws Exception {
        executeCommand(commandMultipleIndex);
    }

    @Test
    public void execute_sendEmailToGroupIndexSuccessful() throws Exception {
        executeCommand(commandGroupIndex);
    }

    @Test
    public void execute_sendEmailByCommandAliasToSingleIndexSuccessful() throws Exception {
        executeCommand(commandSingleIndexAlias);
    }

    @Test
    public void execute_sendEmailByCommandAliasToMultipleIndexSuccessful() throws Exception {
        executeCommand(commandMultipleIndexAlias);
    }

    @Test
    public void execute_sendEmailByCommandAliasToGroupIndexSuccessful() throws Exception {
        executeCommand(commandGroupIndexAlias);
    }

    @Test
    public void execute_sendEmailUnsuccessfulDueToNoLogin() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(Email.MESSAGE_EMAIL_CONSTRAINTS);
        setNoLogin();
        AddressBookParser addressBookParser = new AddressBookParser();
        Command command = addressBookParser.parseCommand(commandSingleIndex);
        thrown.expect(CommandException.class);
        thrown.expectMessage(EmailCommand.MESSAGE_NO_LOGIN);
        CommandResult commandResult = command.execute(model, commandHistory);
        assertEquals(EmailCommand.MESSAGE_NO_LOGIN, commandResult.feedbackToUser);
    }

    @Test
    public void execute_sendEmailUnsuccessfulDueToInvalidLogin() throws Exception {
        setInvalidLogin();
        AddressBookParser addressBookParser = new AddressBookParser();
        Command command = addressBookParser.parseCommand(commandSingleIndex);
        thrown.expect(CommandException.class);
        thrown.expectMessage(EmailCommand.MESSAGE_AUTHENTICATION_FAIL);
        CommandResult commandResult = command.execute(model, commandHistory);
        assertEquals(EmailCommand.MESSAGE_AUTHENTICATION_FAIL, commandResult.feedbackToUser);
    }

    /**
     * Parses an {@Code commandInput}, executes the command and asserts that it is successful.
     * @param commandInput represents the input entered into the CLI.
     */
    private void executeCommand(String commandInput) throws ParseException, CommandException {
        thrown.expect(ParseException.class);
        thrown.expectMessage(Email.MESSAGE_EMAIL_CONSTRAINTS);
        setValidLogin();
        AddressBookParser addressBookParser = new AddressBookParser();
        Command command = addressBookParser.parseCommand(commandInput);
        CommandResult commandResult = command.execute(model, commandHistory);
        assertEquals(EmailCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
    }

    /**
     * Method to execute valid login command.
     */
    public void setValidLogin() throws ParseException, CommandException {
        AddressBookParser addressBookParser = new AddressBookParser();
        Command command = addressBookParser.parseCommand(commandLogin);
        command.execute(model, commandHistory);
    }

    /**
     * Method to execute invalid login command.
     */
    public void setInvalidLogin() throws ParseException, CommandException {
        AddressBookParser addressBookParser = new AddressBookParser();
        Command command = addressBookParser.parseCommand(commandLoginInvalid);
        command.execute(model, commandHistory);
    }

    /**
     * Method to execute no login command.
     */
    public void setNoLogin() throws ParseException, CommandException {
        AddressBookParser addressBookParser = new AddressBookParser();
        Command command = addressBookParser.parseCommand(commandLoginNoLogin);
        command.execute(model, commandHistory);
    }
}
