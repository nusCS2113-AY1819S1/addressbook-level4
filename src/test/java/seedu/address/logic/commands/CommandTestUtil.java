package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESIGNATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_ALICE = "Alice Pauline";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_CALVIN = "Calvin Duu";
    public static final String VALID_NAME_BENSON = "Benson Meier";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_ALICE = "alice@example.com";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BENSON = "johnd@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_EMAIL_CALVIN = "calvin@example.com";
    public static final String VALID_EMAIL_CARL = "heinz@example.com";
    public static final String VALID_EMAIL_HOON = "stefan@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_DEPARTMENT_AMY = "Marketing";
    public static final String VALID_DEPARTMENT_BOB = "Admin";
    public static final String VALID_DESIGNATION_AMY = "manager";
    public static final String VALID_DESIGNATION_BOB = "manager";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String VALID_EVENT_NAME_BIRTHDAY = "Birthday";
    public static final String VALID_EVENT_NAME_MEETING = "Meeting";
    public static final String VALID_DATE = "2018-10-28";
    public static final String VALID_DATE_CHRISTMAS = "2018-12-25";
    public static final String VALID_DESCRIPTION_PUNCTUAL = "Please be punctual.";
    public static final String VALID_DESCRIPTION_CHRISTMAS = "It's Christmas!";
    public static final String VALID_LOCATION_LT = "LT15";
    public static final String VALID_LOCATION_ROOM = "Room";
    public static final String VALID_MONTH = "01";
    public static final String VALID_TIME_MORNING = "08:00";
    public static final String VALID_TIME_NOON = "12:00";
    public static final String VALID_TIME_NIGHT = "19:00";
    public static final String VALID_YEAR = "2018";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String DEPARTMENT_DESC_AMY = " " + PREFIX_DEPARTMENT + VALID_DEPARTMENT_AMY;
    public static final String DEPARTMENT_DESC_BOB = " " + PREFIX_DEPARTMENT + VALID_DEPARTMENT_BOB;
    public static final String DESIGNATION_DESC_AMY = " " + PREFIX_DESIGNATION + VALID_DESIGNATION_AMY;
    public static final String DESIGNATION_DESC_BOB = " " + PREFIX_DESIGNATION + VALID_DESIGNATION_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String DATE_DESC = " " + PREFIX_DATE + VALID_DATE;
    public static final String MONTH_DESC = " " + PREFIX_MONTH + VALID_MONTH;
    public static final String YEAR_DESC = " " + PREFIX_YEAR + VALID_YEAR;
    public static final String MONTHANDYEAR_DESC = " " + PREFIX_YEAR + VALID_YEAR + " " + PREFIX_MONTH + VALID_MONTH;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_DEPARTMENT_DESC = " " + PREFIX_DEPARTMENT + "M@rketing";
    // '@' not allowed in department
    public static final String INVALID_DESIGNATION_DESC = " " + PREFIX_DESIGNATION + "m@nager";
    // '@' not allowed in designation
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "2018/12/25";
    public static final String INVALID_MONTH_DESC = " " + PREFIX_MONTH + "13";
    public static final String INVALID_YEAR_DESC = " " + PREFIX_YEAR + "18";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final EditEventCommand.EditEventDescriptor DESC_BIRTHDAY;
    public static final EditEventCommand.EditEventDescriptor DESC_MEETING;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder()
                .withPhone(VALID_PHONE_AMY).withName(VALID_NAME_AMY).withAddress(VALID_ADDRESS_AMY)
                .withDepartment(VALID_DEPARTMENT_AMY).withDesignation(VALID_DESIGNATION_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).withName(VALID_NAME_BOB).withAddress(VALID_ADDRESS_BOB)
                .withDepartment(VALID_DEPARTMENT_BOB).withDesignation(VALID_DESIGNATION_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_BIRTHDAY = new EditEventDescriptorBuilder().withEventName(VALID_EVENT_NAME_BIRTHDAY)
                .withDescription(VALID_DESCRIPTION_PUNCTUAL).withLocation(VALID_LOCATION_LT).withDate(VALID_DATE)
                .withStartTime(VALID_TIME_MORNING).withEndTime(VALID_TIME_NOON).build();
        DESC_MEETING = new EditEventDescriptorBuilder().withEventName(VALID_EVENT_NAME_MEETING)
                .withDescription(VALID_DESCRIPTION_CHRISTMAS).withLocation(VALID_LOCATION_ROOM)
                .withDate(VALID_DATE_CHRISTMAS).withStartTime(VALID_TIME_NOON).withEndTime(VALID_TIME_NIGHT).build();
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
     * Updates {@code model}'s filtered list to show only the event at the given {@code targetIndex} in the
     * {@code model}'s event list.
     */
    public static void showEventAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEventList().size());

        Event event = model.getFilteredEventList().get(targetIndex.getZeroBased());
        final String[] splitName = event.getEventName().fullName.split("\\s+");
        model.updateFilteredEventList(new EventContainsKeywordsPredicate(Arrays.asList(splitName[2])));

        assertEquals(1, model.getFilteredEventList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
        model.commitAddressBook();
        model.commitEventList();
    }

    /**
     * Deletes the first event in {@code model}'s filtered list from {@code model}'s event list.
     */
    public static void deleteFirstEvent(Model model) {
        Event firstEvent = model.getFilteredEventList().get(0);
        model.deleteEvent(firstEvent);
        model.commitEventList();
    }

}
