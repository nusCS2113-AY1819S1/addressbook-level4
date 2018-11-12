package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITYLEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WORKINGRATE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditLeaveCommand.EditLeaveDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.NricContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.prioritylevel.PriorityLevelEnum;
import seedu.address.testutil.EditLeaveDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NRIC_AMY = "T2457788T";
    public static final String VALID_NRIC_BOB = "S8572058E";
    public static final String VALID_PASSWORD_AMY = "QWe65e4we65w4";
    public static final String VALID_PASSWORD_BOB = "ASd654";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_DATE_REQUEST3 = "01/10/2020";
    public static final String VALID_DATE_REQUEST1 = "01/11/2020";
    public static final String VALID_DATE_REQUEST2 = "01/12/2020";
    public static final String VALID_NRIC_REQUEST3 = "S1234591A";
    public static final String VALID_APPROVAL_REQUEST3 = "PENDING";
    public static final String VALID_APPROVAL_REQUEST1 = "APPROVED";
    public static final String VALID_APPROVAL_REQUEST2 = "REJECTED";
    public static final String VALID_DEPARTMENT_AMY = "Top Management";
    public static final String VALID_DEPARTMENT_BOB = "Top Management";
    public static final int VALID_PRIORITYLEVEL_AMY = PriorityLevelEnum.MANAGER.getPriorityLevelCode();
    public static final int VALID_PRIORITYLEVEL_BOB = PriorityLevelEnum.MANAGER.getPriorityLevelCode();

    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_MODE_AMY = "in";
    public static final String VALID_MODE_BOB = "out";
    public static final double VALID_WORKINGRATE_AMY = 7.5;
    public static final double VALID_WORKINGRATE_BOB = 10;


    public static final String VALID_TIME_DAWN = "1000";
    public static final String VALID_TIME_NOON = "1100";
    public static final String VALID_TIME_DUSK = "1800";
    public static final String VALID_TIME_MIDNIGHT = "1700";
    public static final String VALID_VENUE_LEVEL_3 = "Level 3";
    public static final String VALID_VENUE_TOILET = "Toilet";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NRIC_DESC_AMY = " " + PREFIX_NRIC + VALID_NRIC_AMY;
    public static final String NRIC_DESC_BOB = " " + PREFIX_NRIC + VALID_NRIC_BOB;
    public static final String NRIC_DESC_REQUEST3 = " " + PREFIX_NRIC + VALID_NRIC_REQUEST3;
    public static final String DATE_DESC_REQUEST3 = " " + PREFIX_DATE + VALID_DATE_REQUEST3;
    public static final String PASSWORD_DESC_AMY = " " + PREFIX_PASSWORD + VALID_PASSWORD_AMY;
    public static final String PASSWORD_DESC_BOB = " " + PREFIX_PASSWORD + VALID_PASSWORD_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String DEPARTMENT_DESC_AMY = " " + PREFIX_DEPARTMENT + VALID_DEPARTMENT_AMY;
    public static final String DEPARTMENT_DESC_BOB = " " + PREFIX_DEPARTMENT + VALID_DEPARTMENT_BOB;
    public static final String PRIORITYLEVEL_DESC_AMY = " " + PREFIX_PRIORITYLEVEL + VALID_PRIORITYLEVEL_AMY;
    public static final String PRIORITYLEVEL_DESC_BOB = " " + PREFIX_PRIORITYLEVEL + VALID_PRIORITYLEVEL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String WORKINGRATE_DESC_AMY = " " + PREFIX_WORKINGRATE + VALID_WORKINGRATE_AMY;
    public static final String WORKINGRATE_DESC_BOB = " " + PREFIX_WORKINGRATE + VALID_WORKINGRATE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String TIME_START_DESC_DAWN = " " + PREFIX_TIME_START + VALID_TIME_DAWN;
    public static final String TIME_END_DESC_DUSK = " " + PREFIX_TIME_END + VALID_TIME_DUSK;
    public static final String VENUE_DESC_TOILET = " " + PREFIX_VENUE + VALID_VENUE_TOILET;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "20/03/18"; //year must be in a format YYYY
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_DEPARTMENT_DESC = " " + PREFIX_DEPARTMENT + "Junior&"; // '&' not allowed
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_WORKINGRATE_DESC = " " + PREFIX_WORKINGRATE
        + "9.5a"; // 'a' not allowed in working rates
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_PASSWORD_DESC = " " + PREFIX_PASSWORD + "A1e"; // Too short
    public static final String INVALID_NRIC_DESC = " " + PREFIX_NRIC + "W1234567Q"; //Incorrect NRIC
    public static final String INVALID_PRIORITYLEVEL_DESC = " " + PREFIX_PRIORITYLEVEL + "9999.52"; //Not an integer

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    public static final EditLeaveDescriptor DESC_REQUEST1;
    public static final EditLeaveDescriptor DESC_REQUEST2;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withDepartment(VALID_DEPARTMENT_AMY)
                .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withDepartment(VALID_DEPARTMENT_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_REQUEST1 = new EditLeaveDescriptorBuilder().withNric(VALID_NRIC_AMY).withDate(VALID_DATE_REQUEST1)
                .withApproval(VALID_APPROVAL_REQUEST1).build();
        DESC_REQUEST2 = new EditLeaveDescriptorBuilder().withNric(VALID_NRIC_BOB).withDate(VALID_DATE_REQUEST2)
                .withApproval(VALID_APPROVAL_REQUEST2).build();
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
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the leave at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showLeaveAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredLeaveList().size());

        Leave leave = model.getFilteredLeaveList().get(targetIndex.getZeroBased());
        final String[] splitName = leave.getEmployeeId().nric.split("\\s+");
        model.updateFilteredLeaveList(new NricContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredLeaveList().size());
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
