package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPECTED_TURNOUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_OF_EVENTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.LoginBook;
import seedu.address.model.Model;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_ID_ACCOUNT_1 = "A1234567M";
    public static final String VALID_ID_ACCOUNT_2 = "A1234568M";
    public static final String VALID_PASSWORD_ACCOUNT_1 = "zaq1xsw2cde3";
    public static final String VALID_PASSWORD_ACCOUNT_2 = "1qaz2wsx3edc";
    public static final String VALID_ROLE_ACCOUNT_1 = "member";
    public static final String VALID_ROLE_ACCOUNT_2 = "treasurer";

    public static final String ID_ACCOUNT_1_DESC = " " + VALID_ID_ACCOUNT_1;
    public static final String ID_ACCOUNT_2_DESC = " " + VALID_ID_ACCOUNT_2;
    public static final String PASSWORD_ACCOUNT_1_DESC = " " + VALID_PASSWORD_ACCOUNT_1;
    public static final String PASSWORD_ACCOUNT_2_DESC = " " + VALID_PASSWORD_ACCOUNT_2;
    public static final String ROLE_ACCOUNT_1_DESC = " " + VALID_ROLE_ACCOUNT_1;
    public static final String ROLE_ACCOUNT_2_DESC = " " + VALID_ROLE_ACCOUNT_2;

    public static final String INVALID_USERID = " " + "AA234567M"; // 'A' not allowed where it is supposed to be a digit
    public static final String INVALID_USERROLE = " " + "janitor"; // no such role as a 'janitor'

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_SKILL_AMY = "Photography";
    public static final String VALID_SKILL_BOB = "Stage Manager";
    public static final int VALID_SKILL_LEVEL_AMY = 5;
    public static final int VALID_SKILL_LEVEL_BOB = 37;

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String VALID_CLUB_NAME_COMPUTING = "Computing Club";
    public static final String VALID_CLUB_NAME_ECE = "ECE Club";
    public static final String VALID_EXPECTED_TURNOUT_COMPUTING = "200";
    public static final String VALID_EXPECTED_TURNOUT_ECE = "300";
    public static final String VALID_NUMBER_OF_EVENTS_COMPUTING = "5";
    public static final String VALID_NUMBER_OF_EVENTS_ECE = "7";

    public static final String CLUB_NAME_DESC_COMPUTING = " " + PREFIX_CLUB_NAME + VALID_CLUB_NAME_COMPUTING;
    public static final String CLUB_NAME_DESC_ECE = " " + PREFIX_CLUB_NAME + VALID_CLUB_NAME_ECE;
    public static final String EXPECTED_TURNOUT_DESC_COMPUTING = " " + PREFIX_EXPECTED_TURNOUT
            + VALID_EXPECTED_TURNOUT_COMPUTING;
    public static final String EXPECTED_TURNOUT_DESC_ECE = " " + PREFIX_EXPECTED_TURNOUT + VALID_EXPECTED_TURNOUT_ECE;
    public static final String NUMBER_OF_EVENTS_DESC_COMPUTING = " " + PREFIX_NUMBER_OF_EVENTS
            + VALID_NUMBER_OF_EVENTS_COMPUTING;
    public static final String NUMBER_OF_EVENTS_DESC_ECE = " " + PREFIX_NUMBER_OF_EVENTS + VALID_NUMBER_OF_EVENTS_ECE;

    public static final String INVALID_CLUB_NAME_DESC = " " + PREFIX_CLUB_NAME
            + "Comput!ng Club"; // '!' not allowed in club names
    public static final String INVALID_EXPECTED_TURNOUT_DESC = " " + PREFIX_EXPECTED_TURNOUT
            + "10a"; // 'a' not allowed in expected turnout
    public static final String INVALID_NUMBER_OF_EVENTS_DESC = " " + PREFIX_NUMBER_OF_EVENTS
            + "5!"; // '!' not allowed in number of events

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the login book and the filtered account list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertLoginCommandFailure(Command command, Model actualModel,
                                                 CommandHistory actualCommandHistory, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        LoginBook expectedLoginBook = new LoginBook(actualModel.getLoginBook());
        List<LoginDetails> expectedFilteredList = new ArrayList<>(actualModel.getFilteredLoginDetailsList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedLoginBook, actualModel.getLoginBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredLoginDetailsList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.executeSearch(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
        model.commitAddressBook();
    }

}
